package com.roamy.web

import java.util.{Calendar, Date, UUID}

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}
import com.roamy.domain.AbstractEntity
import org.springframework.http.HttpStatus


/**
 * Created by amit on 10/19/15.
 */
@JsonIgnoreProperties(Array("_status"))
trait RestApiResponse[T <: AbstractEntity] {

  final val _responseCurrentAt: Date = Calendar.getInstance().getTime
  final val _correlationId: UUID = UUID.randomUUID()


  val status: Int

  val message: Map[String, Object]
  val links: Map[String, String]

  def _data: Option[Either[T, Iterable[T]]]


}

case class EmptyRestApiResponse[T <: AbstractEntity](@JsonProperty("_status") val _status: HttpStatus, override val message: Map[String, Object] = Map(), override val links: Map[String, String] = Map()) extends RestApiResponse[T] {
  override def _data = None

  override val status = _status.value()
}

case class SimpleRestApiResponse[T <: AbstractEntity](@JsonProperty("_status") val _status: HttpStatus, data: T, override val links: Map[String, String] = Map(), override val message: Map[String, Object] = Map()) extends RestApiResponse[T] {
  override def _data = Option(Left(data))

  override val status = _status.value()
}

case class CompositeRestApiResponse[T <: AbstractEntity](@JsonProperty("_status") val _status: HttpStatus, data: Iterable[T], override val links: Map[String, String] = Map(), override val message: Map[String, Object] = Map()) extends RestApiResponse[T] {
  override def _data = Option(Right(data))

  override val status = _status.value()
}
