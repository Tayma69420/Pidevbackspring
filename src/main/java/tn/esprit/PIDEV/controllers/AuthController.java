package tn.esprit.PIDEV.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.PIDEV.payload.request.LoginRequest;
import tn.esprit.PIDEV.payload.request.SignupRequest;
import tn.esprit.PIDEV.services.UserServiceImp;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    UserServiceImp userServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userServiceImp.authenticateUser(loginRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
       return userServiceImp.logoutUser();
        }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
        return userServiceImp.registerUser(signUpRequest);
    }
}