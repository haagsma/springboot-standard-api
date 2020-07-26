package br.com.haagsma.productcontrol.service.impl;

import br.com.haagsma.productcontrol.model.Token;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.repository.TokenRepository;
import br.com.haagsma.productcontrol.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token generateToken(User user) {

        String dateTime = new Date().getTime() + "";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);

        Token token = new Token();
        token.setToken(dateTime.substring(dateTime.length() - 5));
        token.setUser(user);
        token.setExpireDate(calendar.getTime());
        token.setIsValid(true);

        tokenRepository.save(token);

        return token;
    }

    public Boolean verifyToken(User user, String code) {
        Token token = tokenRepository.findByUserAndToken(user, code);
        if (token != null) {
            if (token.getIsValid() && token.getExpireDate().getTime() > new Date().getTime()) {
                token.setIsValid(false);
                tokenRepository.save(token);
                return true;
            }
        }
        return false;
    }
}
