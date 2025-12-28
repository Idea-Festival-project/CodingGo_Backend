package Coding_GO.codingGO.domain.community.exception;

public class InvalidCategoryException extends RuntimeException {
    public InvalidCategoryException(String message) {
        super(message);
    }

    public InvalidCategoryException() {
        super("INVALID CATEGORY");
    }
}

