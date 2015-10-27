package com.roamy.web.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.roamy.web.RestApiResponse
import org.springframework.http.HttpOutputMessage
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

/**
 * Created by amit on 10/24/15.
 */
class RestApiResponseMessageConverter extends MappingJackson2HttpMessageConverter {
  override def writeInternal(obj: Object, outputMessage: HttpOutputMessage): Unit = {
    if (classOf[RestApiResponse[_]].isAssignableFrom(obj.getClass)) {
      val om: ObjectMapper = new ObjectMapper()
      super.writeInternal(om.readTree(obj.asInstanceOf[RestApiResponse[_]].serialize.toString), outputMessage)
    } else {
      super.writeInternal(obj, outputMessage)
    }
  }
}
