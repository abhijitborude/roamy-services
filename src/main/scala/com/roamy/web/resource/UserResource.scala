package com.roamy.web.resource

import com.roamy.dao.api.UserRepository
import com.roamy.domain.User
import com.roamy.web.SimpleRestEndPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 * Created by amit on 10/17/15.
 */
@RestController
@RequestMapping(Array("/users"))
class UserResource extends SimpleRestEndPoint[User] {


  @Autowired
  var userRepository: UserRepository = _


  def repository = {
    this.userRepository.asInstanceOf[JpaRepository[User, java.lang.Long]]
  }

}
