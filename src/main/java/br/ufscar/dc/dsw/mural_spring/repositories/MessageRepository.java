package br.ufscar.dc.dsw.mural_spring.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(MessageRepository.class);

    private final IMessageDAO dao;

    public MessageRepository(IMessageDAO dao) {
        this.dao = dao;
        logger.info("MessageRepository instantiated");
    }

    public Message save(Message message) {
        if (message.getTimestamp() == null) {
            message.setTimestamp(new Date().toString());
        }

        logger.info("Saving message: {}", message);

        return dao.save(message);
    }

    public List<Message> getMessages() {
        return dao.findAllOrderByIdDesc();
    }

    public Optional<Message> findById(Long id) {
        return dao.findById(id);
    }

    public boolean existsById(Long id) {
        return dao.existsById(id);
    }

    public void delete(Message message) {
        dao.delete(message);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}