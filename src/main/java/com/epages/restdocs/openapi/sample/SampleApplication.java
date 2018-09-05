package com.epages.restdocs.openapi.sample;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@SpringBootApplication
@EnableSwagger2
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Configuration
	static class ValidationConfiguration extends RepositoryRestConfigurerAdapter {

		@Override
		public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
			validatingListener.addValidator("beforeCreate", validator());
			validatingListener.addValidator("beforeSave", validator());
		}

		@Bean
		@Primary
		public Validator validator() {
			return new LocalValidatorFactoryBean();
		}

		@Autowired
		private TypeResolver typeResolver;

		@Bean
		public Docket myApi() {
			return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(new ApiInfo(
							"My API",
							"some more",
							"1.0.0", null, null, null, null, Collections.emptyList()))
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build()
					.pathMapping("/")
					.directModelSubstitute(LocalDate.class, String.class)
					.genericModelSubstitutes(ResponseEntity.class)
					.alternateTypeRules(
							newRule(typeResolver.resolve(DeferredResult.class,
									typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
									typeResolver.resolve(WildcardType.class)))
					.useDefaultResponseMessages(false)
					.globalResponseMessage(RequestMethod.GET,
							newArrayList(new ResponseMessageBuilder()
									.code(500)
									.message("500 message")
									.responseModel(new ModelRef("Error"))
									.build()))
					.enableUrlTemplating(true)
					;
		}
	}
}
