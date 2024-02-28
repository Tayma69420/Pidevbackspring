package tn.esprit.PIDEV.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String tel;
    private String image;
    private List<String> roles;

}
