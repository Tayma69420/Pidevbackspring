package tn.esprit.PIDEV.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.payload.request.LoginRequest;
import tn.esprit.PIDEV.payload.request.SignupRequest;
import tn.esprit.PIDEV.payload.request.UserInfoRequest;
import tn.esprit.PIDEV.payload.response.UserInfoResponse;

import java.util.List;

public interface IUserService {
    public ResponseEntity<?> authenticateUser( LoginRequest loginRequest);
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest);
    public ResponseEntity<?> logoutUser();

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    List<UserInfoResponse> getAllUsersInfo();

    ResponseEntity<?> modifyUserDetails(Long userId, UserInfoRequest userInfoRequest);


    ResponseEntity<?> deleteUser(Long userId);


    String getUserPassword(Long userId);

    ResponseEntity<?> changeUserPassword(Long userId, String newPassword);
}
