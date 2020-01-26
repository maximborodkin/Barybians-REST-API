package ru.maxim.barybians.api.socket;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.service.MessageService;
import ru.maxim.barybians.api.service.UserService;

import java.util.Date;

@Component
public class SocketMessagesHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject request = new JSONObject(message.getPayload());
        String method = request.getString("method");
        switch (method){
            case "POST":
                addMessage(request);
                break;
            case "PUT":
                editMessage(request);
                break;
            case "DELETE":
                deleteMessage(request);
                break;
            default:
                session.sendMessage(new TextMessage("Invalid method"));
                break;
        }
    }

    private void deleteMessage(JSONObject request) {
        if (messageService.findById(request.getLong("id")) != null){
            messageService.deleteMessage(request.getLong("id"));
        }
    }

    private void addMessage(JSONObject request){
        User sender = userService.findById(2L);
        User receiver = userService.findById(request.getLong("receiverId"));
        String text = request.getString("text");
        Date time = new Date(request.getLong("time"));
        Message newMessage = new Message();
        System.out.println(request);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        newMessage.setText(text);
        newMessage.setTime(time);
        newMessage.setUnread(1);
        messageService.saveMessage(newMessage);
    }

    private void editMessage(JSONObject request){
        User sender = userService.findById(2L);
        User receiver = userService.findById(request.getLong("receiverId"));
        String text = request.getString("text");
        Date time = new Date(request.getLong("time"));
        Message editedMessage = new Message();
        System.out.println(request);
        editedMessage.setSender(sender);
        editedMessage.setReceiver(receiver);
        editedMessage.setText(text);
        editedMessage.setTime(time);
        editedMessage.setUnread(1);
        messageService.saveMessage(editedMessage);
    }
}