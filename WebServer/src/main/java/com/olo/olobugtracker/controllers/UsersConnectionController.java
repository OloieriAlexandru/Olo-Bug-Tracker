package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.GenericSuccessDTO;
import com.olo.olobugtracker.dtos.UsersConnectionGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionInvitationGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionUserInfoDTO;
import com.olo.olobugtracker.exceptions.GenericBadRequestException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.services.abstractions.UsersConnectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Endpoints for operations on user connections and user connection invitations")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UsersConnectionController {
    private UsersConnectionService usersConnectionService;

    @Autowired
    public UsersConnectionController(UsersConnectionService usersConnectionService) {
        this.usersConnectionService = usersConnectionService;
    }

    @GetMapping("{userId}/connections")
    @ApiOperation("Returns all the connections of a user")
    public ResponseEntity<List<UsersConnectionGetAllDTO>> getAllConnections(@PathVariable Long userId, @RequestAttribute(value = "usedId") Long tokenUserId) throws NotFoundUserException {
        if (!userId.equals(tokenUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(usersConnectionService.findAllConnections(userId), HttpStatus.OK);
    }

    @GetMapping("{userId}/connections/invitations")
    @ApiOperation("Returns all the connection invitations of a user")
    public ResponseEntity<List<UsersConnectionInvitationGetAllDTO>> getAllInvitations(@PathVariable Long userId, @RequestAttribute(value = "userId") Long tokenUserId) throws NotFoundUserException {
        if (!userId.equals(tokenUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(usersConnectionService.findAllInvitations(userId), HttpStatus.OK);
    }

    @GetMapping("{userId}/connections/suggestions")
    @ApiOperation("Returns connection suggestions for a user")
    public ResponseEntity<List<UsersConnectionUserInfoDTO>> getSuggestions(@RequestParam(required = false) Long limit, @RequestAttribute(value = "userId") Long tokenUserId, @PathVariable Long userId)
            throws GenericBadRequestException, NotFoundUserException {
        if (!userId.equals(tokenUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (limit == null) {
            limit = 5L;
        }

        return new ResponseEntity<>(usersConnectionService.getConnectionsSuggestions(userId, limit), HttpStatus.OK);
    }

    @PostMapping("{receiverId}/connections/invitations")
    @ApiOperation("Creates a connection invitation")
    public GenericSuccessDTO createInvitation(@PathVariable Long receiverId, @RequestAttribute(value = "userId") Long userId) throws NotFoundUserException {
        this.usersConnectionService.createInvitation(userId, receiverId);

        return new GenericSuccessDTO("Invitation created!");
    }

    @PostMapping("{receiverId}/connections/invitations/{id}")
    @ApiOperation("Accepts a connection invitation")
    public ResponseEntity<GenericSuccessDTO> acceptInvitation(@PathVariable Long receiverId, @RequestAttribute(value = "userId") Long userId, @PathVariable Long id)
            throws GenericNotFoundException, GenericBadRequestException {
        if (!receiverId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        this.usersConnectionService.acceptInvitation(receiverId, id);
        return new ResponseEntity<>(new GenericSuccessDTO("Invitation accepted successfully!"), HttpStatus.OK);
    }
}
