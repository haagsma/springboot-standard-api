package br.com.haagsma.productcontrol.repository;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.Status;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {

}
