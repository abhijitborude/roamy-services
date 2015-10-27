package com.roamy.web.config

import java.util

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

/**
 * Created by amit on 10/24/15.
 */
@Configuration
class RoamyWebMvcConfig extends WebMvcConfigurationSupport {

  @Bean
  def customJackson2HttpMessageConverter: MappingJackson2HttpMessageConverter = {
    new RestApiResponseMessageConverter()
  }

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    converters.add(customJackson2HttpMessageConverter)
    super.addDefaultHttpMessageConverters(converters)
  }
}
