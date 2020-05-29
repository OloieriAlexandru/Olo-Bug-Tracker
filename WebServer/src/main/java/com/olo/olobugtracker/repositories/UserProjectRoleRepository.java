package com.olo.olobugtracker.repositories;

import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.models.UserProjectRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserProjectRoleRepository extends CrudRepository<UserProjectRole, Long> {
    List<UserProjectRole> findAllByUser(User user);
}
