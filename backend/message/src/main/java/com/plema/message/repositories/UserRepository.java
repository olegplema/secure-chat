package com.plema.message.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plema.message.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
