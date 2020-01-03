package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim.barybians.api.dto.AuthenticationRequestDto;
import ru.maxim.barybians.api.dto.RegisterRequestDto;
import ru.maxim.barybians.api.dto.UserDto;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtTokenProvider;
import ru.maxim.barybians.api.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    // Get full UserDto object with token and authenticate user
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("user", UserDto.fromUser(userService.findByUsername(username), true, true, true));
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    // Get full UserDto object with token and register user
    @PostMapping(value = "register")
    public ResponseEntity registerUser(@RequestBody RegisterRequestDto requestDto){
        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        String status = requestDto.getStatus();
        int sex = requestDto.getSex();
        Date birthDate = new Date(requestDto.getBirthDate());
        String imageUrl = requestDto.getImageUrl();
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || sex<0
                || sex>1
                || birthDate.after(new Date())
                || username.isEmpty()
                || password.isEmpty()){
            return new ResponseEntity<>("Invalid request body", HttpStatus.BAD_REQUEST);
        }

        if (status.isEmpty()){
            status = "Я - Барыбинец!";
        }

        // Check username availability
        User sameUsernameUser = userService.findByUsername(username);
        if (sameUsernameUser != null){
            return new ResponseEntity<>("Same username already exist", HttpStatus.CONFLICT);
        }

        User registerUser = new User();
        registerUser.setFirstName(firstName);
        registerUser.setLastName(lastName);
        registerUser.setStatus(status);
        registerUser.setSex(sex);
        registerUser.setBirthDate(birthDate);
        registerUser.setPhotoUrl(imageUrl);
        registerUser.setUsername(username);
        registerUser.setPassword(password);
        userService.register(registerUser);

        AuthenticationRequestDto authenticationRequest = new AuthenticationRequestDto();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);

        return login(authenticationRequest);
    }
}