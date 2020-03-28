package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.maxim.barybians.api.model.DialogPreview;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DialogPreviewDto {

    private UserDto firstUser;
    private UserDto secondUser;
    private MessageDto lastMessage;

    public static DialogPreviewDto fromDialogPreview(User firstUser, User secondUser, Message lastMessage){
        DialogPreviewDto dialogPreviewDto = new DialogPreviewDto();
        dialogPreviewDto.setFirstUser(UserDto.fromUser(firstUser, false, false, false));
        dialogPreviewDto.setSecondUser(UserDto.fromUser(secondUser, false, false, false));
        dialogPreviewDto.setLastMessage(MessageDto.fromMessage(lastMessage));
        return dialogPreviewDto;
    }
}
