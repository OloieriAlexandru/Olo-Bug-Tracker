package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetAllDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.dtos.ProjectUpdateDTO;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
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
    private ProjectRepository projectRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

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
    public ProjectGetByIdDTO findById(Long id) throws GenericNotFoundException {
        Optional<Project> project = projectRepository.findById(id);

        if (!project.isPresent()) {
            throw new GenericNotFoundException("The project with id " + id + " doesn't exist!");
        }

        return modelMapper.map(project.get(), ProjectGetByIdDTO.class);
    }

    @Override
    public ProjectGetByIdDTO create(ProjectCreateDTO newProject) {
        Project project = modelMapper.map(newProject, Project.class);

        project = projectRepository.save(project);

        return modelMapper.map(project, ProjectGetByIdDTO.class);
    }

    @Override
    public boolean update(Long id, ProjectUpdateDTO updatedProject) throws GenericNotFoundException {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (!projectOpt.isPresent()) {
            throw new GenericNotFoundException("The project with id " + id + " doesn't exist!");
        }

        Project project = projectOpt.get();
        BeanUtils.copyProperties(updatedProject, project);
        projectRepository.save(project);

        return true;
    }

    @Override
    public boolean delete(Long id) throws GenericNotFoundException {
        if (!projectRepository.existsById(id)) {
            throw new GenericNotFoundException("The project with id " + id + " doesn't exist!");
        }

        projectRepository.deleteById(id);
        return true;
    }
}
