package com.roamy.web

import java.util.{Calendar, Optional, UUID, Date}

import com.google.gson.{JsonArray, Gson, JsonObject}
import com.roamy.domain.AbstractEntity
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import scala.collection.convert.wrapAsScala._


/**
 * Created by amit on 10/19/15.
 */
trait RestApiResponse[T <: AbstractEntity] {


  val status: HttpStatus
  final val responseCurrentAt: Date = Calendar.getInstance().getTime
  final val correlationId: UUID = UUID.randomUUID()

  val data: Option[Either[T, Iterable[T]]] = None
  val message: Option[Map[String, Object]] = None
  val links: Option[Map[String, String]] = None

  val serializer: T => JsonObject = (entity: T) => {
    val gson = new Gson
    val entityAsJson = gson.toJsonTree(entity).getAsJsonObject
    entityAsJson.addProperty("_type", entity.getClass.getSimpleName.toLowerCase)
    entityAsJson
  }


  lazy val serialize: JsonObject = {
    val gson = new Gson
    val resp = new JsonObject
    resp.addProperty("status", status.value)
    resp.add("_responseCurrentAt", gson.toJsonTree(responseCurrentAt))
    resp.addProperty("_correlationId", correlationId.toString)

    data match {
      case Some(d) =>
        d match {
          case Left(entity) =>
            resp.add("data", serializer(entity))
          case Right(entities) =>
            resp.add("data", entities.foldLeft(new JsonArray)((a, b) => {
              a.add(serializer(b))
              a
            }))
        }
      case None =>
    }

    message match {
      case Some(m) =>
        resp.add("message", m.foldLeft(new JsonObject)((b, c) => {
          b.add(c._1, gson.toJsonTree(c._2))
          b
        }))
      case None =>
    }

    links match {
      case Some(ls) =>
        resp.add("links", ls.foldLeft(new JsonObject)((b, c) => {
          b.addProperty(c._1, c._2)
          b
        }))
      case None =>
    }

    resp
  }

}

case class EmptyRestApiResponse[T <: AbstractEntity](override val status: HttpStatus, messages: Map[String, Object], override val links: Option[Map[String, String]] = None) extends RestApiResponse[T] {
  override val message = Option(messages)
}

case class SimpleRestApiResponse[T <: AbstractEntity](override val status: HttpStatus, entity: T, override val links: Option[Map[String, String]] = None, messages: Option[Map[String, Object]] = None) extends RestApiResponse[T] {
  override val message = messages
  override val data = Option(Left(entity))
}

case class CompositeRestApiResponse[T <: AbstractEntity](override val status: HttpStatus, entities: Iterable[T], override val links: Option[Map[String, String]] = None, override val message: Option[Map[String, Object]] = None) extends RestApiResponse[T] {
  override val data = Option(Right(entities))
}
