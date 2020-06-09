package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UsersConnection")
@Table(name = "users_connections")
public class UsersConnection {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    public UsersConnection(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Long getId() {
        return id;
    }

    public User getOtherUser(User user) {
        if (user.equals(user1)) {
            return user2;
        }
        return user1;
    }
}
