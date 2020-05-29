package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.ProjectCreateDTO;
import com.olo.olobugtracker.dtos.ProjectGetAllDTO;
import com.olo.olobugtracker.dtos.ProjectGetByIdDTO;
import com.olo.olobugtracker.dtos.ProjectUpdateDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.models.*;
import com.olo.olobugtracker.repositories.ProjectRepository;
import com.olo.olobugtracker.repositories.UserProjectRoleRepository;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.services.abstractions.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    private UserProjectRoleRepository userProjectRoleRepository;

    private ProjectRepository projectRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, UserProjectRoleRepository userProjectRoleRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userProjectRoleRepository = userProjectRoleRepository;
    }

    @Override
    public List<ProjectGetAllDTO> findAll(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        List<UserProjectRole> projects = userProjectRoleRepository.findAllByUser(userOpt.get());
        List<ProjectGetAllDTO> resultProjects = new ArrayList<>();

        for (UserProjectRole projectRole : projects) {
            Project project = projectRole.getProject();
            ProjectGetAllDTO projectDTO = modelMapper.map(project, ProjectGetAllDTO.class);
            if (project.getDescription().length() > 100) {
                projectDTO.setShortDescription(project.getDescription().substring(0, 100));
            } else {
                projectDTO.setShortDescription(project.getDescription());
            }
            resultProjects.add(projectDTO);
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
    @Transactional
    public ProjectGetByIdDTO create(ProjectCreateDTO newProject, Long userId) throws GenericDuplicateException {
        Project optProject = projectRepository.findByName(newProject.getName());

        if (optProject != null) {
            throw new GenericDuplicateException("A project with the name \"" + newProject.getName() + "\" already exists!");
        }
        Project project = modelMapper.map(newProject, Project.class);

        project = projectRepository.save(project);

        Optional<User> optUser = userRepository.findById(userId);
        UserProjectRole userProjectRole = new UserProjectRole(optUser.get(), project, new UserRole(UserRoleEnum.OWNER));
        userProjectRole = userProjectRoleRepository.save(userProjectRole);
        project.addUsers(userProjectRole);

        project = projectRepository.save(project);

        return modelMapper.map(project, ProjectGetByIdDTO.class);
    }

    @Override
    public void update(Long id, ProjectUpdateDTO updatedProject) throws GenericNotFoundException {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (!projectOpt.isPresent()) {
            throw new GenericNotFoundException("The project with id " + id + " doesn't exist!");
        }

        Project project = projectOpt.get();
        BeanUtils.copyProperties(updatedProject, project);
        projectRepository.save(project);
    }

    @Override
    public void delete(Long id) throws GenericNotFoundException {
        if (!projectRepository.existsById(id)) {
            throw new GenericNotFoundException("The project with id " + id + " doesn't exist!");
        }

        projectRepository.deleteById(id);
    }
}
