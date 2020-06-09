package com.olo.olobugtracker.repositories;

import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.models.UsersConnectionInvitation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersConnectionInvitationRepository extends CrudRepository<UsersConnectionInvitation, Long> {
    List<UsersConnectionInvitation> findAllByReceiver(User receiver);
}
