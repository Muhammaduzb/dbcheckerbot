package com.example.pass.controller;

import com.example.pass.entity.Users1;
import com.example.pass.repository.UsersRepository;
import com.example.pass.telegram.TelegramController;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController{
    UsersRepository usersRepository;
    TelegramController telegramController;

    WebController(UsersRepository usersRepository,TelegramController telegramController) {
        this.usersRepository = usersRepository;
        this.telegramController = telegramController;
    }

//    WebController(TelegramController telegramController) {
//        this.telegramController = telegramController;
//    }

    @PostMapping("/sendMessage")
    public Users1 addMessage(@RequestBody Users1 newUsers1) {
        telegramController.sendMessage(newUsers1.getPhoneNumber(), newUsers1.getMessage());
//        System.out.println(newUsers1.getPhoneNumber() + "   " + newUsers1.getMessage());
        usersRepository.save(newUsers1);
        return newUsers1;
    }

}