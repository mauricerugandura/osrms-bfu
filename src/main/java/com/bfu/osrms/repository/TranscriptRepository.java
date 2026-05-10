package com.bfu.osrms.repository;

import com.bfu.osrms.model.Transcript;
import com.bfu.osrms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY LAYER - TranscriptRepository
 */
@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {
    Optional<Transcript> findByTranscriptId(String transcriptId);
    Optional<Transcript> findByStudent(Student student);
}
