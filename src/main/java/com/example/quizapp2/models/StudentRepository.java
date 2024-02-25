package com.example.quizapp2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer>{
    
    List<Student> findByName(String name);
    List<Student> findAllByOrderByUid();
    Student findByUid(int uid);
}
