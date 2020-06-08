package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.BugCreateDTO;
import com.olo.olobugtracker.dtos.BugGetAllDTO;
import com.olo.olobugtracker.dtos.BugGetAllProjectInfoDTO;
import com.olo.olobugtracker.dtos.BugGetByIdDTO;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.models.*;
import com.olo.olobugtracker.repositories.BugRepository;
import com.olo.olobugtracker.repositories.ProjectRepository;
import com.olo.olobugtracker.repositories.UserProjectRoleRepository;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.services.abstractions.BugService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BugServiceImpl implements BugService {
    private UserProjectRoleRepository userProjectRoleRepository;

    private UserRepository userRepository;

    private ProjectRepository projectRepository;

    private BugRepository bugRepository;

    private ModelMapper modelMapper;

    @Autowired
    public BugServiceImpl(BugRepository bugRepository, ProjectRepository projectRepository,
                          UserRepository userRepository, UserProjectRoleRepository userProjectRoleRepository,
                          ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userProjectRoleRepository = userProjectRoleRepository;
        this.projectRepository = projectRepository;
        this.bugRepository = bugRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BugGetAllProjectInfoDTO> findAll(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        List<UserProjectRole> projects = userProjectRoleRepository.findAllByUser(userOpt.get());
        List<BugGetAllProjectInfoDTO> resultBugs = new ArrayList<>();

        for (UserProjectRole projectRole : projects) {
            BugGetAllProjectInfoDTO projectBugs = buildDTOFromProject(projectRole.getProject());
            if (projectBugs.getBugs().size() > 0) {
                resultBugs.add(projectBugs);
            }
        }

        return resultBugs;
    }

    @Override
    public BugGetAllProjectInfoDTO findAllFromProject(Long userId, Long projectId) throws GenericNotFoundException {
        Optional<Project> optProject = projectRepository.findById(projectId);

        if (!optProject.isPresent()) {
            throw new GenericNotFoundException("The project with id " + projectId + " doesn't exist!");
        }

        return buildDTOFromProject(optProject.get());
    }

    private BugGetAllProjectInfoDTO buildDTOFromProject(Project project) {
        BugGetAllProjectInfoDTO result = new BugGetAllProjectInfoDTO(project.getId(), project.getName());

        for (Bug bug : project.getBugs()) {
            result.addBug(new BugGetAllDTO(bug));
        }

        return result;
    }

    @Override
    @Transactional
    public BugGetByIdDTO create(Long userId, Long projectId, BugCreateDTO newBug) throws GenericNotFoundException {
        Optional<Project> optProject = projectRepository.findById(projectId);

        if (!optProject.isPresent()) {
            throw new GenericNotFoundException("The project with id " + projectId + " doesn't exist!");
        }

        Bug bug = new Bug(newBug);
        bug = bugRepository.save(bug);

        Project project = optProject.get();
        project.addBugs(bug);
        projectRepository.save(project);

        BugGetByIdDTO returnedBug = new BugGetByIdDTO();
        modelMapper.map(newBug, returnedBug);
        returnedBug.setId(bug.getId());
        returnedBug.setStatus(BugStatusEnum.OPEN);

        return returnedBug;
    }
}
