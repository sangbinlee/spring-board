package com.smartscore.board.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.Scopes;
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

	private Info apiInfo() {
		return new Info()
				.title("Swagger Documentation")
				.version("1.0")
				.termsOfService("http://swagger.io/terms/")
				.description("This project is developed to explore about spring doc configurations")
				.contact(
						new Contact()
								.name("Sallo Szrajbman")
								.email("www.baeldung.com")
								.url("salloszraj@gmail.com"))
				.license(
						new License()
								.name("Apache 2.0")
								.url("http://springdoc.org"));
	}

	private List<Server> servers() {
		Server server = new Server();
		Server devServer = new Server();
		Server prodServer = new Server();

		devServer.url(devUrl).description("Server URL in Development environment");
		prodServer.url(prodUrl).description("Server URL in Production environment");
		server.url("http://localhost:8080").description("API URL");
		return List.of(server, devServer, prodServer);
	}

	/**
	 * https://springdoc.org/#faq
	 *
	 * @return
	 */
	public OpenAPI openAPI() {

		final String securitySchemeName = "bearerAuth";
		// Bearer Authentication
		// .security(Collections.singletonList(securityRequirement))
		// .in(SecurityScheme.In.HEADER)
		SecurityRequirement securityRequirement = new SecurityRequirement()
				.addList(securitySchemeName)
				.addList(String.valueOf(new Scopes().addString("global", "access all APIs")));
		return new OpenAPI(SpecVersion.V30)
				.info(apiInfo())
				.externalDocs(new ExternalDocumentation())
				.servers(servers())
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(
						new Components()
								.addSecuritySchemes(
										securitySchemeName,
										new SecurityScheme()
												.name(securitySchemeName)
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")
								));
	}

	/**
	 * mapping v1 인 경우만 나오게 할때
	 *
	 * @return
	 */
	// @Profile({"!test && !dev"})
	@Bean
	@Profile({ "test || dev" })
	public GroupedOpenApi chatOpenApi() {
		String[] paths = { "/v1/**" };
		return GroupedOpenApi.builder().group("COUPLE API v1").pathsToMatch(paths).build();
	}

}
