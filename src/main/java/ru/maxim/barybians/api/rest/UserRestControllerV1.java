package ru.maxim.barybians.api.rest;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxim.barybians.api.dto.UserDto;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtTokenProvider;
import ru.maxim.barybians.api.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class UserRestControllerV1 {

    @Value("${jwt.token.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @GetMapping(value = "users/{identifier}")
    public ResponseEntity getUser(@PathVariable(name = "identifier") String identifier){
        User user;
        try {
            user = userService.findById(Long.parseLong(identifier));
        }catch (Exception e){
            user = userService.findByUsername(identifier);
        }
        if (user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }else {
            UserDto result = UserDto.fromUser(user, true, true, true);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "users/{identifier}")
    public ResponseEntity deleteUser(@PathVariable(name = "identifier") String identifier){
        User user;
        try {
            user = userService.findById(Long.parseLong(identifier));
        }catch (Exception e){
            user = userService.findByUsername(identifier);
        }
        if (user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }else {
            userService.delete(user.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(value = "users", params = "search")
    public ResponseEntity searchUsers(@RequestParam(value = "search") String search){
        List<User> users = userService.getAll();
        if (users == null || users.size() == 0){
            return new ResponseEntity<>("No users found by parameter " + search, HttpStatus.NO_CONTENT);
        }
        List<UserDto> result = new ArrayList<>();
        users.forEach(user ->{
            if (user.concatToSearchString().contains(search.toLowerCase())){
                result.add(UserDto.fromUser(user, false, false, false));
            }
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "users")
    public ResponseEntity getAllUsers(){
        List<User> users = userService.getAll();
        if (users == null || users.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            List<UserDto> result = new ArrayList<>();
            users.forEach(user ->
                result.add(UserDto.fromUser(user, false, false, false))
            );
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}