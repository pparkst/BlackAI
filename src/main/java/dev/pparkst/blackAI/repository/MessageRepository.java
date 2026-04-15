package dev.pparkst.blackAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pparkst.blackAI.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
