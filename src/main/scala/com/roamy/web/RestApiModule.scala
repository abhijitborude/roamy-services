package com.roamy.web

import java.io.{PrintWriter, StringWriter}
import javax.websocket.server.PathParam
import javax.xml.bind.util.JAXBSource

import com.fasterxml.jackson.databind.JsonNode
import com.google.gson.{Gson, JsonObject}
import com.roamy.{AllPassRestApiEntitlementsFilter, RestApiEntitlementFilter}
import com.roamy.dao.api.CitableRepository
import com.roamy.domain.{Category, CitableEntity, City, AbstractEntity}
import com.wordnik.swagger.annotations.{ApiModel, ApiParam, ApiOperation}
import org.springframework.data.domain.{Pageable, Page, PageRequest}
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.{HttpHeaders, ResponseEntity, HttpStatus}
import org.springframework.web.bind.annotation._

import scala.util.{Try, Failure, Success}
import scala.collection.convert.wrapAsScala._
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{universe => ru}
import scala.reflect._
/**
 * Created by amit on 10/18/15.
 */

trait SimpleRestEndPoint[T <: AbstractEntity] extends SupportForCrudOperationsOverHttp[T] {

  def repository: JpaRepository[T, java.lang.Long]

  def findByIdentifier(id: String): Option[T] = {
    Try(Option(repository.findOne(id.toLong))).getOrElse(None)
  }

  def entitlementsFilter: RestApiEntitlementFilter[T] = new AllPassRestApiEntitlementsFilter[T] {}
}

trait CitableRestEndPoint[T <: CitableEntity] extends SupportForCrudOperationsOverHttp[T] {

  def repository: CitableRepository[T, _]

  def findByIdentifier(code: String): Option[T] = {
    Option(repository.findByCode(code))
  }

  def entitlementsFilter: RestApiEntitlementFilter[T] = new AllPassRestApiEntitlementsFilter[T] {}
}

trait SupportForFilteringResourcesViaEntitlements[T <: AbstractEntity] {

  def entitlementsFilter: RestApiEntitlementFilter[T]
}

trait SupportForReturningOneResourceOverHttpGet[T <: AbstractEntity] extends SupportForFilteringResourcesViaEntitlements[T] {

  def findByIdentifier(id: String): Option[T]

  @RequestMapping(value = Array("/{identifier}/"), method = Array(RequestMethod.GET))
  def readOne(@PathVariable("identifier") identifier: String): RestApiResponse[T] = {
    Try(this.findByIdentifier(identifier).filter(entitlementsFilter.isEntitledToView(_))) match {
      case Success(Some(entity)) =>
        new SimpleRestApiResponse[T](HttpStatus.OK, entity)
      case Success(None) =>
        new EmptyRestApiResponse[T](HttpStatus.NOT_FOUND, Map(("error", s"Entity with identifier $identifier was not found.")))
      case Failure(e) =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", Utils.stackTraceAsString(e))
        ))
    }
  }

}

trait SupportForPagingResourcesViaHttpGet[T <: AbstractEntity] extends SupportForFilteringResourcesViaEntitlements[T] {

  def repository: JpaRepository[T, _]

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET))
  def readAll(@RequestParam(value = "page", required = false, defaultValue = "0") page: Int, @RequestParam(value = "size", required = false, defaultValue = "100") size: Int): RestApiResponse[T] = {
    Try {

      val entities: Page[T] = repository.findAll(new PageRequest(page, size))

      val links = Seq(Utils.getNextLink(entities, page, size), Utils.getPreviousLink(entities, page, size)).filter(!_.isEmpty).map(_.get).toMap

      val viewable = entities.filter(entitlementsFilter.isEntitledToView(_))

      (links, viewable)
    } match {
      case Success((links: Map[String, String], entities: Iterable[T])) =>
        new CompositeRestApiResponse[T](HttpStatus.OK, entities, links)
      case Failure(e) =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", Utils.stackTraceAsString(e))
        ))
    }
  }
}

trait SupportForCreatingOneResourceViaHttpPost[T <: AbstractEntity] extends SupportForFilteringResourcesViaEntitlements[T] {

  def deserializer(content: String): Option[T]

