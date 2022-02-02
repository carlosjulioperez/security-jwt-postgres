package com.nirsa.security.repository;

import java.util.Optional;

import com.nirsa.security.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  //Boolean existsByEmail(String email);
}
