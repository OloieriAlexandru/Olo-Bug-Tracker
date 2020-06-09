package com.olo.olobugtracker.unit;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.models.*;
import com.olo.olobugtracker.repositories.ProjectRepository;
import com.olo.olobugtracker.repositories.UserProjectRoleRepository;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.services.abstractions.ProjectService;
import com.olo.olobugtracker.services.implementations.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectServiceTests {

    @Test
    public void findById_ThrowsException_IfIdNotFound() {
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        UserProjectRoleRepository userProjectRoleRepository = mock(UserProjectRoleRepository.class);
        when(projectRepository.findById(any())).thenReturn(Optional.empty());
        ProjectService projectService = new ProjectServiceImpl(projectRepository, userRepository, userProjectRoleRepository, new ModelMapper());

        Assertions.assertThrows(GenericNotFoundException.class, () -> {
            projectService.findById(10L);
        });
    }

    @Test
    public void create_ThrowsException_IfDuplicateName() {
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        UserProjectRoleRepository userProjectRoleRepository = mock(UserProjectRoleRepository.class);
        Project duplicateProject = new Project();
        when(projectRepository.findByName("Duplicate")).thenReturn(duplicateProject);

        ProjectService projectService = new ProjectServiceImpl(projectRepository, userRepository, userProjectRoleRepository, new ModelMapper());

        ProjectCreateDTO newlyCreatedProject = new ProjectCreateDTO("Duplicate", "Some description");
        Assertions.assertThrows(GenericDuplicateException.class, () -> {
            projectService.create(newlyCreatedProject, 1L);
        });
    }

    @Test
    public void create_CreatesTheProject_IfNormalExecution() throws GenericDuplicateException {
        Project returnedProject = new Project(1L, "Project Name", "Project description");
        ProjectGetByIdDTO returnedProjectDTO = new ProjectGetByIdDTO(1L, "Project Name", "Project description");
        ProjectCreateDTO newlyCreatedProject = new ProjectCreateDTO();
        BeanUtils.copyProperties(returnedProject, newlyCreatedProject);

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.save(any())).thenReturn(returnedProject);

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User(1L)));

        UserProjectRoleRepository userProjectRoleRepository = mock(UserProjectRoleRepository.class);
        when(userProjectRoleRepository.save(any())).thenReturn(new UserProjectRole(new User(1L), returnedProject, new UserRole(UserRoleEnum.OWNER)));

        ProjectService projectService = new ProjectServiceImpl(projectRepository, userRepository, userProjectRoleRepository, new ModelMapper());

        ProjectGetByIdDTO returnedDTO = projectService.create(newlyCreatedProject, 1L);
        Assertions.assertEquals(returnedDTO, returnedProjectDTO);
    }
}
