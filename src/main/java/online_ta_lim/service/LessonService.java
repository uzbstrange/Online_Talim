package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Lesson;
import online_ta_lim.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public ApiResponse<Lesson> createLesson(Lesson lesson) {
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
}
