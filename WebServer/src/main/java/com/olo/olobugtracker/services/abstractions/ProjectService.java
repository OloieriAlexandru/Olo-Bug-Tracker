package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.*;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;

import java.util.List;

public interface ProjectService {
    List<ProjectGetAllDTO> findAll(Long userId);

    ProjectGetByIdDTO findById(Long id) throws GenericNotFoundException;

    ProjectGetByIdDTO create(ProjectCreateDTO newProject, Long userId) throws GenericDuplicateException;

    void update(Long id, ProjectUpdateDTO updatedProject) throws GenericNotFoundException;

    void delete(Long id) throws GenericNotFoundException;

    void addUserOnProject(UserRoleCreateDTO createdRole, Long projectId, Long ownerId) throws GenericNotFoundException;
}
