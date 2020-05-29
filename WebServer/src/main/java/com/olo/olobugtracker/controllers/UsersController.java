package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.services.abstractions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserGetByUsernameDTO> create(@RequestBody UserCreateDTO user) throws GenericDuplicateException {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
