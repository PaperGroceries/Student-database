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

    @GetMapping("/users/viewById")
    public String getUserById(@RequestParam Map<String, String> newuser, Model model, HttpServletResponse response)
    {
        System.out.println("Getting user by id");
        int uid = Integer.parseInt(newuser.get("uid"));
        Users users = userRepo.findByUid(uid);
        if (users != null) {
            model.addAttribute("us", users);

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
        String newPwd = newuser.get("password");
        int newSize = Integer.parseInt(newuser.get("size"));
        userRepo.save(new Users(newName,newPwd,newSize));
        response.setStatus(201);
        return "users/addedUser";
    }
   
    
    
    @PostMapping("/users/update")
    public String updateUser(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
        System.out.println("UPDATE user");
        String name = newuser.get("name");
        String password = newuser.get("password");
        int size = Integer.parseInt(newuser.get("size"));
        
        int uid = Integer.parseInt(newuser.get("uid"));
        Users user = userRepo.findByUid(uid);
        if (user != null) {
            user.setPassword(password);
            user.setName(name);
            user.setSize(size);
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

    //connect this instead later
    @PostMapping("/users/deleteById")
    public String deleteUserById(@RequestParam Map<String, String> newuser, HttpServletResponse response)
    {
    System.out.println("DELETE user by id");
    int uid = Integer.parseInt(newuser.get("uid"));
    Users users = userRepo.findByUid(uid);
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

