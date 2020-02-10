package ru.maxim.barybians.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxim.barybians.api.model.DialogPreview;
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
    public List<Message> getDialog(long firstUserId, long secondUserId) {
        List<Message> dialog = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();
        messages.forEach(message -> {
            if (message.getReceiver().getId() == firstUserId &&
                    message.getSender().getId() == secondUserId ||
                    message.getReceiver().getId() == secondUserId &&
                    message.getSender().getId() == firstUserId){
                dialog.add(message);
                message.setUnread(0);
                messageRepository.save(message);
            }
        });
        Collections.reverse(messages);
        return dialog;
    }

    @Override
    public List<DialogPreview> getDialogPreviews(long userId) {
        User user;
        if (userRepository.findById(userId).isPresent()){
            user = userRepository.findById(userId).get();
        }else {
            return null;
        }
        user.setLastVisit(new Date());
        userRepository.save(user);
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
        List<DialogPreview> dialogsPreviews = new ArrayList<>();
        interlocutors.forEach(interlocutor -> {
            List<Message> interlocutorMessages = new ArrayList<>();
            allUserMessages.forEach(userMessage -> {
                if (userMessage.getSender() == interlocutor || userMessage.getReceiver() == interlocutor){
                    interlocutorMessages.add(userMessage);
                }
                Collections.sort(interlocutorMessages);
            });
            dialogsPreviews.add(new DialogPreview(interlocutor, interlocutorMessages.get(interlocutorMessages.size()-1)));
        });
        return dialogsPreviews;
    }



    @Override
    @Transactional
    public Message saveMessage(Message message) {
        User user = message.getSender();
        user.setLastVisit(new Date());
        userRepository.save(user);
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public boolean deleteMessage(long id) {
        if (messageRepository.findById(id).isPresent()){
            User user = messageRepository.findById(id).get().getSender();
            user.setLastVisit(new Date());
            userRepository.save(user);
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Message editMessage(Message message) {
        if (messageRepository.findById(message.getId()).isPresent()){
            User user = message.getSender();
            user.setLastVisit(new Date());
            userRepository.save(user);
            return messageRepository.save(message);
        }
        return null;
    }

    @Override
    public Message findById(long id) {
        if (messageRepository.findById(id).isPresent()){
            return messageRepository.findById(id).get();
        }
        return null;
    }
}