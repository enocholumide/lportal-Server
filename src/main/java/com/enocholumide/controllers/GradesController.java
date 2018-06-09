package com.enocholumide.controllers;

import com.enocholumide.services.grades.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradesController {

    @Autowired
    private GradesService gradesService;

    @GetMapping(value = "/api/grades/student/{student_id}", name = "Get a student grade")
    private ResponseEntity findByStudent(@PathVariable long student_id){
        return  gradesService.findByStudent(student_id);
    }
    @GetMapping(value = "/api/grades/stat/{course_id}", name = "Get grade stastistics from a course")
    private ResponseEntity getCourseStatistics(@PathVariable long course_id){
        return gradesService.getCourseStatistics(course_id);
    }

    @GetMapping(value = "/api/students/regid/{course_id}", name = "Get all student matric no for a course")
    private ResponseEntity getCourseGrades(@PathVariable long course_id){
        return gradesService.getCourseGrades(course_id);
    }

}
