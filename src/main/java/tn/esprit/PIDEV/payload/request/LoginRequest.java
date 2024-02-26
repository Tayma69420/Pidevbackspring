package tn.esprit.PIDEV.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {

    @NonNull
    private String username;

    @NonNull
    private String password;

}

