package ru.maxim.barybians.api.service;

import javafx.util.Pair;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;

import java.util.List;

public interface MessageService  {

    List<Message> getDialog(Long firstUserId, Long secondUserId);

    List<Pair<User, Message>> getDialogPreviews(Long userId);
}
