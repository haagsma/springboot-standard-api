package br.com.haagsma.productcontrol.service.impl;

import br.com.haagsma.productcontrol.service.RoutesService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoutesServiceNoAuthenticationImpl implements RoutesService {

    @Override
    public List<String> getAllowedRoutes() {
        return Arrays.asList("/user/login", "/user/register");
    }
}
