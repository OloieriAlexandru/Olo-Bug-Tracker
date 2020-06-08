package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.BugCreateDTO;
import com.olo.olobugtracker.dtos.BugGetAllProjectInfoDTO;
import com.olo.olobugtracker.dtos.BugGetByIdDTO;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;

import java.util.List;

public interface BugService {
    List<BugGetAllProjectInfoDTO> findAll(Long userId);

    BugGetAllProjectInfoDTO findAllFromProject(Long userId, Long projectId) throws GenericNotFoundException;

    BugGetByIdDTO create(Long userId, Long projectId, BugCreateDTO newBug) throws GenericNotFoundException;
}
