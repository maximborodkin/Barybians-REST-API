package ru.maxim.barybians.api.service;

import ru.maxim.barybians.api.model.DialogPreview;
import ru.maxim.barybians.api.model.Message;

import java.util.List;

public interface MessageService  {

    List<Message> getDialog(long firstUserId, long secondUserId);

    List<DialogPreview> getDialogPreviews(long userId);

    Message saveMessage(Message message);

    boolean deleteMessage(long id);

    Message editMessage(Message message);

    Message findById(long id);
}