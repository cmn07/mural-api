package br.ufscar.dc.dsw.mural_spring.services;

import br.ufscar.dc.dsw.mural_spring.dto.CreateMessageRequest;
import br.ufscar.dc.dsw.mural_spring.dto.ListedMessage;
import br.ufscar.dc.dsw.mural_spring.dto.UpdateMessageRequest;
import br.ufscar.dc.dsw.mural_spring.repositories.Message;
import br.ufscar.dc.dsw.mural_spring.repositories.MessageRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<ListedMessage> listMessages() {
        return messageRepository.getMessages()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ListedMessage findById(Long id) {
        Message message = findEntityById(id);
        return toDto(message);
    }

    public ListedMessage save(
            CreateMessageRequest request,
            String authenticatedUsername) {

        Message message = new Message();

        message.setFrom(authenticatedUsername);
        message.setTo(request.to());
        message.setMessage(request.message());

        Message savedMessage = messageRepository.save(message);

        return toDto(savedMessage);
    }

    @Transactional
    public ListedMessage update(
            Long id,
            UpdateMessageRequest request,
            String authenticatedUsername) {

        Message message = findEntityById(id);

        verifyOwner(message, authenticatedUsername);

        message.setMessage(request.message());

        Message updatedMessage = messageRepository.save(message);

        return toDto(updatedMessage);
    }

    @Transactional
    public void delete(Long id, String authenticatedUsername) {
        Message message = findEntityById(id);

        verifyOwner(message, authenticatedUsername);

        messageRepository.delete(message);
    }

    private Message findEntityById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Mensagem não encontrada"
                        )
                );
    }

    private void verifyOwner(
            Message message,
            String authenticatedUsername) {

        if (!message.getFrom().equals(authenticatedUsername)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não pode modificar esta mensagem"
            );
        }
    }

    private ListedMessage toDto(Message message) {
        return new ListedMessage(
                message.getId(),
                message.getFrom(),
                message.getTo(),
                message.getMessage(),
                message.getTimestamp()
        );
    }
}