package com.olo.olobugtracker.unit;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.models.Project;
import com.olo.olobugtracker.repositories.ProjectRepository;
import com.olo.olobugtracker.services.abstractions.ProjectService;
import com.olo.olobugtracker.services.implementations.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class ProjectServiceImplTests {

    @Test
    public void findById_ThrowsException_IfIdNotFound() {
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
        Mockito.when(projectRepository.findById(any())).thenReturn(Optional.empty());
        ProjectService projectService = new ProjectServiceImpl(projectRepository, new ModelMapper());

        Assertions.assertThrows(GenericNotFoundException.class, () -> {
            projectService.findById(10L);
        });
    }

    @Test
    public void create_ThrowsException_IfDuplicateName() {
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
        Project duplicateProject = new Project();
        Mockito.when(projectRepository.findByName("Duplicate")).thenReturn(duplicateProject);

        ProjectService projectService = new ProjectServiceImpl(projectRepository, new ModelMapper());

        ProjectCreateDTO newlyCreatedProject = new ProjectCreateDTO("Duplicate", "Some description");
        Assertions.assertThrows(GenericDuplicateException.class, () -> {
            projectService.create(newlyCreatedProject);
        });
    }

    @Test
    public void create_CreatesTheProject_IfNormalExecution() throws GenericDuplicateException {
        Project returnedProject = new Project(1L, "Project Name", "Project description");
        ProjectGetByIdDTO returnedProjectDTO = new ProjectGetByIdDTO(1L, "Project Name", "Project description");
        ProjectCreateDTO newlyCreatedProject = new ProjectCreateDTO();
        BeanUtils.copyProperties(returnedProject, newlyCreatedProject);

        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
        Mockito.when(projectRepository.save(any())).thenReturn(returnedProject);

        ProjectService projectService = new ProjectServiceImpl(projectRepository, new ModelMapper());

        ProjectGetByIdDTO returnedDTO = projectService.create(newlyCreatedProject);
        Assertions.assertEquals(returnedDTO, returnedProjectDTO);
    }
}
