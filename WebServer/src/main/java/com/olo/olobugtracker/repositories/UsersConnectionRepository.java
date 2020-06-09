package com.olo.olobugtracker.repositories;

import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.models.UsersConnection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersConnectionRepository extends CrudRepository<UsersConnection, Long> {
    List<UsersConnection> findAllByUser1OrUser2(User user1, User user2);
}
