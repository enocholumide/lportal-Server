package com.enocholumide.services.grades;

import org.springframework.http.ResponseEntity;

public interface GradesService {

    ResponseEntity findByStudent(long studentId);
    ResponseEntity getCourseStatistics(long courseID);
}
