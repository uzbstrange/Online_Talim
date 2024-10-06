package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Lesson;
import online_ta_lim.dto.LessonCreationDto;
import online_ta_lim.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public ApiResponse<Lesson> createLesson(LessonCreationDto dto) {
        // Convert DTO to Lesson entity
        Lesson lesson = new Lesson();
        lesson.setTitle(dto.getTitle());
        lesson.setContent(dto.getContent());
        lesson.setGroup(dto.getGroupName());
        lesson.setChatActive(false); // Initialize chat as inactive
        lesson.setChatStartTime(null); // No chat start time initially

        // Save the lesson to the database
        Lesson savedLesson = lessonRepository.save(lesson);
        return new ApiResponse<>("Lesson created successfully", true, savedLesson);
    }

    public ApiResponse<Lesson> getLessonById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isPresent()) {
            return new ApiResponse<>("Lesson retrieved successfully", true, lesson.get());
        } else {
            return new ApiResponse<>("Lesson not found", false);
        }
    }

    public ApiResponse<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return new ApiResponse<>("Lessons retrieved successfully", true, lessons);
    }

    public ApiResponse<Lesson> updateLesson(Long id, Lesson lesson) {
        if (lessonRepository.existsById(id)) {
            lesson.setId(id);
            Lesson updatedLesson = lessonRepository.save(lesson);
            return new ApiResponse<>("Lesson updated successfully", true, updatedLesson);
        } else {
            return new ApiResponse<>("Lesson not found", false);
        }
    }

    public ApiResponse<Void> deleteLesson(Long id) {
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
            return new ApiResponse<>("Lesson deleted successfully", true);
        } else {
            return new ApiResponse<>("Lesson not found", false);
        }
    }

    public ApiResponse<Lesson> startChat(Long lessonId) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            lesson.setChatActive(true);
            lesson.setChatStartTime(LocalDateTime.now());
            lessonRepository.save(lesson);
            return new ApiResponse<>("Chat started successfully", true, lesson);
        } else {
            return new ApiResponse<>("Lesson not found", false);
        }
    }

    public ApiResponse<Lesson> endChat(Long lessonId) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            lesson.setChatActive(false);
            lessonRepository.save(lesson);
            return new ApiResponse<>("Chat ended successfully", true, lesson);
        } else {
            return new ApiResponse<>("Lesson not found", false);
        }
    }
}
