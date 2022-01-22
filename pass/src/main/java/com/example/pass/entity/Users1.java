package com.example.pass.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String phoneNumber;
    private String message;
//    @OneToMany
//    private List<Messages> message;

//    private String chatId;
//    private String firstName;
//    private String userName;

    public Users1(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

//    public Users1(String phoneNumber, List<Messages> message) {
//        this.phoneNumber = phoneNumber;
//        this.message = message;
//    }
}
