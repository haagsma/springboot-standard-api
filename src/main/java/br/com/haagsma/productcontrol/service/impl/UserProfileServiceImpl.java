package br.com.haagsma.productcontrol.service.impl;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.model.UserProfile;
import br.com.haagsma.productcontrol.repository.UserProfileRepository;
import br.com.haagsma.productcontrol.repository.UserRepository;
import br.com.haagsma.productcontrol.service.JwtService;
import br.com.haagsma.productcontrol.service.UserProfileService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<String> getProfilesTagByUser(User user) {
        List<UserProfile> userProfiles = userProfileRepository.findAllByUserId(user.getId());
        List<String> profiles = new ArrayList<>();
        if (userProfiles.size() > 0)
            for (UserProfile userProfile: userProfiles) profiles.add(userProfile.getProfile().getTag());
        return profiles;
    }
}
