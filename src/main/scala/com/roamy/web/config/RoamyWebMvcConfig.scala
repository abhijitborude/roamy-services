package com.roamy.web.config

import com.fasterxml.classmate.TypeResolver
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.roamy.web.RestApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.{Bean, Configuration}
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
class RoamyWebMvcConfig {

  @Autowired var typeResolver: TypeResolver = _

  @Bean
  def jacksonScalaModule: DefaultScalaModule = {
    DefaultScalaModule
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
