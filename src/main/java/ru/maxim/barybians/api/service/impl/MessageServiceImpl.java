package ru.maxim.barybians.api.service.impl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.repository.MessageRepository;
import ru.maxim.barybians.api.repository.UserRepository;
import ru.maxim.barybians.api.service.MessageService;

import java.util.*;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Message> getDialog(Long firstUserId, Long secondUserId) {
        List<Message> dialog = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();
        messages.forEach(message -> {
            if (message.getReceiver().getId() == firstUserId &&
                    message.getSender().getId() == secondUserId ||
                    message.getReceiver().getId() == secondUserId &&
                    message.getSender().getId() == firstUserId){
                dialog.add(message);
            }
        });
        Collections.reverse(messages);
        return dialog;
    }

    @Override
    public List<Pair<User, Message>> getDialogPreviews(Long userId) {
        User user;
        if (userRepository.findById(userId).isPresent()){
            user = userRepository.findById(userId).get();
        }else {
            return null;
        }
        List<Message> allMessages = messageRepository.findAll();
        List<Message> allUserMessages = new ArrayList<>();
        allMessages.forEach(message -> {
            if (message.getReceiver().getId() == userId || message.getSender().getId() == userId){
                allUserMessages.add(message);
            }
        });
        Set<Long> interlocutorsIdList = new HashSet<>();
        allUserMessages.forEach(message -> {
            if (message.getSender().getId() != userId){
                interlocutorsIdList.add(message.getSender().getId());
            }else {
                interlocutorsIdList.add(message.getReceiver().getId());
            }
        });
        List<User> allUsers = userRepository.findAll();
        List<User> interlocutors = new ArrayList<>();
        allUsers.forEach(u -> {
            if (interlocutorsIdList.contains(u.getId())){
                interlocutors.add(u);
            }
        });
        List<Pair<User, Message>> messagesPreviews = new ArrayList<>();
        interlocutors.forEach(interlocutor -> {
            List<Message> interlocutorMessages = new ArrayList<>();
            allUserMessages.forEach(userMessage -> {
                if (userMessage.getSender() == interlocutor || userMessage.getReceiver() == interlocutor){
                    interlocutorMessages.add(userMessage);
                }
                Collections.sort(interlocutorMessages);
            });
            messagesPreviews.add(new Pair<>(interlocutor, interlocutorMessages.get(interlocutorMessages.size()-1)));
        });
        return messagesPreviews;
    }
}
