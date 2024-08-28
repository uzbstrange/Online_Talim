package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Chat;
import online_ta_lim.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ApiResponse<Chat> createChat(Chat chat) {
        Chat savedChat = chatRepository.save(chat);
        return new ApiResponse<>("Chat created successfully", true, savedChat);
    }

    public ApiResponse<Chat> getChatById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()) {
            return new ApiResponse<>("Chat retrieved successfully", true, chat.get());
        } else {
            return new ApiResponse<>("Chat not found", false);
        }
    }

    public ApiResponse<List<Chat>> getAllChats() {
        List<Chat> chats = chatRepository.findAll();
        return new ApiResponse<>("Chats retrieved successfully", true, chats);
    }

    public ApiResponse<Void> deleteChat(Long id) {
        if (chatRepository.existsById(id)) {
            chatRepository.deleteById(id);
            return new ApiResponse<>("Chat deleted successfully", true);
        } else {
            return new ApiResponse<>("Chat not found", false);
        }
    }
}
