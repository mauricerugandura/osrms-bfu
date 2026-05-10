package com.bfu.osrms.repository;

import com.bfu.osrms.model.User;
import com.bfu.osrms.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * REPOSITORY LAYER - UserRepository
 * Provides database access for User entities
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
}
