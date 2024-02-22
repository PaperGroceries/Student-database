package com.example.quizapp2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer>{
    List<Users> findBySize(int size);
    List<Users> findByNameAndPassword(String name, String password);
    List<Users> findByName(String name);
    Users findByUid(int uid);
}
