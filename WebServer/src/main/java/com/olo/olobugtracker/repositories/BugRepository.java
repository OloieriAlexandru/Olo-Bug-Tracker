package com.olo.olobugtracker.repositories;

import com.olo.olobugtracker.models.Bug;
import org.springframework.data.repository.CrudRepository;

public interface BugRepository extends CrudRepository<Bug, Long> {
}
