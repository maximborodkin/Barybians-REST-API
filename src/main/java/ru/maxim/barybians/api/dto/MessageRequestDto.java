package ru.maxim.barybians.api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class MessageRequestDto {

    private long receiverId;
    private String text;
    private Long time = new Date().getTime();
}
