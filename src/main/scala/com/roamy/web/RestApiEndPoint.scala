package com.roamy.web

import java.io.{PrintWriter, StringWriter}

import com.google.gson.{Gson, JsonObject}
import com.roamy.{AllPassRestApiEntitlementsFilter, RestApiEntitlementFilter}
import com.roamy.dao.api.CitableRepository
import com.roamy.domain.{CitableEntity, City, AbstractEntity}
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
trait RestEndPoint[T <: AbstractEntity] {

  def findByIdentifier(id: String): Option[T]

  def repository: JpaRepository[T, _]

  def deserializer(content: String): Option[T] = None


  val entitlementsFilter: RestApiEntitlementFilter[T] = new AllPassRestApiEntitlementsFilter[T] {}

  val serializer: T => JsonObject = (entity: T) => {
    val entityAsJson = new Gson().toJsonTree(entity).getAsJsonObject
    entityAsJson
  }

  @RequestMapping(value = Array("alt/{code}"), method = Array(RequestMethod.GET))
  def readOneAlt(@PathVariable code: String): ResponseEntity[T] = {
    Try(this.findByIdentifier(code).filter(entitlementsFilter.isEntitledToView(_))) match {
      case Success(Some(entity)) =>
        ResponseEntity.ok(entity)
      case Success(None) =>
        new ResponseEntity[T](Map(("error", s"Entity with identifier $code was not found.")).foldLeft(new HttpHeaders())((a, b) => {
          a.add(b._1, b._2)
          a
        }), HttpStatus.NOT_FOUND)
      case Failure(e) =>
        new ResponseEntity[T](Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", stackTraceAsString(e))
        ).foldLeft(new HttpHeaders())((a, b) => {
          a.add(b._1, b._2)
          a
        }), HttpStatus.INTERNAL_SERVER_ERROR)

    }
  }

  @RequestMapping(value = Array("/{code}"), method = Array(RequestMethod.GET))
  def readOne(@PathVariable code: String): RestApiResponse[T] = {
    Try(this.findByIdentifier(code).filter(entitlementsFilter.isEntitledToView(_))) match {
      case Success(Some(entity)) =>
        new SimpleRestApiResponse[T](HttpStatus.OK, entity)
      case Success(None) =>
        new EmptyRestApiResponse[T](HttpStatus.NOT_FOUND, Map(("error", s"Entity with identifier $code was not found.")))
      case Failure(e) =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", stackTraceAsString(e))
        ))
    }
  }

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET))
  def readAll(
               @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
               @RequestParam(value = "size", required = false, defaultValue = "100") size: Int): RestApiResponse[T] = {
    Try {

      val entities: Page[T] = repository.findAll(new PageRequest(page, size))

      val links = Seq(getNextLink(entities, page, size), getPreviousLink(entities, page, size)).filter(!_.isEmpty).map(_.get).toMap

      val viewable = entities.filter(entitlementsFilter.isEntitledToView(_))

      (links, viewable)
    } match {
      case Success((links: Map[String, String], entities: Iterable[T])) =>
        new CompositeRestApiResponse[T](HttpStatus.OK, entities, Option(links))
      case Failure(e) =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", stackTraceAsString(e))
        ))
    }
  }

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.POST))
  def createOne(@RequestBody entityJsonString: String): RestApiResponse[T] = {
    deserializer(entityJsonString) match {
      case Some(t: T) =>
        Try(repository.save(t)) match {
          case Success(createdEntity: T) =>
            new SimpleRestApiResponse[T](HttpStatus.CREATED, createdEntity)
          case Failure(e) =>
            new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
              ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
              ("developerErrorMessage", stackTraceAsString(e))
            ))
        }
      case None =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"Unable to deserialize from $entityJsonString"),
          ("developerErrorMessage", s"The deserializer function returned None. Does the resource override the deserializer function in the correct way!!")
        ))
    }
  }

  def getPreviousLink(page: Page[T], pageNumber: Int, size: Int): Option[(String, String)] = {
    if (page.hasPrevious) Option(("previous", s"/?page=${page.previousPageable().getOffset}&size=$size")) else None
  }

  def getNextLink(page: Page[T], pageNumber: Int, size: Int): Option[(String, String)] = {
    if (page.hasNext()) Option(("next", s"/?page=${page.nextPageable().getOffset}&size=$size")) else None
  }


  def stackTraceAsString(th: Throwable): String = {
    val sw = new StringWriter();
    th.printStackTrace(new PrintWriter(sw));
    sw.toString
  }


}

trait SimpleRestEndPoint[T <: AbstractEntity] extends RestEndPoint[T] {

  def repository: JpaRepository[T, java.lang.Long]

  def findByIdentifier(id: String): Option[T] = {
    Try(Option(repository.findOne(id.toLong))).getOrElse(None)
  }
}

trait CitableRestEndPoint[T <: CitableEntity] extends RestEndPoint[T] {

  def repository: CitableRepository[T, _]

  def findByIdentifier(code: String): Option[T] = {
    Option(repository.findByCode(code))
  }
}



