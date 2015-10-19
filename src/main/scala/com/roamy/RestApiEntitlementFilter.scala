package com.roamy

import com.roamy.domain.AbstractEntity

/**
 * Created by amit on 10/17/15.
 */
trait RestApiEntitlementFilter[T <: AbstractEntity] {

  def isEntitledToView(t: T): Boolean

  def isEntitledToModify(t: T): Boolean

  def isEntitledToCreate(t: T): Boolean

  def isEntitledToDelete(t: T): Boolean


}

trait AllPassRestApiEntitlementsFilter[T <: AbstractEntity] extends RestApiEntitlementFilter[T] {
  def isEntitledToView(t: T): Boolean = true

  def isEntitledToDelete(t: T): Boolean = true

  def isEntitledToCreate(t: T): Boolean = true

  def isEntitledToModify(t: T): Boolean = true
}