  def repository: JpaRepository[T, _]

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.POST))
  def createOne(@RequestBody resourceJsonRep: String): RestApiResponse[T] = {
    deserializer(resourceJsonRep) match {
      case Some(t: T) =>
        Try(repository.save(t)) match {
          case Success(createdEntity: T) =>
            new SimpleRestApiResponse[T](HttpStatus.CREATED, createdEntity)
          case Failure(e) =>
            new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
              ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
              ("developerErrorMessage", Utils.stackTraceAsString(e))
            ))
        }
      case None =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"Unable to deserialize from $resourceJsonRep"),
          ("developerErrorMessage", s"The deserializer function returned None. Does the resource override the deserializer function in the correct way!!")
        ))
    }
  }

}

trait SupportForCreatingOneResourceViaHttpPut[T <: AbstractEntity] extends SupportForFilteringResourcesViaEntitlements[T] {

  def deserializer(content: String): Option[T]

  def repository: JpaRepository[T, _]

  def findByIdentifier(id: String): Option[T]

  @RequestMapping(value = Array("/{identifier}/"), method = Array(RequestMethod.PUT))
  def createOne(@PathVariable("identifier") identifier: String, @RequestBody resourceJsonRep: String): RestApiResponse[T] = {
    findByIdentifier(identifier).map(new SimpleRestApiResponse[T](
      HttpStatus.NOT_MODIFIED, _
    )).getOrElse(
        deserializer(resourceJsonRep) match {
          case Some(t: T) =>
            Try(repository.save(t)) match {
              case Success(createdEntity: T) =>
                new SimpleRestApiResponse[T](HttpStatus.CREATED, createdEntity)
              case Failure(e) =>
                new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
                  ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
                  ("developerErrorMessage", Utils.stackTraceAsString(e))
                ))
            }
          case None =>
            new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
              ("error", s"Unable to deserialize from $resourceJsonRep"),
              ("developerErrorMessage", s"The deserializer function returned None. Does the resource override the deserializer function in the correct way!!")
            ))
        })
  }

}

trait SupportForDeletingOneResourceViaHttpDelete[T <: AbstractEntity] extends SupportForFilteringResourcesViaEntitlements[T] {

  def deserializer(content: String): Option[T]

  def repository: JpaRepository[T, _]

  def findByIdentifier(id: String): Option[T]

  @RequestMapping(value = Array("/{identifier}/"), method = Array(RequestMethod.DELETE))
  def deleteOne(@PathVariable("identifier") identifier: String): RestApiResponse[T] = {
    findByIdentifier(identifier).map((t: T) => {
      if (entitlementsFilter.isEntitledToDelete(t)) {
        Try(repository.delete(t)) match {
          case Success(_) =>
            new EmptyRestApiResponse[T](HttpStatus.NO_CONTENT)
          case Failure(e) =>
            new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
              ("error", s"Unable to delete resource"),
              ("developerErrorMessage", s"Error occurred while deleting resource. Error : ${Utils.stackTraceAsString(e)}")
            ))
        }
      } else {
        new EmptyRestApiResponse[T](HttpStatus.UNAUTHORIZED, Map(("error", s"User is unauthorized to delete resource with identifier $identifier")))
      }
    }).getOrElse(new EmptyRestApiResponse[T](HttpStatus.NOT_FOUND, Map(("error", s"Resource with identifier $identifier was not found"))))
  }
}

trait SupportForResourceRetrievalOverHttpGet[T <: AbstractEntity]
  extends SupportForReturningOneResourceOverHttpGet[T]
  with SupportForPagingResourcesViaHttpGet[T]

trait SupportForCrudOperationsOverHttp[T <: AbstractEntity]
  extends SupportForResourceRetrievalOverHttpGet[T]
  with SupportForCreatingOneResourceViaHttpPost[T]
  with SupportForDeletingOneResourceViaHttpDelete[T]



object Utils {
  def stackTraceAsString(th: Throwable): String = {
    val sw = new StringWriter();
    th.printStackTrace(new PrintWriter(sw));
    sw.toString
  }

  def getPreviousLink[T](page: Page[T], pageNumber: Int, size: Int): Option[(String, String)] = {
    if (page.hasPrevious) Option(("previous", s"/?page=${page.previousPageable().getOffset}&size=$size")) else None
  }

  def getNextLink[T](page: Page[T], pageNumber: Int, size: Int): Option[(String, String)] = {
    if (page.hasNext()) Option(("next", s"/?page=${page.nextPageable().getOffset}&size=$size")) else None
  }
}