package Coding_GO.codingGO.global.exception;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getErrorCode().getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }
}
