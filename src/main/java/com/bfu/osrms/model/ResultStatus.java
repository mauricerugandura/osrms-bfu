package com.bfu.osrms.model;

/**
 * Enum for Result status
 */
public enum ResultStatus {
    PENDING,      // Entered by lecturer, waiting for approval
    APPROVED,     // Approved by registrar
    PUBLISHED     // Published to student
}
