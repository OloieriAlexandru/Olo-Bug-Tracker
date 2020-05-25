package com.olo.olobugtracker.unit;

import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.repositories.ProjectRepository;
import com.olo.olobugtracker.services.abstractions.ProjectService;
import com.olo.olobugtracker.services.implementations.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

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
}
