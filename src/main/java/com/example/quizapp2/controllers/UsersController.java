package com.example.quizapp2.controllers;

import java.util.ArrayList; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quizapp2.models.UserRepository;
import com.example.quizapp2.models.Users;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users/view")
    public String getAllUsers(Model model)
    {
        System.out.println("Getting all users");

        List<Users> users = userRepo.findAll();

        model.addAttribute("us", users);
        return "users/showAll";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
        System.out.println("ADD user");
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        int newSize = Integer.parseInt(newuser.get("size"));
        userRepo.save(new Users(newName,newPwd,newSize));
        response.setStatus(201);
        return "users/addedUser";
    }
   
    
    //write a method to change attributes of a user
    @PostMapping("/users/update")
    public String updateUser(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
        System.out.println("UPDATE user");
        String name = newuser.get("name");
        String password = newuser.get("password");
        int size = Integer.parseInt(newuser.get("size"));
        List<Users> users = userRepo.findByName(name);
        if (!users.isEmpty()) {
            Users user = users.get(0);
            user.setPassword(password);
            user.setSize(size);
            userRepo.save(user);
            response.setStatus(201); // Updated
            return "users/updatedUser";
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
    List<Users> users = userRepo.findByName(name);
    if (!users.isEmpty()) {
        userRepo.deleteAll(users);
        response.setStatus(201); // Deleted
        return "users/deletedUser";
    } else {
        response.setStatus(404); // User not found
        return "users/userNotFound";
    }
    }
}

