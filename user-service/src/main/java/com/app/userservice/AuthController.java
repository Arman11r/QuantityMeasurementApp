package com.app.userservice;

import com.app.userservice.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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