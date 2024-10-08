package online_ta_lim.controller;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Lesson;
import online_ta_lim.dto.LessonCreationDto;
import online_ta_lim.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/{id}/start-chat")
    public ApiResponse<Lesson> startChat(@PathVariable Long id) {
        return lessonService.startChat(id);
    }

    @PostMapping("/{id}/end-chat")
    public ApiResponse<Lesson> endChat(@PathVariable Long id) {
        return lessonService.endChat(id);
    }

    @PostMapping
    public ApiResponse<Lesson> createLesson(@RequestBody LessonCreationDto lesson) {
        return lessonService.createLesson(lesson);
    }

    @GetMapping("/{id}")
    public ApiResponse<Lesson> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping
    public ApiResponse<List<Lesson>> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PutMapping("/{id}")
    public ApiResponse<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        return lessonService.updateLesson(id, lesson);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
