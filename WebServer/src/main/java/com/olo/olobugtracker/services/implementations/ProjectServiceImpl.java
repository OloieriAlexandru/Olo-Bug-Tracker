package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.*;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundProjectException;
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
public class ProjectServiceImpl extends BaseService implements ProjectService {
    private UserProjectRoleRepository userProjectRoleRepository;

    private ProjectRepository projectRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, UserProjectRoleRepository userProjectRoleRepository, ModelMapper modelMapper) {
        super(userRepository);
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.userProjectRoleRepository = userProjectRoleRepository;
    }

    /**
     * Returns all the projects to which the user identified by 'userId' is assigned
     *
     * @param userId The user that is assigned to the returned projects
     * @return A list of project DTOs
     */
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

    /**
     * Returns a project by id
     *
     * @param id The id of the project
     * @return The project that has the specified id
     * @throws GenericNotFoundException The project doesn't exist in the repository
     */
    @Override
    public ProjectGetByIdDTO findById(Long id) throws GenericNotFoundException {
        Optional<Project> project = projectRepository.findById(id);

        if (!project.isPresent()) {
            throw new NotFoundProjectException(id);
        }

        return modelMapper.map(project.get(), ProjectGetByIdDTO.class);
    }

    /**
     * Creates a project owned by the user identified by 'userId'
     *
     * @param newProject The project information
     * @param userId     The owner of the newly created project
     * @return A project DTO
     * @throws GenericDuplicateException When a project with the same name already exists
     */
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

    /**
     * Updates the project identified by 'id' id
     *
     * @param id             The id of the updated project
     * @param updatedProject The updated information of the project
     * @throws GenericNotFoundException When there is no project with the specified id
     */
    @Override
    public void update(Long id, ProjectUpdateDTO updatedProject) throws GenericNotFoundException {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (!projectOpt.isPresent()) {
            throw new NotFoundProjectException(id);
        }

        Project project = projectOpt.get();
        BeanUtils.copyProperties(updatedProject, project);
        projectRepository.save(project);
    }

    /**
     * Deletes a project identified by 'id' id
     *
     * @param id The id of the deleted project
     * @throws GenericNotFoundException When there is no project with the specified id
     */
    @Override
    public void delete(Long id) throws GenericNotFoundException {
        if (!projectRepository.existsById(id)) {
            throw new NotFoundProjectException(id);
        }

        projectRepository.deleteById(id);
    }

    /**
     * Adds a user on a project, with a specific role
     *
     * @param createdRole Information about the newly created role
     * @param projectId   The id of the project on which the user role is added
     * @param ownerId     The id of the owner of the project
     * @throws GenericNotFoundException When an user, a project doesn't exist in the repository
     */
    @Override
    public void addUserOnProject(UserRoleCreateDTO createdRole, Long projectId, Long ownerId) throws GenericNotFoundException {
        Project project = getProjectFromRepositoryById(projectId);
        User owner = getUserFromRepositoryById(ownerId);
        User user = getUserFromRepositoryById(createdRole.getUserId());

        UserRole ownerRole = new UserRole(UserRoleEnum.OWNER);
        Optional<UserProjectRole> userProjectRole = userProjectRoleRepository.findByUserAndRole(owner, ownerRole);
        if (!userProjectRole.isPresent()) {
            throw new GenericNotFoundException("The project who made the request is not the owner of the project!");
        }

        UserRole newRole = new UserRole(createdRole.getRole());
        UserProjectRole newUserRole = new UserProjectRole(user, project, newRole);
        userProjectRoleRepository.save(newUserRole);
    }

    /**
     * Returns a project from the repository
     *
     * @param projectId The id of the project
     * @return The project identified by 'projectId'
     * @throws NotFoundProjectException When the project doesn't exist in the repository
     */
    private Project getProjectFromRepositoryById(Long projectId) throws NotFoundProjectException {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            throw new NotFoundProjectException(projectId);
        }
        return optionalProject.get();
    }
}
