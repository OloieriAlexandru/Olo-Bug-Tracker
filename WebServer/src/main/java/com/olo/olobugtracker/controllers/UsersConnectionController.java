package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.GenericSuccessDTO;
import com.olo.olobugtracker.dtos.UsersConnectionGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionInvitationGetAllDTO;
import com.olo.olobugtracker.exceptions.GenericBadRequestException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.services.abstractions.UsersConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<UsersConnectionGetAllDTO>> getAllConnections(@PathVariable Long userId, @RequestAttribute(value = "usedId") Long tokenUserId) throws NotFoundUserException {
        if (!userId.equals(tokenUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(usersConnectionService.findAllConnections(userId), HttpStatus.OK);
    }

    @GetMapping("{userId}/connections/invitations")
    public ResponseEntity<List<UsersConnectionInvitationGetAllDTO>> getAllInvitations(@PathVariable Long userId, @RequestAttribute(value = "userId") Long tokenUserId) throws NotFoundUserException {
        if (!userId.equals(tokenUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(usersConnectionService.findAllInvitations(userId), HttpStatus.OK);
    }

    @PostMapping("{receiverId}/connections/invitations")
    public GenericSuccessDTO createInvitation(@PathVariable Long receiverId, @RequestAttribute(value = "userId") Long userId) throws NotFoundUserException {
        this.usersConnectionService.createInvitation(userId, receiverId);

        return new GenericSuccessDTO("Invitation created!");
    }

    @PostMapping("{receiverId}/connections/invitations/{id}")
    public ResponseEntity<GenericSuccessDTO> acceptInvitation(@PathVariable Long receiverId, @RequestAttribute(value = "userId") Long userId, @PathVariable Long id) throws GenericNotFoundException, GenericBadRequestException {
        if (!receiverId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        this.usersConnectionService.acceptInvitation(receiverId, id);
        return new ResponseEntity<>(new GenericSuccessDTO("Invitation accepted successfully!"), HttpStatus.OK);
    }
}
