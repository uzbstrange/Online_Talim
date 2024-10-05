package online_ta_lim.controller;

import online_ta_lim.dto.GroupCreationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Group;
import online_ta_lim.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

//    // Create a new group
//    @PostMapping
//    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody String groupName) {
//        ApiResponse<Group> response = groupService.createGroup(groupName);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody GroupCreationDto groupDto) {
        String groupName = groupDto.getGroupName();
        List<Long> studentIds = groupDto.getStudentIds();

        ApiResponse<Group> response = groupService.createGroup(groupName, studentIds);
        return ResponseEntity.ok(response);
    }

    // Add a student to a group
    @PostMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<ApiResponse<Group>> addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        ApiResponse<Group> response = groupService.addStudentToGroup(groupId, studentId);
        return ResponseEntity.ok(response);
    }

    // Remove a student from a group
    @DeleteMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<ApiResponse<Group>> removeStudentFromGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        ApiResponse<Group> response = groupService.removeStudentFromGroup(groupId, studentId);
        return ResponseEntity.ok(response);
    }

    // Get a group by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Group>> getGroupById(@PathVariable Long id) {
        ApiResponse<Group> response = groupService.getGroupById(id);
        return ResponseEntity.ok(response);
    }

    // Get all groups
    @GetMapping
    public ResponseEntity<ApiResponse<List<Group>>> getAllGroups() {
        ApiResponse<List<Group>> response = groupService.getAllGroups();
        return ResponseEntity.ok(response);
    }

    // Get all groups by a specific teacher
    @GetMapping("/teacher")
    public ResponseEntity<ApiResponse<List<Group>>> getGroupsByTeacher() {
        ApiResponse<List<Group>> response = groupService.getGroupsByTeacher();
        return ResponseEntity.ok(response);
    }

    // Update a group's name
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Group>> updateGroup(@PathVariable Long id, @RequestBody String groupName) {
        ApiResponse<Group> response = groupService.updateGroup(id, groupName);
        return ResponseEntity.ok(response);
    }

    // Delete a group
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(@PathVariable Long id) {
        ApiResponse<Void> response = groupService.deleteGroup(id);
        return ResponseEntity.ok(response);
    }
}
