package ru.maxim.barybians.api.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private String status = "Я - Барыбинец!";
    private int sex = 0;
    private long birthDate;
    private String imageUrl = null;
    private String username;
    private String password;
}
