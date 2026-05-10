package com.bfu.osrms.service;

import com.bfu.osrms.model.Lecturer;
import com.bfu.osrms.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE LAYER - LecturerService
 * Handles lecturer-specific business logic
 */
@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    /**
     * Get lecturer by lecturer ID
     */
    public Optional<Lecturer> getLecturerByLecturerId(String lecturerId) {
        return lecturerRepository.findByLecturerId(lecturerId);
    }

    /**
     * Get lecturer by user ID
     */
    public Optional<Lecturer> getLecturerByUserId(Long userId) {
        return lecturerRepository.findByUserId(userId);
    }

    /**
     * Get all registered lecturers
     */
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    /**
     * Create or update lecturer
     */
    public Lecturer saveLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }
}
