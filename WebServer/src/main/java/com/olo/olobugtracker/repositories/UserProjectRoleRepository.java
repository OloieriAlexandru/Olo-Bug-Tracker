package com.olo.olobugtracker.repositories;

import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.models.UserProjectRole;
import com.olo.olobugtracker.models.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRoleRepository extends CrudRepository<UserProjectRole, Long> {
    List<UserProjectRole> findAllByUser(User user);

    Optional<UserProjectRole> findByUserAndRole(User user, UserRole role);
}
