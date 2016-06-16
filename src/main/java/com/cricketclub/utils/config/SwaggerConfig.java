package com.cricketclub.utils.config;


import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.AuthorizationScope;
import com.mangofactory.swagger.models.dto.AuthorizationType;
import com.mangofactory.swagger.models.dto.GrantType;
import com.mangofactory.swagger.models.dto.ImplicitGrant;
import com.mangofactory.swagger.models.dto.LoginEndpoint;
import com.mangofactory.swagger.models.dto.builder.OAuthBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				//Root level documentation
				.apiInfo(new ApiInfo(
						"My Service JSON API",
						"This service provides a JSON API",
						null,
						null,
						null,
						null
				))
				.useDefaultResponseMessages(false)
				//Map the specific URL patterns into Swagger
				.includePatterns("/my")
				.authorizationTypes(getAuthorizationTypes())
				.ignoredParameterTypes(OAuth2Authentication.class, Principal.class);
	}

	private List<AuthorizationType> getAuthorizationTypes()
	{
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
