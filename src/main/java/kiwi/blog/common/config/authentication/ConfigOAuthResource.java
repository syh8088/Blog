package kiwi.blog.common.config.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Slf4j
@Configuration
@EnableResourceServer
public class ConfigOAuthResource extends ResourceServerConfigurerAdapter {

/*
    @Override
    public void configure(final HttpSecurity http) throws Exception {
    		http.
    		anonymous().disable()
    		.authorizeRequests()
    		.antMatchers("/oauth/token/**").permitAll();
    }

    @Bean
    public WebResponseExceptionTranslator exceptionTranslator() {
        return new CustomRestErrorWebResponseExceptionTranslator();
    }
*/


    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous().disable()
                .authorizeRequests()
                .antMatchers("/oauth/token/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

}
