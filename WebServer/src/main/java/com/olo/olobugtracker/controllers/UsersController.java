package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;
import com.olo.olobugtracker.services.abstractions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserGetByUsernameDTO> create(@RequestBody UserCreateDTO user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
