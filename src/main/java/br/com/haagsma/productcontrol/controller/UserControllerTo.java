package br.com.haagsma.productcontrol.controller;

import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerTo {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public List<User> findAllUsers() {
        log.info("request find all users");
        return userService.findAll();
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) throws Exception {
        System.out.println(user.getEmail());
        userService.save(user);
        System.out.println(user.getId());
        return user;
    }
}
