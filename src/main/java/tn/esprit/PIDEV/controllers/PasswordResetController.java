package tn.esprit.PIDEV.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.payload.response.GenericResponse;
import tn.esprit.PIDEV.repositories.UserRepository;
import tn.esprit.PIDEV.services.EmailService;
import tn.esprit.PIDEV.utilsUser.EmailUtil;
import tn.esprit.PIDEV.services.PasswordResetTokenService;
import tn.esprit.PIDEV.services.UserServiceImp;
import tn.esprit.PIDEV.utilsUser.UrlUtil;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import java.util.Locale;
import java.util.UUID;
@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class PasswordResetController {

    UserServiceImp userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messages;
    @Autowired
    private Environment env;

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail)throws Exception {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new Exception();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        mailSender.send(constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }
    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
