package kiwi.blog.common.config.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

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


/*    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous().disable()
                .authorizeRequests()
                .antMatchers("/oauth/token/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
*/
}
