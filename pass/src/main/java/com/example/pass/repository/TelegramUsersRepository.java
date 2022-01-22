package com.example.pass.repository;

import com.example.pass.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelegramUsersRepository extends JpaRepository<TelegramUser,Integer> {
    Optional<TelegramUser> findByPhoneNumber(String phoneNumber);
    Optional<TelegramUser> findByChatUserId(Long chatId);
    List<TelegramUser> findAllByType(String type);
}
