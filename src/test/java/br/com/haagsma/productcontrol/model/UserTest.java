package br.com.haagsma.productcontrol.model;

import br.com.haagsma.productcontrol.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser() {
        User user = new User();
        user.setEmail("userTest@test.com");
        user.setPassword("123");
        this.userRepository.save(user);
        Assertions.assertThat(user.getId()).isNotNull();
    }
}
