package com.project.FleetManagement.controller;

import com.project.FleetManagement.entity.User;
import com.project.FleetManagement.repository.UserRepository;
import com.project.FleetManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> request){
        String username = request.get("username");
        String password = request.get("password");

        User user=userRepository.findByusername(username).orElse(null);

        if(user!=null && passwordEncoder.matches(password,user.getPassword())){
            String token=jwtUtil.generateToken(username,user.getRole());

            Map<String,Object> response =new HashMap<>();
            response.put("token",token);
            response.put("username",username);
            response.put("role",user.getRole());
            response.put("message","Login Successful");

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message","Invalid username or password"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (userRepository.existsByusername(username)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username already exists"));
        }

        User user = new User(username, passwordEncoder.encode(password), "ADMIN");
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }



}
