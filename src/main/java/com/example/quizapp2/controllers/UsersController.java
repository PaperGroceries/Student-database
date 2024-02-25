package com.example.quizapp2.controllers;


import java.util.List;
import java.util.Map;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quizapp2.models.Student;
import com.example.quizapp2.models.StudentRepository;
// import com.example.quizapp2.models.Users;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    @Autowired
    private StudentRepository userRepo;

    @GetMapping("/users/view")
    public String getAllUsers(Model model)
    {
        System.out.println("Getting all users");

        List<Student> users = userRepo.findAllByOrderByUid();

        model.addAttribute("students", users);
        return "users/showAll";
    }

    @GetMapping("/users/viewById")
    public String getUserById(@RequestParam Map<String, String> newuser, Model model, HttpServletResponse response)
    {
        System.out.println("Getting user by id");
        int uid = Integer.parseInt(newuser.get("uid"));
        Student users = userRepo.findByUid(uid);
        if (users != null) {
            model.addAttribute("student", users);

            return "users/updateUser";
        } else {
            response.setStatus(404); // User not found
            return "users/userNotFound";
        }
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
        System.out.println("ADD user");
        String newName = newuser.get("name");
        String newHairColor = newuser.get("hairColor");

        double newWeight = Double.parseDouble(newuser.getOrDefault("weight", "0.0"));
        double newHeight = Double.parseDouble(newuser.getOrDefault("height", "0.0"));
        Double newGpa = Double.parseDouble(newuser.getOrDefault("gpa", "0.0"));

        userRepo.save(new Student(newName, newWeight, newHeight, newHairColor, newGpa));
        response.setStatus(201);
        return "users/addedUser";
    }
   
    
    
    @PostMapping("/users/update")
    public String updateUser(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
        System.out.println("UPDATE user");
        String name = newuser.get("name");
        double newWeight = Double.parseDouble(newuser.get("weight"));
        double newHeight = Double.parseDouble(newuser.get("height"));
        String newHairColor = newuser.get("hairColor");
        Double newGpa = Double.parseDouble(newuser.get("gpa"));
        
        int uid = Integer.parseInt(newuser.get("uid"));
        Student user = userRepo.findByUid(uid);
        if (user != null) {
            user.setName(name);
            user.setWeight(newWeight);
            user.setHeight(newHeight);
            user.setHairColor(newHairColor);
            user.setGpa(newGpa);

            userRepo.save(user);
            response.setStatus(201); // Updated
            return "users/addedUser";
        } else {
            response.setStatus(404); // User not found
            return "users/userNotFound";
        }
    }

    
    @PostMapping("/users/delete")
    public String deleteUserByName(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
    System.out.println("DELETE user");
    String name = newuser.get("name");
    List<Student> users = userRepo.findByName(name);
    if (!users.isEmpty()) {
        userRepo.deleteAll(users);
        response.setStatus(201); // Deleted
        return "users/deletedUser";
    } else {
        response.setStatus(404); // User not found
        return "users/userNotFound";
    }
    }

    //connect this instead later
    @PostMapping("/users/deleteById")
    public String deleteUserById(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
    System.out.println("DELETE user by id");
    int uid = Integer.parseInt(newuser.get("uid"));
    Student users = userRepo.findByUid(uid);
    if (users != null) {
        userRepo.delete(users);
        response.setStatus(201); // Deleted
        return "users/deletedUser";
    } else {
        response.setStatus(404); // User not found
        return "users/userNotFound";
    }
    }

  


}

