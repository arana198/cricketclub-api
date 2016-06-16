package com.cricketclub.utils.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.*;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.models.dto.builder.OAuthBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mangofactory.swagger.models.alternates.Alternates.newRule;

@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig {

	@Value("${oauth.authzEndpoint}")
	private String swaggerOAuthUrl;

	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Cricket Club Management API")
                .description("This service provides a JSON API for managing cricket club related data")
                .termsOfServiceUrl("http://springfox.io")
                .contact("springfox")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .build();
    }

    @Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				//Root level documentation
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				//Map the specific URL patterns into Swagger
				.includePatterns("/my")
				.authorizationTypes(getAuthorizationTypes())
				.ignoredParameterTypes(OAuth2Authentication.class, Principal.class);
	}

	private List<AuthorizationType> getAuthorizationTypes() {
		List<AuthorizationType> authorizationTypes = new ArrayList<>();
		List<AuthorizationScope> scopes = new ArrayList<>();
		scopes.add(new AuthorizationScope("my-resource.read","Read access on the API"));

		List<GrantType> grantTypes = new ArrayList<>();
		ImplicitGrant implicitGrant = new ImplicitGrant(new LoginEndpoint(swaggerOAuthUrl),"access_code");
		grantTypes.add(implicitGrant);

		AuthorizationType oauth = new OAuthBuilder()
				.scopes(scopes)
				.grantTypes(grantTypes)
				.build();
		authorizationTypes.add(oauth);
		return authorizationTypes;
	}

}
