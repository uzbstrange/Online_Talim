package online_ta_lim.repository;

import online_ta_lim.domain.Group;
import online_ta_lim.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByTeacher(UserEntity teacher);
}
