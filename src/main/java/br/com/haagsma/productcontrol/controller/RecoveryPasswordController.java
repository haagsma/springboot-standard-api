package br.com.haagsma.productcontrol.controller;

import br.com.haagsma.productcontrol.config.JwtFilter;
import br.com.haagsma.productcontrol.dto.VerifyCodeRequest;
import br.com.haagsma.productcontrol.model.Status;
import br.com.haagsma.productcontrol.model.Token;
import br.com.haagsma.productcontrol.model.User;
import br.com.haagsma.productcontrol.repository.StatusRepository;
import br.com.haagsma.productcontrol.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequestMapping("/recovery")
@RestController
public class RecoveryPasswordController {

    private static Logger log = Logger.getLogger(String.valueOf(RecoveryPasswordController.class));

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody User user) {
        User userResponse = userService.findOne(user);
        try {
            if (userResponse == null) throw new Exception("Invalid Email!");

            Token token = tokenService.generateToken(userResponse);

            String subject = "Código para Recuperação de Senha";

            StringBuilder str = new StringBuilder();
            str.append("Prezado " + userResponse.getName() + "\n\n");
            str.append("Segue abaixo seu código para recuperar sua senha:\n\n");
            str.append("Código: " + token.getToken() + "\n\n");
            str.append("Atenciosamente equipe tecnica\n\n");
            String body = str.toString();

            mailService.sender(userResponse, subject, body);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest verifyCode) {
        User user = new User();
        user.setEmail(verifyCode.getEmail());
        user = userService.findOne(user);

        if (user == null) {
            String error = "Email inválido!";
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (tokenService.verifyToken(user, verifyCode.getCode())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            String error = "Código inválido ou expirado!";
            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody User userRequest) {

        try {
            User user = userService.findOne(userRequest);

            if (user == null) throw new Exception("Email inválido!");

            user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
            userService.save(user);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }


}
