package ru.maxim.barybians.api.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PostRequestDto {

    private String title;
    private String text;
    private long time = new Date().getTime();
}
