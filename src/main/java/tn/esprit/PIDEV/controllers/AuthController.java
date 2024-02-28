package tn.esprit.PIDEV.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.PIDEV.payload.request.LoginRequest;
import tn.esprit.PIDEV.payload.request.SignupRequest;
import tn.esprit.PIDEV.payload.request.UserInfoRequest;
import tn.esprit.PIDEV.payload.response.UserInfoResponse;
import tn.esprit.PIDEV.services.UserServiceImp;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
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
    @GetMapping("/allUsersInfo")
    public ResponseEntity<List<UserInfoResponse>> getAllUsersInfo() {
        List<UserInfoResponse> usersInfo = userServiceImp.getAllUsersInfo();
        return ResponseEntity.ok(usersInfo);
    }
    @PutMapping("/modifyUserDetails/{userId}")
    public ResponseEntity<?> modifyUserDetails(@PathVariable Long userId, @RequestBody UserInfoRequest userInfoRequest) {
        ResponseEntity<?> response = userServiceImp.modifyUserDetails(userId, userInfoRequest);
        return response;
    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        ResponseEntity<?> response = userServiceImp.deleteUser(userId);
        return response;
    }
    @GetMapping("/getPassword/{userId}")
    public ResponseEntity<?> getUserPassword(@PathVariable Long userId) {
        String password = userServiceImp.getUserPassword(userId);
        if (password == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(password);
    }

    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<?> changeUserPassword(@PathVariable Long userId, @RequestBody String newPassword) {
        ResponseEntity<?> response = userServiceImp.changeUserPassword(userId, newPassword);
        return response;
    }
}