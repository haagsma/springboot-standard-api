package br.com.haagsma.productcontrol.config;

import br.com.haagsma.productcontrol.service.JwtService;
import br.com.haagsma.productcontrol.service.RoutesService;
import br.com.haagsma.productcontrol.service.impl.JwtServiceImpl;
import br.com.haagsma.productcontrol.service.impl.RoutesServiceNoAuthenticationImpl;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JwtFilter extends GenericFilterBean {

    private static Logger log = Logger.getLogger(String.valueOf(JwtFilter.class));

    private JwtService jwtService = new JwtServiceImpl();

    private RoutesService routesService = new RoutesServiceNoAuthenticationImpl();

    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        try {
            if (!routesService.getAllowedRoutes()
                    .contains(req.getRequestURI())) {
                Authentication authentication = verifyRequest(req);
                if (authentication != null) SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.warning(e.getMessage());
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed!");

        }
    }

    private Authentication verifyRequest(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        log.info("Authentication!");

        if (token != null) {
            DecodedJWT decodedJWT = jwtService.decode(token);
            if (decodedJWT != null) {
                log.info("User: " + decodedJWT.getClaim("user").asString());
                List<GrantedAuthority> collection = new ArrayList<>();
                for (String profile : decodedJWT.getClaim("profiles").asList(String.class)) {
                    collection.add(new SimpleGrantedAuthority(profile));
                }
                return new UsernamePasswordAuthenticationToken(decodedJWT.getClaim("user"), null, collection);
            }
        }

        throw new Exception("Token invalid!");
    }
}
