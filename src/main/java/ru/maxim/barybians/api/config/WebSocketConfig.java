package ru.maxim.barybians.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.maxim.barybians.api.socket.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SocketMessagesHandler messagesHandler;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
            .addHandler(new SocketPostsHandler(), "/posts")
            .addHandler(messagesHandler, "/messages")
            .addHandler(new SocketLikesHandler(), "/likes")
            .addHandler(new SocketCommentsHandler(), "/comments")
            .setAllowedOrigins("*");
    }
}