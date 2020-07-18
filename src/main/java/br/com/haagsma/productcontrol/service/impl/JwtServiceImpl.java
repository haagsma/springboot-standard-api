package br.com.haagsma.productcontrol.service.impl;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.model.UserProfile;
import br.com.haagsma.productcontrol.repository.UserProfileRepository;
import br.com.haagsma.productcontrol.repository.UserRepository;
import br.com.haagsma.productcontrol.service.JwtService;
import br.com.haagsma.productcontrol.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private String secret = "a(WDU98wa9d8waW&&@@";
    private Algorithm algorithm = null;


    public String encode(User user, List<String> profiles) {

            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("user", user.getEmail())
                    .withClaim("profiles", profiles)
                    .sign(getAlgorithm());
    }

    public DecodedJWT decode(String token) {

        JWTVerifier verifier = JWT.require(getAlgorithm())
                .withIssuer("auth0")
                .build();
        return verifier.verify(token);
    }

    private Algorithm getAlgorithm() {
        if (algorithm == null)
            algorithm = Algorithm.HMAC256(secret);
        return algorithm;
    }

}
