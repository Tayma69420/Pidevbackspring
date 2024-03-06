package tn.esprit.PIDEV.services.UserServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.Configurations.JwtService;
import tn.esprit.PIDEV.repositories.UserRepository;
import tn.esprit.PIDEV.RestControllers.AuthController.Param.AuthenticateResponse;
import tn.esprit.PIDEV.RestControllers.AuthController.Param.AuthenticationRequest;
import tn.esprit.PIDEV.RestControllers.AuthController.Param.RegesterRequest;
import tn.esprit.PIDEV.entities.User;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(RegesterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .tel(request.getTel())
                .image(request.getImage())
                .role(request.role.ROLE_ETUDIANT)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken((UserDetails) user);
        var refreshToken = jwtService.generateRefreshToken((UserDetails) user);
        return AuthenticateResponse.builder()
                .acessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
   /* @Override
    public ResponseEntity<?> authenticateUser(AuthenticationRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getTel(),
                        userDetails.getImage(),
                        roles));
    }*/
    /*public AuthenticateResponse authenticate(AuthenticationRequest request) {
        log.info(request.getUsername());
        log.info(request.getPassword());
        log.info(String.valueOf(userRepository.findByUsername(request.getUsername())));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        log.info(String.valueOf(user == null) + "----------------------------------------------------------");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + user.getUsername());
        var jwtToken = jwtService.generateToken( user);
        var refreshToken = jwtService.generateRefreshToken( user);
        return AuthenticateResponse.builder().
                acesstoken(jwtToken).
                refreshToken(refreshToken)
                .build();

    }*/
   public ResponseEntity<AuthenticateResponse> authenticate(AuthenticationRequest request) {
       log.info(request.getUsername());
       log.info(request.getPassword());
       log.info(String.valueOf(userRepository.findByUsername(request.getUsername())));

       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getUsername(),
                       request.getPassword()
               )
       );

       var user = userRepository.findByUsername(request.getUsername())
               .orElseThrow();

       log.info(String.valueOf(user == null) + "----------------------------------------------------------");
       log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + user.getUsername());

       var jwtToken = jwtService.generateToken(user);
       var refreshToken = jwtService.generateRefreshToken(user);

       // Créez l'objet AuthenticateResponse avec les informations nécessaires
       AuthenticateResponse response = AuthenticateResponse.builder()
               .acessToken(jwtToken)
               .refreshToken(refreshToken)
               .id(user.getId())
               .username(user.getUsername())
               .email(user.getEmail())
               .tel(user.getTel())
               .image(user.getImage())
               .role(user.getRole()) // Vous devez ajuster cela en fonction de la structure réelle des rôles de l'utilisateur
               .build();

       return ResponseEntity.ok(response);
   }

    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken,  user)) {
                var accessToken = jwtService.generateToken( user);
                var authResponse = AuthenticateResponse.builder()
                        .acessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
