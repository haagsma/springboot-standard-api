package br.com.haagsma.productcontrol.service;

import br.com.haagsma.productcontrol.model.Token;
import br.com.haagsma.productcontrol.model.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    Token generateToken(User user);
    Boolean verifyToken(User user, String token);
}
