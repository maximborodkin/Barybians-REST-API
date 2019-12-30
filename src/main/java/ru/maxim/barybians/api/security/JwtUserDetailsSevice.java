package ru.maxim.barybians.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException ;
import org.springframework.stereotype.Service;
import ru.maxim.barybians.api.model.User;
import ru.maxim.barybians.api.security.jwt.JwtUser;
import ru.maxim.barybians.api.security.jwt.JwtUserFactory;
import ru.maxim.barybians.api.service.UserService;

@Service
@Slf4j
public class JwtUserDetailsSevice implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsSevice(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return JwtUserFactory.create(user);
    }
}
