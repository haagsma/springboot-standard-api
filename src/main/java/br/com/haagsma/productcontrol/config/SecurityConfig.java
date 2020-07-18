package br.com.haagsma.productcontrol.config;

import br.com.haagsma.productcontrol.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoutesService routesService;

    public void configure(HttpSecurity httpSecurity) throws Exception {

        String[] routes = new String[routesService.getAllowedRoutes().size()];
        routes = routesService.getAllowedRoutes().toArray(routes);

        httpSecurity.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                    .antMatchers(routes).permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .addFilterBefore(new JwtFilter(), BasicAuthenticationFilter.class);
    }
}
