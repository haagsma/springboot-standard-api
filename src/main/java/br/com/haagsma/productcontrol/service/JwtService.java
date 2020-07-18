package br.com.haagsma.productcontrol.service;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.model.UserProfile;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JwtService {

    DecodedJWT decode(String token);
    String encode(User user, List<String> profiles);
}
