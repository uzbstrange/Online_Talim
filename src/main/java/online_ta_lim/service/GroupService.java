package online_ta_lim.service;

import jakarta.transaction.Transactional;
import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Group;
import online_ta_lim.domain.UserEntity;
import online_ta_lim.domain.UserType;
import online_ta_lim.repository.GroupRepository;
import online_ta_lim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public ApiResponse<Group> createGroup(String groupName) {
        UserEntity currentUser = userService.getCurrentUser();
        if (currentUser.getUserType() != UserType.TEACHER) {
            return new ApiResponse<>("Only teachers can create groups", false);
        }
        Group group = new Group(groupName, currentUser);
        Group savedGroup = groupRepository.save(group);
        return new ApiResponse<>("Group created successfully", true, savedGroup);
    }

    public ApiResponse<Group> getGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(value -> new ApiResponse<>("Group retrieved successfully", true, value))
                .orElseGet(() -> new ApiResponse<>("Group not found", false));
    }

    public ApiResponse<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return new ApiResponse<>("Groups retrieved successfully", true, groups);
    }

    public ApiResponse<List<Group>> getGroupsByTeacher() {
        UserEntity currentUser = userService.getCurrentUser();
        if (currentUser.getUserType() != UserType.TEACHER) {
            return new ApiResponse<>("Only teachers can view their groups", false);
        }
        List<Group> groups = groupRepository.findByTeacher(currentUser);
        return new ApiResponse<>("Teacher's groups retrieved successfully", true, groups);
    }

    @Transactional
    public ApiResponse<Group> updateGroup(Long id, String groupName) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            UserEntity currentUser = userService.getCurrentUser();
            if (!group.getTeacher().equals(currentUser)) {
                return new ApiResponse<>("Only the group's teacher can update the group", false);
            }
            group.setGroupName(groupName);
            Group updatedGroup = groupRepository.save(group);
            return new ApiResponse<>("Group updated successfully", true, updatedGroup);
        } else {
            return new ApiResponse<>("Group not found", false);
        }
    }

    @Transactional
    public ApiResponse<Void> deleteGroup(Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            UserEntity currentUser = userService.getCurrentUser();
            if (!group.getTeacher().equals(currentUser)) {
                return new ApiResponse<>("Only the group's teacher can delete the group", false);
            }
            groupRepository.delete(group);
            return new ApiResponse<>("Group deleted successfully", true);
        } else {
            return new ApiResponse<>("Group not found", false);
        }
    }

    @Transactional
    public ApiResponse<Group> addStudentToGroup(Long groupId, Long studentId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Optional<UserEntity> studentOptional = userRepository.findById(studentId);

        if (groupOptional.isPresent() && studentOptional.isPresent()) {
            Group group = groupOptional.get();
            UserEntity student = studentOptional.get();
            UserEntity currentUser = userService.getCurrentUser();

            if (!group.getTeacher().equals(currentUser)) {
                return new ApiResponse<>("Only the group's teacher can add students", false);
            }

            if (student.getUserType() != UserType.STUDENT) {
                return new ApiResponse<>("Only students can be added to groups", false);
            }

            group.addStudent(student);
            Group updatedGroup = groupRepository.save(group);
            return new ApiResponse<>("Student added to group successfully", true, updatedGroup);
        } else {
            return new ApiResponse<>("Group or student not found", false);
        }
    }

    @Transactional
    public ApiResponse<Group> removeStudentFromGroup(Long groupId, Long studentId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Optional<UserEntity> studentOptional = userRepository.findById(studentId);

        if (groupOptional.isPresent() && studentOptional.isPresent()) {
            Group group = groupOptional.get();
            UserEntity student = studentOptional.get();
            UserEntity currentUser = userService.getCurrentUser();

            if (!group.getTeacher().equals(currentUser)) {
                return new ApiResponse<>("Only the group's teacher can remove students", false);
            }

            group.removeStudent(student);
            Group updatedGroup = groupRepository.save(group);
            return new ApiResponse<>("Student removed from group successfully", true, updatedGroup);
        } else {
            return new ApiResponse<>("Group or student not found", false);
        }
    }
}
