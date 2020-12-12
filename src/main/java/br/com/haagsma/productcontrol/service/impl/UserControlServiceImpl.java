package br.com.haagsma.productcontrol.service.impl;

import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.repository.UserRepository;
import br.com.haagsma.productcontrol.service.JwtService;
import br.com.haagsma.productcontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserControlServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public User findOne(User user) {
        if (user.getEmail() != null) {
            return userRepository.findByEmail(user.getEmail());
        } else {
            return userRepository.findById(user.getId()).get();
        }
    }

    @Override
    public User save(User user) throws Exception {
        return userRepository.save(user);
    }
    @Override
    public User register(User user) throws Exception {
        if (user.getId() == null && userRepository.findByEmail(user.getEmail()) != null)
            throw new Exception("Email already exists");
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        System.out.println("findall");
        return userRepository.findAll(pageable);
    }
    @Override
    public List<User> findAll() {
        System.out.println("findall");
        return userRepository.findAll();
    }

    @Override
    public User login(User user) throws Exception {

        if (user.getEmail() == null || user.getEmail().isEmpty()) throw new Exception("Email cannot be empty");

        if (user.getPassword() == null || user.getPassword().isEmpty()) throw new Exception("Password cannot be empty");

        User userValidation = userRepository.findByEmail(user.getEmail());

        if (userValidation == null) throw new Exception("Email doesn't exists");

        if (!userValidation.getStatus().getTag().equals("ACTIVE")) throw new Exception("User isn't active, please contact the support or administrator");

        if (!new BCryptPasswordEncoder().matches(user.getPassword(), userValidation.getPassword())) throw new Exception("Password incorrect, try again!");

        return userValidation;
    }
}
