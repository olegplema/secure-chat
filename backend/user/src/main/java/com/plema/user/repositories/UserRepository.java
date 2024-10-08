package com.plema.user.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.plema.user.entities.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUsername(String username);
}
