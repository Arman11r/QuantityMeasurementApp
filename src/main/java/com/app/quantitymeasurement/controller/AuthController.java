package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.UserEntity;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public String register(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }
    @GetMapping("/oauth-success")
    public String oauthSuccess(org.springframework.security.core.Authentication authentication) {

        org.springframework.security.oauth2.core.user.OAuth2User oauthUser =
                (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");

        UserEntity user = userRepository.findByUsername(email)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setUsername(email);
                    newUser.setPassword("google_user");
                    return userRepository.save(newUser);
                });


        String token = jwtService.generateToken(email);

        return token;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {

        Optional<UserEntity> existingUser = userRepository.findByUsername(user.getUsername());

        if (!existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username");
        }

        UserEntity dbUser = existingUser.get();

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
    }
}