package com.roamy

import org.slf4j.{Logger, LoggerFactory}

/**
 * Created by amit on 10/17/15.
 */
trait Logging {

  val logger: Logger = LoggerFactory.getLogger(classOf[Logging])
}
