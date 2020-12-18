package br.com.haagsma.productcontrol.config;

import br.com.haagsma.productcontrol.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoutesService routesService;

    public void configure(HttpSecurity httpSecurity) throws Exception {

        String[] routes = new String[routesService.getAllowedRoutes().size()];
        routes = routesService.getAllowedRoutes().toArray(routes);

        httpSecurity.csrf().disable()
                .cors().configurationSource(request -> corsConfiguration())
                .and()
                .authorizeRequests()
                    .antMatchers(routes).permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .addFilterBefore(new JwtFilter(), BasicAuthenticationFilter.class);
    }

    private CorsConfiguration corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*", "http://localhost:3000"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization", "Accept"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PUT"));
        configuration.setAllowCredentials(false);

        return configuration;
    }
}
