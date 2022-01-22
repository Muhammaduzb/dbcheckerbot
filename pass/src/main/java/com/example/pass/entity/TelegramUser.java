package com.example.pass.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long chatUserId;
    private String phoneNumber;
    private String firstName;
    private String userName;
    private String type;
//    private Integer step;

    public TelegramUser(String type) {
        this.type = type;
    }

    public TelegramUser(Long chatUserId, String phoneNumber, String firstName, String userName) {
        this.chatUserId = chatUserId;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.userName = userName;
    }
}