package online_ta_lim.repository;

import online_ta_lim.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsernameAndPassword(String username,String password);
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByUsernameIgnoreCase(String username);
}
