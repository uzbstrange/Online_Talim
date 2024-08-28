package online_ta_lim.controller;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.UserEntity;
import online_ta_lim.domain.UserType;
import online_ta_lim.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return new ApiResponse<>("Successfully fetched all users", true, users);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ApiResponse<>("User found", true, user.get());
        } else {
            return new ApiResponse<>("User not found", false);
        }
    }

    @PostMapping
    public ApiResponse<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return new ApiResponse<>("User created successfully", true, createdUser);
    }

    @GetMapping("/username/{username}")
    public ApiResponse<UserEntity> findByUsername(@PathVariable String username) {
        Optional<UserEntity> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return new ApiResponse<>("User found", true, user.get());
        } else {
            return new ApiResponse<>("User not found", false);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse<>("User deleted successfully", true);
    }

    @GetMapping("/type/{userType}")
    public ApiResponse<List<UserEntity>> getUsersByType(@PathVariable UserType userType) {
        List<UserEntity> users = userService.getUsersByType(userType);
        return new ApiResponse<>("Successfully fetched users by type", true, users);
    }
}
