package online_ta_lim.controller;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Group;
import online_ta_lim.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ApiResponse<Group> createGroup(@RequestBody String groupName) {
        return groupService.createGroup(groupName); // Only pass groupName
    }

    @GetMapping("/{id}")
    public ApiResponse<Group> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @GetMapping
    public ApiResponse<List<Group>> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PutMapping("/{id}")
    public ApiResponse<Group> updateGroup(@PathVariable Long id, @RequestBody String groupName) {
        return groupService.updateGroup(id, groupName);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }
}
