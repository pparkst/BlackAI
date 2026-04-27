package dev.pparkst.blackAI.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "content", columnDefinition = "TEXT",  nullable = false, length = 2000)
    private String content;

    @Column(name = "sender", nullable = false)
    private String sender;

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Message(String category, String content, String sender) {
        this.category = category;
        this.content = content;
        this.sender = sender;
    }
}
