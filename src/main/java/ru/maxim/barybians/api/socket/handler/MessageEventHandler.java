package ru.maxim.barybians.api.socket.handler;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.repository.MessageRepository;

import java.io.*;
import java.util.logging.Logger;

@RepositoryEventHandler(MessageRepository.class)
public class MessageEventHandler {

    Logger logger = Logger.getLogger("Class MessageEventHandler");

    @HandleAfterCreate
    public void handleMessageAfterCreate(Message message){
        System.out.println("message " + message.getId() + " created!!! (" + message.getText()+")");
    }

    @HandleAfterDelete
    public void handleMessageAfterDelete(Message message) {

    }
}
