package dev.pparkst.blackAI.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MessageDTO {
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String category;
        private String content;
        private String sender;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long no;
        private String category;
        private String content;
        private String sender;
        private LocalDateTime createdAt;
    }
}
