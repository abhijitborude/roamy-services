package com.roamy.web.resource

import com.roamy.dao.api.{CategoryRepository, CitableRepository, CityRepository}
import com.roamy.domain.{Category, City}
import com.roamy.web.CitableRestEndPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 * Created by amit on 10/17/15.
 */
@RestController
@RequestMapping(Array("/categories"))
class CategoryResource extends CitableRestEndPoint[Category] {


  @Autowired
  var categoryRepository: CategoryRepository = _


  def repository = {
    this.categoryRepository.asInstanceOf[CitableRepository[Category, _]]
  }

}
