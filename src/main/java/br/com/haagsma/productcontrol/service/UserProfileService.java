package br.com.haagsma.productcontrol.service;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.User;

import java.util.List;

public interface UserProfileService {

    List<String> getProfilesTagByUser(User user);
}
