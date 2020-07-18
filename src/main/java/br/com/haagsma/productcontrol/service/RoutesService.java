package br.com.haagsma.productcontrol.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoutesService {
    public List<String> getAllowedRoutes();
}
