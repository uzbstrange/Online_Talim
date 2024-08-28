package online_ta_lim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online_ta_lim.domain.UserType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {
    private String username;
    private String email;
    private String password;
    private UserType userType;
}
