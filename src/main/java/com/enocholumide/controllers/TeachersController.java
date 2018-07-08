package com.enocholumide.controllers;

import com.enocholumide.services.courses.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/teachers")
public class TeachersController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/{teacherID}/courses", name = "Get teacher courses")
    private ResponseEntity getCourses(@PathVariable long teacherID) {
        return courseService.getTeacherCourses(teacherID);
    }

    @GetMapping(value = "/{teacherID}/org/{orgID}/courses", name = "Get teacher courses by organisation")
    private ResponseEntity getCoursesByOrganisation(@PathVariable long teacherID, @PathVariable long orgID) {
        return courseService.getTeacherCoursesByOrganisation(teacherID, orgID);
    }
}
