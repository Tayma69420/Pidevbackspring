package tn.esprit.PIDEV.payload.request;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserInfoRequest {
    private String username;
    private String email;
    private String tel;
    private String image;
}
