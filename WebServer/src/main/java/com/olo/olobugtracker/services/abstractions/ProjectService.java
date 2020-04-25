package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetAllDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.dtos.ProjectUpdateDTO;

import java.util.List;

public interface ProjectService {
    List<ProjectGetAllDTO> findAll();

    ProjectGetByIdDTO findById(Long id);

    ProjectGetByIdDTO create(ProjectCreateDTO newProject);

    boolean update(Long id, ProjectUpdateDTO updatedProject);

    boolean delete(Long id);
}
