package com.enocholumide.controllers;

import com.enocholumide.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/users", name = "All Users")
    private ResponseEntity getAllUsers(){
        return ResponseEntity.ok().body(userService.listAll());
    }
}
