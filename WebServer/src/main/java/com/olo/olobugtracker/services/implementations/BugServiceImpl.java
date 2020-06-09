package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.*;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundBugException;
import com.olo.olobugtracker.exceptions.NotFoundProjectException;
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

    /**
     * Returns the bugs from all the projects the user identified by 'userId' id is assigned to
     *
     * @param userId The id of the user
     * @return A list of bugs
     */
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

    /**
     * Returns all the bugs from a specific project
     *
     * @param projectId The id of the project
     * @return A list of bugs
     * @throws GenericNotFoundException When there is no project with the specified id
     */
    @Override
    public BugGetAllProjectInfoDTO findAllFromProject(Long userId, Long projectId) throws GenericNotFoundException {
        Optional<Project> optProject = projectRepository.findById(projectId);

        if (!optProject.isPresent()) {
            throw new NotFoundProjectException(projectId);
        }

        return buildDTOFromProject(optProject.get());
    }

    /**
     * Creates a DTO used when sending bugs in a project
     *
     * @param project The project that contains bug
     * @return A DTO
     */
    private BugGetAllProjectInfoDTO buildDTOFromProject(Project project) {
        BugGetAllProjectInfoDTO result = new BugGetAllProjectInfoDTO(project.getId(), project.getName());

        for (Bug bug : project.getBugs()) {
            result.addBug(new BugGetAllDTO(bug));
        }

        return result;
    }

    /**
     * Creates a bug on the project identified by 'projectId' id
     *
     * @param userId    The user that created the bug
     * @param projectId The id of the project that has the bug
     * @param newBug    The newly created bug information
     * @return A Bug DTO
     * @throws GenericNotFoundException When there is no project with the specified id
     */
    @Override
    @Transactional
    public BugGetByIdDTO create(Long userId, Long projectId, BugCreateDTO newBug) throws GenericNotFoundException {
        Optional<Project> optProject = projectRepository.findById(projectId);

        if (!optProject.isPresent()) {
            throw new NotFoundProjectException(projectId);
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

    /**
     * Updates the information of the bug identified by 'updatedBug.id' id
     *
     * @param updatedBug The updated information of the bug identified by updatedBug.id
     */
    @Override
    public void update(BugUpdateDTO updatedBug) throws NotFoundBugException {
        Optional<Bug> optBug = bugRepository.findById(updatedBug.getId());

        if (!optBug.isPresent()) {
            throw new NotFoundBugException(updatedBug.getId());
        }

        Bug bug = optBug.get();
        bug.updateInformation(updatedBug);
        bugRepository.save(bug);
    }

    /**
     * Deletes the bug that has the id equal to 'bugId'
     *
     * @param bugId The id of the deleted bug
     * @throws NotFoundBugException When there is no bug with the specified id
     */
    @Override
    public void delete(Long bugId) throws NotFoundBugException {
        Optional<Bug> bug = bugRepository.findById(bugId);

        if (!bug.isPresent()) {
            throw new NotFoundBugException(bugId);
        }

        bugRepository.delete(bug.get());
    }
}
