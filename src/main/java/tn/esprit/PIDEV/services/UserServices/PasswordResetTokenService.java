package tn.esprit.PIDEV.services.UserServices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.RestControllers.AuthController.Param.AuthenticateResponse;
import tn.esprit.PIDEV.entities.User;

@Service
@AllArgsConstructor
public class PasswordResetTokenService {

    public void createPasswordResetTokenUser(User user , String token){

        AuthenticateResponse response = new AuthenticateResponse();
    }
}
