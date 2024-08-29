package online_ta_lim.domain;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class CustomUserDetails extends User {
    private final UserEntity user;

    public CustomUserDetails(UserEntity user) {
        super(user.getUsername(), user.getPassword(), true, true, true, true, new ArrayList<>());
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
