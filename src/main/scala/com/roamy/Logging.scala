package com.roamy

import org.slf4j.{LoggerFactory, Logger}

/**
 * Created by amit on 10/17/15.
 */
trait Logging {

  val logger: Logger = LoggerFactory.getLogger(classOf[Logging])
}
