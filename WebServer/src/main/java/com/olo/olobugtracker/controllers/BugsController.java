package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.BugCreateDTO;
import com.olo.olobugtracker.dtos.BugGetAllProjectInfoDTO;
import com.olo.olobugtracker.dtos.BugGetByIdDTO;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.services.abstractions.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class BugsController {
    private BugService bugService;

    @Autowired
    public BugsController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("bugs")
    public ResponseEntity<List<BugGetAllProjectInfoDTO>> findAll(@RequestAttribute(value = "userId") Long userId) {
        return new ResponseEntity<>(bugService.findAll(userId), HttpStatus.OK);
    }

    @GetMapping("projects/{projectId}/bugs")
    public ResponseEntity<BugGetAllProjectInfoDTO> findAllFromProject(@RequestAttribute(value = "userId") Long userId, @PathVariable Long projectId)
            throws GenericNotFoundException {
        return new ResponseEntity<>(bugService.findAllFromProject(userId, projectId), HttpStatus.OK);
    }

    @PostMapping("projects/{projectId}/bugs")
    public ResponseEntity<BugGetByIdDTO> create(@RequestAttribute(value = "userId") Long userId, @RequestBody BugCreateDTO newBug, @PathVariable Long projectId)
            throws GenericNotFoundException {
        BugGetByIdDTO createdBug = bugService.create(userId, projectId, newBug);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(createdBug.getId()).toUri();

        return ResponseEntity.created(location).body(createdBug);
    }
}
