package br.com.haagsma.productcontrol.repository;

import static org.assertj.core.api.Assertions.*;

import br.com.haagsma.productcontrol.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void start() {
        User user = new User();
        user.setEmail("userTest@test.com");
        user.setPassword("123");
        this.userRepository.save(user);
    }

    @Test
    public void findByEmailReturnUser() {
        User user = this.userRepository.findByEmail("userTest@test.com");
        Assertions.assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("userTest@test.com");
    }

    @Test
    public void shouldFindAllUsers_returnAnUserList() {

        User user = new User();
        user.setEmail("userTest2@test.com");
        user.setPassword("1234");
        this.userRepository.save(user);

        List<User> users = this.userRepository.findAll();
        assertThat(users)
                .isNotEmpty()
                .hasSize(2);
    }
}
