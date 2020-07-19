package br.com.haagsma.productcontrol.repository;

import br.com.haagsma.productcontrol.model.Status;
import br.com.haagsma.productcontrol.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends PagingAndSortingRepository<Status, Long> {

    Status findByTag(String tag);

}
