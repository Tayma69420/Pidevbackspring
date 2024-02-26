package tn.esprit.PIDEV.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.PIDEV.payload.request.LoginRequest;
import tn.esprit.PIDEV.payload.request.SignupRequest;

public interface IUserService {
    public ResponseEntity<?> authenticateUser( LoginRequest loginRequest);
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest);
    public ResponseEntity<?> logoutUser();

}
