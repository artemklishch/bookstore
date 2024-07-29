package org.example.intro.repository.user;

import java.util.Optional;
import org.example.intro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
