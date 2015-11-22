//package com.roamy.web.resource
//
//import com.google.gson.Gson
//import com.roamy.{AllPassRestApiEntitlementsFilter, RestApiEntitlementFilter}
//import com.roamy.dao.api.{CategoryRepository, CitableRepository, CityRepository}
//import com.roamy.domain.{User, Category, City}
//import com.roamy.web.{SupportForReturningOneResourceOverHttpGet, SupportForResourceRetrievalOverHttpGet, CitableRestEndPoint}
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.{RequestMapping, RestController}
//
//import scala.util.Try
//import scala.reflect.runtime.universe._
///**
// * Created by amit on 10/17/15.
// */
//@RestController
//@RequestMapping(Array("/cats"))
//class CatResource extends CitableRestEndPoint[Category] {
//
//  val tag = typeTag[Category]
//
//  @Autowired
//  var categoryRepository: CategoryRepository = _
//
//
//  def deserializer(content: String): Option[Category] = {
//    Try(new Gson().fromJson(content, classOf[Category])).map(Option(_)).getOrElse(None)
//  }
//
//  override def repository = {
//    this.categoryRepository.asInstanceOf[CitableRepository[Category, _]]
//  }
//
//  //def entitlementsFilter: RestApiEntitlementFilter[Category] = new AllPassRestApiEntitlementsFilter[Category]{}
//
//}
