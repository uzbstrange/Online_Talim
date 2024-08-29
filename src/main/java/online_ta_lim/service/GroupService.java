package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Group;
import online_ta_lim.domain.UserEntity;
import online_ta_lim.repository.GroupRepository;
import online_ta_lim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService; // Inject UserService to get current user

    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public ApiResponse<Group> createGroup(String groupName) {
        UserEntity currentUser = userService.getCurrentUser(); // Get the current user
        Group group = new Group(groupName, currentUser); // Set the current user as the teacher
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

    public ApiResponse<Group> updateGroup(Long id, String groupName) {
        if (groupRepository.existsById(id)) {
            Group group = groupRepository.findById(id).orElseThrow();
            group.setGroupName(groupName);
            Group updatedGroup = groupRepository.save(group);
            return new ApiResponse<>("Group updated successfully", true, updatedGroup);
        } else {
            return new ApiResponse<>("Group not found", false);
        }
    }

    public ApiResponse<Void> deleteGroup(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return new ApiResponse<>("Group deleted successfully", true);
        } else {
            return new ApiResponse<>("Group not found", false);
        }
    }
}
