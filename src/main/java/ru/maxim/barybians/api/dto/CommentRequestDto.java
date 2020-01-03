package ru.maxim.barybians.api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CommentRequestDto {

    private long postId;
    private String text;
    private long time = new Date().getTime();
}