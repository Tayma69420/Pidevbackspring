package tn.esprit.PIDEV.RestControllers.AuthController.Param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.PIDEV.entities.ERole;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {

    private String acessToken ;
    private String refreshToken;

    private Long id;
    public String username;
    public String email;
    public String tel;
    public String image;
    public ERole role ;
    public boolean activated = true ;

}
