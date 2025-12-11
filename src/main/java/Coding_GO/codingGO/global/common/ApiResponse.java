package Coding_GO.codingGO.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private final String message;  // final로 불변성 보장
    private final T data;

    // 데이터 없이 메시지만 반환하는 경우
    public static <T> ApiResponse<T> of(String message) {
        return new ApiResponse<>(message, null);
    }

    // 메시지와 데이터를 함께 반환하는 경우
    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(message, data);
    }
}