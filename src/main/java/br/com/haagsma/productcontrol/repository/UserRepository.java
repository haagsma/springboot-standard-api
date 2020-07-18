package br.com.haagsma.productcontrol.repository;

import br.com.haagsma.productcontrol.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByEmail(String email);
}
