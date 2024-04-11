package com.smartscore.board.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

//@OpenAPIDefinition(info = @Info(title = "Couple App", description = "couple app api명세", version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	  @Value("${bezkoder.openapi.dev-url}")
	  private String devUrl;

	  @Value("${bezkoder.openapi.prod-url}")
	  private String prodUrl;
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build();
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("test").description("description").version("1.0.0").build();
//	}

//	@Bean
//	public GroupedOpenApi publicApi() {
//		return GroupedOpenApi.builder().group("springshop-public").pathsToMatch("/public/**").build();
//	}

//	@Bean
//	public GroupedOpenApi adminApi() {
//		return GroupedOpenApi.builder().group("springshop-admin").pathsToMatch("/admin/**")
//				.addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class)).build();
//	}

//	  @Bean
//	  public OpenAPI springShopOpenAPI() {
//	      return new OpenAPI()
//	              .info(new Info().title("SpringShop API")
//	              .description("Spring shop sample application")
//	              .version("v0.0.1")
//	              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//	              .externalDocs(new ExternalDocumentation()
//	              .description("SpringShop Wiki Documentation")
//	              .url("https://springshop.wiki.github.org/docs"));
//	  }

	/**
	 * swagger 화면에 authorize 버튼 보이게 할때 사용 :  Available authorizations
	 * @return
	 */
//	@Bean
//	public OpenAPI openAPI() {
//		SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
//				.bearerFormat("JWT").in(SecurityScheme.In.HEADER).name("Authorization");
//		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
//
//		return new OpenAPI()
//				.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
//				.security(Arrays.asList(securityRequirement))
//				;
//	}
	@Bean
	public OpenAPI openAPI() {


		Server devServer = new Server();
	    devServer.setUrl(devUrl);
	    devServer.setDescription("Server URL in Development environment");

	    Server prodServer = new Server();
	    prodServer.setUrl(prodUrl);
	    prodServer.setDescription("Server URL in Production environment");







	    // https://springdoc.org/#faq
	    return new OpenAPI()
	    		.addSecurityItem(
	    				new SecurityRequirement().addList("Bearer Authentication")
	    		)
	    		.components(
	    				new Components()
		    				.addSecuritySchemes(
	    						"Bearer Authentication"
	    						, new SecurityScheme()
	    							.type(SecurityScheme.Type.HTTP)
	    				        	.bearerFormat("JWT")
	    				        	.scheme("bearer")
					        )
				)
	    		.info(
	    				new Info()
			        		.title("My REST API")
			        		.version("1.0")
			        		.description("This is a sample server Petstore server. You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). For this sample, you can use the api key `special-key` to test the authorization filters.")
//			        		.description("Some custom description of API.")
			        		.termsOfService("http://swagger.io/terms/")
			        		.contact(
			        				new Contact()
			        				.name("Sallo Szrajbman")
			        				.email( "www.baeldung.com")
			        				.url("salloszraj@gmail.com")
			                )
				            .license(
				            		new License()
				            			.name("Apache 2.0")
				            			.url("http://springdoc.org")
		            		)
	            )
	    		.servers(List.of(devServer, prodServer));
	}

	/**
	 * mapping vi 인 경우만 나오게 할때
	 * @return
	 */
	@Profile({"test || dev"})
//    @Profile({"!test && !dev"})
	@Bean
	public GroupedOpenApi chatOpenApi() {
		String[] paths = { "/v1/**" };

		return GroupedOpenApi.builder().group("COUPLE API v1").pathsToMatch(paths).build();
	}


}
