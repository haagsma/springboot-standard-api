package br.com.haagsma.productcontrol.controller;

import br.com.haagsma.productcontrol.model.Profile;
import br.com.haagsma.productcontrol.model.Status;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.model.UserProfile;
import br.com.haagsma.productcontrol.repository.StatusRepository;
import br.com.haagsma.productcontrol.service.JwtService;
import br.com.haagsma.productcontrol.service.UserProfileService;
import br.com.haagsma.productcontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        User user = new User();
        user.setName("jho");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/find-one")
    public ResponseEntity<User> findOne(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "email", required = false) String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return new ResponseEntity<User>(userService.findOne(user), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User user) {
        try {
            if (user.getName() == null) throw new Exception("Name cannot be empty");
            if (user.getEmail() == null) throw new Exception("Email cannot be empty");
            if (user.getPassword() == null) throw new Exception("Password cannot be empty");
            if (user.getStatus() == null || user.getStatus().getId() == null) throw new Exception("Status cannot be empty or incorrect");

            if (user.getPassword().length() < 60)
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.save(user);
            user.setPassword(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (user.getName() == null) throw new Exception("Name cannot be empty");
            if (user.getEmail() == null) throw new Exception("Email cannot be empty");
            if (user.getPassword() == null) throw new Exception("Password cannot be empty");

            Status status = statusRepository.findByTag("ACTIVE");
            user.setStatus(status);

            if (user.getPassword().length() < 60)
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.save(user);
            user.setPassword(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User userResponse = userService.login(user);

            List<String> profiles = userProfileService.getProfilesTagByUser(userResponse);

            String token = jwtService.encode(userResponse, profiles);
            userResponse.setPassword(null);

            Map<String, Object> res = new HashMap<>();
            res.put("user", userResponse);
            res.put("token", token);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
