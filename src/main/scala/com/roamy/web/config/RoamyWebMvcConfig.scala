package com.roamy.web.config

import java.time.LocalDate
import java.util

import com.fasterxml.classmate.TypeResolver
import com.roamy.web.RestApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.{PathSelectors, RequestHandlerSelectors}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Created by amit on 10/24/15.
 */

@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
class RoamyWebMvcConfig extends WebMvcAutoConfigurationAdapter {

  @Autowired var typeResolver: TypeResolver = _

  @Bean
  def customJackson2HttpMessageConverter: MappingJackson2HttpMessageConverter = {
    new RestApiResponseMessageConverter()
  }

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    converters.add(customJackson2HttpMessageConverter)
    super.configureMessageConverters(converters)
  }

  @Bean
  def petApi(): Docket = {
    new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any())
      .build()
      .pathMapping("/")
      //.directModelSubstitute(LocalDate.class, String.class)
      .genericModelSubstitutes(classOf[RestApiResponse[_]])
      /*.alternateTypeRules(
        newRule(typeResolver.resolve(DeferredResult.class,
      typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
      typeResolver.resolve(WildcardType.class)))*/
      .useDefaultResponseMessages(false)
    /*.globalResponseMessage(RequestMethod.GET,
      newArrayList(new ResponseMessageBuilder()
        .code(500)
        .message("500 message")
        .responseModel(new ModelRef("Error"))
        .build()))
    .securitySchemes(newArrayList(apiKey()))
    .securityContexts(newArrayList(securityContext()))
    .enableUrlTemplating(true)*/
  }


}
