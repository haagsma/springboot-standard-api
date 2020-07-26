package br.com.haagsma.productcontrol.repository;

import br.com.haagsma.productcontrol.model.Token;
import br.com.haagsma.productcontrol.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token, Long> {

    List<Token> findByUser(User user);
    Token findByUserAndToken(User user, String token);

}
