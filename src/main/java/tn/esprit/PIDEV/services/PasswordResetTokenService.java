package tn.esprit.PIDEV.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.entities.PasswordResetToken;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.repositories.PasswordResetTokenRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private static final int EXPIRATION = 60 * 24;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserServiceImp userService;



    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }


}
