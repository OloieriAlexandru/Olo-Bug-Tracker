package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;
import com.olo.olobugtracker.dtos.UserUpdateDTO;
import com.olo.olobugtracker.exceptions.GenericBadRequestException;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.services.abstractions.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiOperation(value = "Endpoints for operations on users")
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
    @ApiOperation("Creates a new user")
    public ResponseEntity<UserGetByUsernameDTO> create(@RequestBody UserCreateDTO user) throws GenericDuplicateException {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    @ApiOperation("Updates the information of a user identified by id")
    public ResponseEntity.HeadersBuilder<?> update(@RequestBody UserUpdateDTO user, @PathVariable Long userId) throws NotFoundUserException, GenericBadRequestException {
        if (!user.getId().equals(userId)) {
            throw new GenericBadRequestException("The id of the user object doesn't match the request path user id!");
        }

        userService.update(user);
        return ResponseEntity.noContent();
    }
}
