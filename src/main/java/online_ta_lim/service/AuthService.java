package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.UserEntity;
import online_ta_lim.domain.UserType;
import online_ta_lim.dto.UserLoginDto;
import online_ta_lim.dto.UserRegistrationDto;
import online_ta_lim.provider.JwtProvider;
import online_ta_lim.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public ApiResponse login(UserLoginDto dto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(dto.getUsername());

        if (optionalUser.isEmpty()) {
            return new ApiResponse("No user registered with this username: " + dto.getUsername(), false);
        }

        UserEntity user = optionalUser.get();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return new ApiResponse("Invalid username or password", false);
        }

        String token = jwtProvider.generateToken(user);

        return new ApiResponse("Successfully logged in", true, token);
    }


    public ApiResponse<UserEntity> register(UserRegistrationDto dto) {
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            return new ApiResponse("This email already registered. Try another account", false);
        }
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            return new ApiResponse("This username already registered. Try username", false);
        }
        if (dto.getPassword().length() < 6) {
            return new ApiResponse("Password must be at least 6 characters long", false);
        }
        if (dto.getUserType() != UserType.STUDENT && dto.getUserType() != UserType.TEACHER) {
            return new ApiResponse("Invalid user type.", false);
        }
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserType(dto.getUserType());
        userRepository.save(user);

        return new ApiResponse("Successfully registered", true, user);
    }
}
