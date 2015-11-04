package com.roamy.web.resource

import com.google.gson.Gson
import com.roamy.dao.api.{CitableRepository, CityRepository}
import com.roamy.domain.City
import com.roamy.web.{SupportForCreatingOneResourceViaHttpPut, CitableRestEndPoint}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.util.Try

/**
 * Created by amit on 10/17/15.
 */
@RestController
@RequestMapping(Array("/cities"))
class CityResource extends CitableRestEndPoint[City] with SupportForCreatingOneResourceViaHttpPut[City] {


  @Autowired
  var cityRepository: CityRepository = _


  override def repository = {
    this.cityRepository.asInstanceOf[CitableRepository[City, _]]
  }

  def deserializer(content: String): Option[City] = {
    Try(new Gson().fromJson(content, classOf[City])).map(Option(_)).getOrElse(None)
  }
}
