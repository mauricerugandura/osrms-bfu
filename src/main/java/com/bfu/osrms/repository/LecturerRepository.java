package com.bfu.osrms.repository;

import com.bfu.osrms.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY LAYER - LecturerRepository
 */
@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByLecturerId(String lecturerId);
    Optional<Lecturer> findByUserId(Long userId);
}
