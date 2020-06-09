package com.olo.olobugtracker.controllers;

import com.olo.olobugtracker.dtos.*;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.services.abstractions.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api(value = "Endpoints for operations on projects")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsController {
    private ProjectService projectService;

    @Autowired
    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @ApiOperation(value = "Returns all the projects that the user is assigned on")
    public ResponseEntity<List<ProjectGetAllDTO>> findAll(@RequestAttribute(value = "userId") Long userId) {
        return new ResponseEntity<>(projectService.findAll(userId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Returns a project with a specified id")
    public ResponseEntity<ProjectGetByIdDTO> getById(@PathVariable Long id) throws GenericNotFoundException {
        ProjectGetByIdDTO project = projectService.findById(id);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Creates a new project")
    public ResponseEntity<ProjectGetByIdDTO> create(@RequestBody ProjectCreateDTO project,
                                                    @RequestAttribute(value = "userId") Long userId) throws GenericDuplicateException {
        ProjectGetByIdDTO createdProject = projectService.create(project, userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(createdProject.getId()).toUri();

        return ResponseEntity.created(location).body(createdProject);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates a project")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProjectUpdateDTO project) throws GenericNotFoundException {
        projectService.update(id, project);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes a project")
    public ResponseEntity<?> delete(@PathVariable Long id) throws GenericNotFoundException {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "{id}/roles")
    @ApiOperation(value = "Adds a user role on a project")
    public void addProjectUserRole(@PathVariable Long id, @RequestBody UserRoleCreateDTO userRole, @RequestAttribute(value = "userId") Long userId) throws GenericNotFoundException {
        projectService.addUserOnProject(userRole, id, userId);
    }
}
