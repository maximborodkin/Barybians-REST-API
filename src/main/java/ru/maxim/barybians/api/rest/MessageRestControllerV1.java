package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxim.barybians.api.dto.DialogDto;
import ru.maxim.barybians.api.dto.DialogPreviewDto;
import ru.maxim.barybians.api.dto.MessageDto;
import ru.maxim.barybians.api.dto.MessageRequestDto;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtTokenProvider;
import ru.maxim.barybians.api.service.MessageService;
import ru.maxim.barybians.api.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class MessageRestControllerV1 {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Get all messages between two users
    @GetMapping(value = "dialogs")
    public ResponseEntity getDialog(@RequestParam(name = "firstUser") Long firstUserId,
                                    @RequestParam(name = "secondUser") Long secondUserId,
                                    @RequestHeader("Authorization") String token){
        if (firstUserId == null || secondUserId == null){
            return new ResponseEntity<>("Two parameters required", HttpStatus.BAD_REQUEST);
        }
        if (token == null){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }

        User firstUser = userService.findById(firstUserId);
        User secondUser = userService.findById(secondUserId);
        if (firstUser == null){
            return new ResponseEntity<>("User with id " + firstUserId + " not found", HttpStatus.NOT_FOUND);
        }
        if (secondUser == null){
            return new ResponseEntity<>("User with id " + secondUserId + " not found", HttpStatus.NOT_FOUND);
        }

        String username = tokenProvider.getUsername(token.trim().substring(7));
        if (!firstUser.getUsername().equals(username) && !secondUser.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }

        List<Message> messages = messageService.getDialog(firstUserId, secondUserId);
        return new ResponseEntity<>(DialogDto.toDialog(firstUser, secondUser, messages, true), HttpStatus.OK);
    }

    // Get dialogs previews
    @GetMapping(value = "dialogs_list")
    public ResponseEntity getDialogsPreviews(@RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (!user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        List<DialogDto> dialogPreviews = new ArrayList<>();
        messageService.getDialogPreviews(user.getId()).forEach(preview -> {
            dialogPreviews.add(DialogPreviewDto.toDialogPreview(user, preview.getInterlocutor(), preview.getLastMessage()));
        });
        return new ResponseEntity<>(dialogPreviews, HttpStatus.OK);
    }

    // Create message
    @PostMapping(value = "messages")
    public ResponseEntity sendMessage(@RequestBody MessageRequestDto messageRequest,
                                      @RequestHeader("Authorization") String token){

        String username = tokenProvider.getUsername(token.trim().substring(7));
        User sender = userService.findByUsername(username);
        User receiver = userService.findById(messageRequest.getReceiverId());
        String text = messageRequest.getText();
        Date time = new Date();

        if (sender == null || receiver == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (text == null || text.isEmpty() || time.after(new Date())){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        if (!sender.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(text);
        message.setTime(time);
        message.setUnread(1);
        Message sendedMessage = messageService.saveMessage(message);
        if (sendedMessage == null){
            return new ResponseEntity<>("Message not saved", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(MessageDto.fromMessage(sendedMessage), HttpStatus.OK);
        }
    }

    // Edit message by id
    @PutMapping(value = "messages/{id}")
    public ResponseEntity editMessage(@PathVariable(name = "id") Long id,
                                      @RequestBody MessageRequestDto messageRequest,
                                      @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User sender = userService.findByUsername(username);
        User receiver = userService.findById(messageRequest.getReceiverId());
        String text = messageRequest.getText();
        Date time = new Date();

        if (sender == null || receiver == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (id == 0){
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        }
        if (text == null || text.isEmpty() || time.after(new Date())){
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        if (!sender.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        Message message = new Message();
        message.setId(id);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(text);
        message.setTime(time);
        Message editedMessage = messageService.editMessage(message);
        if (editedMessage == null){
            return new ResponseEntity<>("Message not saved", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(MessageDto.fromMessage(editedMessage), HttpStatus.OK);
        }
    }

    // Delete message by id
    @DeleteMapping(value = "messages/{id}")
    public ResponseEntity deleteMessage(@PathVariable(name = "id") Long id, @RequestHeader("Authorization") String token){
        String username = tokenProvider.getUsername(token.trim().substring(7));
        User user = userService.findByUsername(username);
        if (user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        if (messageService.deleteMessage(id)){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        }
    }
}