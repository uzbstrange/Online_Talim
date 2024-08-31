package online_ta_lim.controller;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Chat;
import online_ta_lim.dto.ChatCreationDto;
import online_ta_lim.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ApiResponse<Chat> createChat(@RequestBody ChatCreationDto chatDto) {
        return chatService.createChat(chatDto);
    }

    @GetMapping("/{id}")
    public ApiResponse<Chat> getChatById(@PathVariable Long id) {
        return chatService.getChatById(id);
    }

    @GetMapping
    public ApiResponse<List<Chat>> getAllChats() {
        return chatService.getAllChats();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteChat(@PathVariable Long id) {
        return chatService.deleteChat(id);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/lessons/{lessonId}")
    public ApiResponse<Chat> sendMessage(@RequestBody ChatCreationDto chatDto, @PathVariable Long lessonId) {
        return chatService.sendMessageToLessonChat(lessonId, chatDto);
    }
}
