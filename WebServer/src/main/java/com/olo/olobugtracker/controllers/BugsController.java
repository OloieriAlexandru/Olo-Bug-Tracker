package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.BugCreateDTO;
import com.olo.olobugtracker.dtos.BugGetAllProjectInfoDTO;
import com.olo.olobugtracker.dtos.BugGetByIdDTO;
import com.olo.olobugtracker.dtos.BugUpdateDTO;
import com.olo.olobugtracker.exceptions.GenericBadRequestException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundBugException;
import com.olo.olobugtracker.services.abstractions.BugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api(value = "Endpoints for operations on bugs")
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
    @ApiOperation(value = "Returns the bugs on all the projects that a user is assigned to")
    public ResponseEntity<List<BugGetAllProjectInfoDTO>> findAll(@RequestAttribute(value = "userId") Long userId) {
        return new ResponseEntity<>(bugService.findAll(userId), HttpStatus.OK);
    }

    @PutMapping("bugs/{bugId}")
    @ApiOperation(value = "Updates a bug identified by its id")
    public ResponseEntity.HeadersBuilder<?> update(@RequestBody BugUpdateDTO updatedBug, @PathVariable Long bugId) throws GenericBadRequestException, NotFoundBugException {
        if (!bugId.equals(updatedBug.getId())) {
            throw new GenericBadRequestException("The id of the bug object doesn't match the request path bug id!");
        }

        bugService.update(updatedBug);
        return ResponseEntity.noContent();
    }

    @DeleteMapping("bugs/{bugId}")
    @ApiOperation(value = "Deletes a bug from the system")
    public ResponseEntity.HeadersBuilder<?> delete(@PathVariable Long bugId) throws NotFoundBugException {
        bugService.delete(bugId);
        return ResponseEntity.noContent();
    }

    @GetMapping("projects/{projectId}/bugs")
    @ApiOperation(value = "Returns all the bugs on a specified project")
    public ResponseEntity<BugGetAllProjectInfoDTO> findAllFromProject(@RequestAttribute(value = "userId") Long userId, @PathVariable Long projectId)
            throws GenericNotFoundException {
        return new ResponseEntity<>(bugService.findAllFromProject(userId, projectId), HttpStatus.OK);
    }

    @PostMapping("projects/{projectId}/bugs")
    @ApiOperation(value = "Creates a new bug on a project")
    public ResponseEntity<BugGetByIdDTO> create(@RequestAttribute(value = "userId") Long userId, @RequestBody BugCreateDTO newBug, @PathVariable Long projectId)
            throws GenericNotFoundException {
        BugGetByIdDTO createdBug = bugService.create(userId, projectId, newBug);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(createdBug.getId()).toUri();

        return ResponseEntity.created(location).body(createdBug);
    }
}
