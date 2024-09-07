package online_ta_lim.custom_responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private boolean success;
    private T data;
    private String token;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(String message, boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }
}
