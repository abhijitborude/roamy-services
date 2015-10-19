package com.roamy.web

import java.io.{PrintWriter, StringWriter}

import com.google.gson.{Gson, JsonObject}
import com.roamy.RestApiEntitlementFilter
import com.roamy.domain.AbstractEntity
import org.springframework.data.domain.{Pageable, Page, PageRequest}
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{RequestParam, PathVariable, RequestMethod, RequestMapping}

import scala.util.{Try, Failure, Success}
import scala.collection.convert.wrapAsScala._
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{universe => ru}

/**
 * Created by amit on 10/18/15.
 */
trait RestApiEndPoint[T <: AbstractEntity] {

  type K = java.lang.Long

  val serializer: T => JsonObject = (entity: T) => {
    val entityAsJson = new Gson().toJsonTree(entity).getAsJsonObject
    entityAsJson
  }
  //val deserializer : JsonObject => T
  val entitlementsFilter: RestApiEntitlementFilter[T]

  def repository: JpaRepository[T, K]

  @RequestMapping(value = Array("/{id}"), method = Array(RequestMethod.GET))
  def readOne(@PathVariable id: K): String = {
    Try(Option(repository.findOne(id)).filter(entitlementsFilter.isEntitledToView(_))) match {
      case Success(Some(entity)) =>
        new SimpleRestApiResponse[T](HttpStatus.OK, entity).serialize.toString
      case Success(None) =>
        new EmptyRestApiResponse[T](HttpStatus.NOT_FOUND, Map(("error", s"Entity with id $id was not found."))).serialize.toString
      case Failure(e) =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", stackTraceAsString(e))
        )).serialize.toString
    }
  }

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET))
  def readAll(@RequestParam(value = "limit", required = false) limit: Int = 0, @RequestParam(value = "offset", required = false) offset: Int = 100): String = {
    Try {

      val page = repository.findAll(new PageRequest(offset, limit))

      val links = Seq(getNextLink(page, limit, offset), getPreviousLink(page, limit, offset)).filter(!_.isEmpty).map(_.get).toMap

      val viewable = page.filter(entitlementsFilter.isEntitledToView(_))

      (links, viewable)
    } match {
      case Success((links: Map[String, String], entities: Iterable[T])) =>
        new CompositeRestApiResponse[T](HttpStatus.OK, entities, Option(links)).serialize.toString
      case Failure(e) =>
        new EmptyRestApiResponse[T](HttpStatus.INTERNAL_SERVER_ERROR, Map(
          ("error", s"An error occurred while processing this request. Please use the developerErrorMessage to resolve and fix the issue."),
          ("developerErrorMessage", stackTraceAsString(e))
        )).serialize.toString
    }
  }

  def getPreviousLink(page: Page[T], limit: Int, offset: Int): Option[(String, String)] = {
    if (page.hasPrevious) Option(("previous", s"/?offset=${page.previousPageable().getOffset}&limit=$limit")) else None
  }

  def getNextLink(page: Page[T], limit: Int, offset: Int): Option[(String, String)] = {
    if (page.hasNext()) Option(("next", s"/?offset=${page.nextPageable().getOffset}&limit=$limit")) else None
  }


  def stackTraceAsString(th: Throwable): String = {
    val sw = new StringWriter();
    th.printStackTrace(new PrintWriter(sw));
    sw.toString
  }
}
