package online_ta_lim.service;

import online_ta_lim.domain.UserEntity;
import online_ta_lim.domain.UserType;
import online_ta_lim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserEntity> getUsersByType(UserType userType) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUserType().equals(userType))
                .toList();
    }
}
