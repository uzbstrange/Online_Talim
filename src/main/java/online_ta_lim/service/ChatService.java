package online_ta_lim.service;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.domain.Chat;
import online_ta_lim.domain.UserEntity;
import online_ta_lim.dto.ChatCreationDto;
import online_ta_lim.repository.ChatRepository;
import online_ta_lim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public ApiResponse<Chat> createChat(ChatCreationDto chatDto) {
        UserEntity currentUser = userService.getCurrentUser();
        UserEntity receiver = userRepository.findByUsername(chatDto.getReceiverUsername())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Chat chat = new Chat();
        chat.setSender(currentUser);
        chat.setReceiver(receiver);
        chat.setMessage(chatDto.getMessage());
        chat.setTimestamp(LocalDateTime.now());

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
