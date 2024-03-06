package tn.esprit.PIDEV.RestControllers.AuthController.Param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetUtil {
    private String username   ;
    private String newPassword ;
    private String confirmPassword ;
}
