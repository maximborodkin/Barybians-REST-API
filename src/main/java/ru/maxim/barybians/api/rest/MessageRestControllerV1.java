package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxim.barybians.api.dto.DialogDto;
import ru.maxim.barybians.api.model.Message;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtTokenProvider;
import ru.maxim.barybians.api.service.MessageService;
import ru.maxim.barybians.api.service.UserService;

import java.util.ArrayList;
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

    @GetMapping(value = "dialogs")
    public ResponseEntity getDialog(@RequestParam(name = "firstUser") Long firstUserId, @RequestParam(name = "secondUser") Long secondUserId, @RequestHeader("Authorization") String token){
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

    @GetMapping(value = "dialogs/{userId}")
    public ResponseEntity getDialofsPreviews(@PathVariable(name = "userId") Long id, @RequestHeader("Authorization") String token){
        User user = userService.findById(id);
        String username = tokenProvider.getUsername(token.trim().substring(7));
        if (!user.getUsername().equals(username)){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        List<DialogDto> dialogPreviews = new ArrayList<>();
        messageService.getDialogPreviews(id).forEach(preview -> {
            dialogPreviews.add(DialogDto.toDialog(user, preview.getKey(), preview.getValue()));
        });
        return new ResponseEntity<>(dialogPreviews, HttpStatus.OK);
    }
}