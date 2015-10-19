package com.roamy.web

import com.google.gson.{Gson, JsonObject}
import com.roamy.AllPassRestApiEntitlementsFilter
import com.roamy.dao.api.CityRepository
import com.roamy.domain.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 * Created by amit on 10/17/15.
 */
@RestController
@RequestMapping(Array("/cities"))
class CityResource extends RestApiEndPoint[City] {

  val entitlementsFilter = new AllPassRestApiEntitlementsFilter[City] {}


  @Autowired
  var cityRepository: CityRepository = _


  def repository = {
    this.cityRepository.asInstanceOf[JpaRepository[City, java.lang.Long]]
  }

}
