package com.example.pass.repository;

import com.example.pass.entity.Users1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users1,Integer>{
    Optional<Users1> findByPhoneNumber(String phoneNmber);

}
