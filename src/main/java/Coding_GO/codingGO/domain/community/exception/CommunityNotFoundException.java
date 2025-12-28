package Coding_GO.codingGO.domain.community.exception;

public class CommunityNotFoundException extends RuntimeException {
    public CommunityNotFoundException(String message) {
        super(message);
    }

    public CommunityNotFoundException() {
        super("NOT FOUND COMMUNITY");
    }
}

