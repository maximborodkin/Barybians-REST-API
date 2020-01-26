package ru.maxim.barybians.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.maxim.barybians.api.socket.handler.MessageEventHandler;

@Configuration
public class RepositoryConfiguration {

    public RepositoryConfiguration(){
        super();
    }

    @Bean
    public MessageEventHandler messageEventHandler(){
        return new MessageEventHandler();
    }
}
