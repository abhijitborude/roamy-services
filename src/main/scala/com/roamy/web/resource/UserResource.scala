package com.roamy.web.resource

import com.google.gson.Gson
import com.roamy.dao.api.UserRepository
import com.roamy.domain.{City, User}
import com.roamy.web.SimpleRestEndPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.util.Try

/**
 * Created by amit on 10/17/15.
 */
@RestController
@RequestMapping(Array("/users"))
class UserResource extends SimpleRestEndPoint[User] {


  @Autowired
  var userRepository: UserRepository = _


  override def repository = {
    this.userRepository.asInstanceOf[JpaRepository[User, java.lang.Long]]
  }

  def deserializer(content: String): Option[User] = {
    Try(new Gson().fromJson(content, classOf[User])).map(Option(_)).getOrElse(None)
  }

}
