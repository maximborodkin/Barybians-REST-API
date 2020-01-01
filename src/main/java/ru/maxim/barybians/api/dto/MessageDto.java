package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.maxim.barybians.api.model.Message;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MessageDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String text;
    private Long time;
    private int unread;

    public Message toMessage(){
        Message message = new Message();
        message.setId(id);
        message.setText(text);
        message.setTime(new Date(time));
        message.setUnread(unread);
        return message;
    }

    public static MessageDto fromMessage(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setReceiverId(message.getReceiver().getId());
        messageDto.setText(message.getText());
        messageDto.setTime(message.getTime().getTime());
        messageDto.setUnread(message.getUnread());
        return messageDto;
    }
}
