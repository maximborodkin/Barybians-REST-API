package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DialogDto {

    private UserDto firstUser;
    private UserDto secondUser;
    private MessageDto lastMessage;
    private List<MessageDto> messages;

    public static DialogDto toDialog(User firstUser, User secondUser, List<Message> messages, boolean hasMessages){
        DialogDto dialogDto = new DialogDto();
        dialogDto.setFirstUser(UserDto.fromUser(firstUser, false, false, false));
        dialogDto.setSecondUser(UserDto.fromUser(secondUser, false, false, false));
        if (hasMessages){
            List<MessageDto> messageDtos = new ArrayList<>();
            messages.forEach(message -> messageDtos.add(MessageDto.fromMessage(message)));
            dialogDto.setMessages(messageDtos);
        }else {
            dialogDto.setLastMessage(MessageDto.fromMessage(messages.get(messages.size() - 1)));
        }
        return dialogDto;
    }

    public static DialogDto toDialog(User firstUser, User secondUser, Message lastMessage){
        DialogDto dialogDto = new DialogDto();
        dialogDto.setFirstUser(UserDto.fromUser(firstUser, false, false, false));
        dialogDto.setSecondUser(UserDto.fromUser(secondUser, false, false, false));
            dialogDto.setLastMessage(MessageDto.fromMessage(lastMessage));
        return dialogDto;
    }
}
