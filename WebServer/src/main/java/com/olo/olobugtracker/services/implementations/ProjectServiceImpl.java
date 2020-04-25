package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetAllDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.dtos.ProjectUpdateDTO;
import com.olo.olobugtracker.models.Project;
import com.olo.olobugtracker.repositories.ProjectRepository;
import com.olo.olobugtracker.services.abstractions.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProjectGetAllDTO> findAll() {
        List<Project> projects = (List<Project>) projectRepository.findAll();
        List<ProjectGetAllDTO> resultProjects = new ArrayList<>();

        for (Project project : projects) {
            resultProjects.add(modelMapper.map(project, ProjectGetAllDTO.class));
        }

        return resultProjects;
    }

    @Override
    public ProjectGetByIdDTO findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        return project.map(value -> modelMapper.map(value, ProjectGetByIdDTO.class)).orElse(null);
    }

    @Override
    public ProjectGetByIdDTO create(ProjectCreateDTO newProject) {
        Project project = modelMapper.map(newProject, Project.class);

        project = projectRepository.save(project);

        return modelMapper.map(project, ProjectGetByIdDTO.class);
    }

    @Override
    public boolean update(Long id, ProjectUpdateDTO updatedProject) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (!projectOpt.isPresent()) {
            return false;
        }

        Project project = projectOpt.get();
        BeanUtils.copyProperties(updatedProject, project);
        projectRepository.save(project);

        return true;
    }

    @Override
    public boolean delete(Long id) {
        if (!projectRepository.existsById(id)) {
            return false;
        }

        projectRepository.deleteById(id);
        return true;
    }
}
