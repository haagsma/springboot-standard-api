package br.com.haagsma.productcontrol.repository;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.model.UserProfile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends PagingAndSortingRepository<UserProfile, Long> {

    List<UserProfile> findAllByUserId(Long id);

}
