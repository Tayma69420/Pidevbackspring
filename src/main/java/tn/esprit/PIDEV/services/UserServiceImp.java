package tn.esprit.PIDEV.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.PIDEV.entities.ERole;
import tn.esprit.PIDEV.entities.PasswordResetToken;
import tn.esprit.PIDEV.entities.Role;
import tn.esprit.PIDEV.entities.User;
import tn.esprit.PIDEV.payload.request.LoginRequest;
import tn.esprit.PIDEV.payload.request.SignupRequest;
import tn.esprit.PIDEV.payload.request.UserInfoRequest;
import tn.esprit.PIDEV.payload.response.MessageResponse;
import tn.esprit.PIDEV.payload.response.UserInfoResponse;
import tn.esprit.PIDEV.repositories.PasswordResetTokenRepository;
import tn.esprit.PIDEV.repositories.RoleRepository;
import tn.esprit.PIDEV.repositories.UserRepository;
import tn.esprit.PIDEV.security.jwt.JwtUtils;
import tn.esprit.PIDEV.security.services.UserDetailsImpl;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImp implements IUserService{

    JwtUtils jwtUtils;

    PasswordResetTokenRepository passwordTokenRepository;

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

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
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
 User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
         signUpRequest.getTel(),
                signUpRequest.getImage());


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_ETUDIANT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_EXPOSANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_ETUDIANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @Override
    public ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));

    }
    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }
    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserInfoResponse> getAllUsersInfo() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserInfoResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getTel(),
                        user.getImage(),
                        new ArrayList<>(user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toList()))
                ))
                .collect(Collectors.toList());
    }
    @Override
    public ResponseEntity<?> modifyUserDetails(Long userId, UserInfoRequest userInfoRequest) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        user.setUsername(userInfoRequest.getUsername());
        user.setEmail(userInfoRequest.getEmail());
        user.setTel(userInfoRequest.getTel());
        user.setImage(userInfoRequest.getImage());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User details updated successfully!"));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(userId);

        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @Override
    public String getUserPassword(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        return user.getPassword();
    }

    @Override
    public ResponseEntity<?> changeUserPassword(Long userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        String encryptedPassword = encoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
    }

}
