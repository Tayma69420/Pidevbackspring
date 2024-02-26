package tn.esprit.PIDEV.payload.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignupRequest {
    @NonNull
    private String username;

    @NonNull
    private String email;

    private Set<String> role;

    @NonNull
    private String password;

    @NonNull
    private String tel;

    @NonNull
    private String image;

}