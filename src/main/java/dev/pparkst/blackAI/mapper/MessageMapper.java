package dev.pparkst.blackAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.pparkst.blackAI.domain.Message;
import dev.pparkst.blackAI.dto.MessageDTO;
import dev.pparkst.blackAI.repository.MessageRepository;

@Mapper(componentModel = "spring", uses = {MessageRepository.class})
public interface MessageMapper {
 
    @Mapping(source = "id", target = "no")
    MessageDTO.Response toResponse(Message message);

    default Message toEntity(MessageDTO.Request messageRequest) {
        if(messageRequest == null) return null;

        return Message.builder()
                        .type(messageRequest.getType())
                        .content(messageRequest.getContent())
                        .sender(messageRequest.getSender())
                        .build();
    }
}
