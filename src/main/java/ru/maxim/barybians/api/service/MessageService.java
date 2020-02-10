package ru.maxim.barybians.api.service;

import org.springframework.data.util.Pair;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;

import java.util.List;

public interface MessageService  {

    List<Message> getDialog(long firstUserId, long secondUserId);

    List<Pair<User, Message>> getDialogPreviews(long userId);

    Message saveMessage(Message message);

    boolean deleteMessage(long id);

    Message editMessage(Message message);

    Message findById(long id);
}