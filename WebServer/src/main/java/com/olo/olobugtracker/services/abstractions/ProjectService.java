package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetAllDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.dtos.ProjectUpdateDTO;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;

import java.util.List;

public interface ProjectService {
    List<ProjectGetAllDTO> findAll();

    ProjectGetByIdDTO findById(Long id) throws GenericNotFoundException;

    ProjectGetByIdDTO create(ProjectCreateDTO newProject);

    boolean update(Long id, ProjectUpdateDTO updatedProject) throws GenericNotFoundException;

    boolean delete(Long id) throws GenericNotFoundException;
}
