package br.ufscar.dc.dsw.mural_spring.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MessageRepository {

    private Logger logger = LoggerFactory.getLogger(MessageRepository.class);

    //private List<Message> messages; não salva mais em memória

    private final IMessageDAO dao;

    public MessageRepository(IMessageDAO dao) {
        this.dao = dao;
        //messages = new ArrayList<>();
        logger.info("MessageRepository instantiated");
    }

    public void save(Message message) {
        //message.setId(messages.size() + 1);
        message.setTimestamp((new Date()).toString());
        logger.info("saving message: {}", message);
        //messages.add(message);
        dao.save(message);
        //logger.info("# messages: {}", messages.size());
    }

    public List<Message> getMessages() {
        return dao.findAllOrderByIdDesc();
    }
}
