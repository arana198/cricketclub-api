package com.cricketclub.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationCodeGrant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String securitySchemaOAuth2 = "oauth2schema";
	private static final String authorizationScopeGlobal = "global";
	private static final String authorizationScopeGlobalDesc ="accessEverything";

	@Value("${oauth.authzEndpoint}")
	private String swaggerOAuthUrl;
	@Value("${oauth.clientId}")
	private String oAuthClientId;
	@Value("${oauth.clientSecret}")
	private String oAuthClientSecret;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Cricket Club Management API")
                .description("This service provides a JSON API for managing cricket club related data")
                .termsOfServiceUrl("http://springfox.io")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .build();
    }

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cricketclub"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.securitySchemes(newArrayList(securitySchema()))
				.securityContexts(newArrayList(securityContext()));
	}

	private OAuth securitySchema() {
		AuthorizationScope authorizationScope = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobal);
		TokenEndpoint tokenEndpoint = new TokenEndpoint(swaggerOAuthUrl, "access_code");
		GrantType grantType = new AuthorizationCodeGrant(new TokenRequestEndpoint(swaggerOAuthUrl, oAuthClientId, oAuthClientSecret), tokenEndpoint);
		return new OAuth(securitySchemaOAuth2, newArrayList(authorizationScope), newArrayList(grantType));
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(
				new SecurityReference(securitySchemaOAuth2, authorizationScopes));
	}

}
