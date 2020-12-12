package br.com.haagsma.productcontrol.service;

import br.com.haagsma.productcontrol.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findOne(User user);
    User save(User user) throws Exception;
    User register(User user) throws Exception;
    Page<User> findAll(Pageable pageable);
    List<User> findAll();
    User login(User user) throws Exception;
}
