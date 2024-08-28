package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Group;
import online_ta_lim.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public ApiResponse<Group> createGroup(Group group) {
        Group savedGroup = groupRepository.save(group);
        return new ApiResponse<>("Group created successfully", true, savedGroup);
    }

    public ApiResponse<Group> getGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            return new ApiResponse<>("Group retrieved successfully", true, group.get());
        } else {
            return new ApiResponse<>("Group not found", false);
        }
    }

    public ApiResponse<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return new ApiResponse<>("Groups retrieved successfully", true, groups);
    }

    public ApiResponse<Group> updateGroup(Long id, Group group) {
        if (groupRepository.existsById(id)) {
            group.setId(id);
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
