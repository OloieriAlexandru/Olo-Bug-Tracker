package com.olo.olobugtracker.repositories;

import com.olo.olobugtracker.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByName(String name);
}
