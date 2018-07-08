package com.enocholumide.controllers;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.repositories.CoursesRepository;
import com.enocholumide.repositories.UsersRepository;
import com.enocholumide.services.assignments.AssignmentService;
import com.enocholumide.services.courses.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private UsersRepository usersRepository;



    @GetMapping(value = "/program/{pID}", name = "All Courses")
    private ResponseEntity getAllCourses(@PathVariable long pID){
        return ResponseEntity.ok().body(courseService.listAll(pID));
    }

    @GetMapping(value = "/{id}", name = "A Course")
    private ResponseEntity getCourse(@PathVariable long id){
        Optional<Course> courseOptional = coursesRepository.findById(id);

        if(!courseOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

    @GetMapping(value = "/{id}/students", name = "Course Students")
    private ResponseEntity getAllCourseStudents(@PathVariable long id){

        return ResponseEntity.ok().body(courseService.getStudents(id));
    }

    @GetMapping(value = "/org/{orgID}/students/{stud_id}", name = "Student Courses")
    private ResponseEntity getStudentCourses (@PathVariable long orgID, @PathVariable long stud_id){

        return ResponseEntity.ok().body(courseService.getStudentCourses(orgID, stud_id));
    }

    /**
     * ASSIGNMENTS
     */





    /**
     * ACTIVITIES
     */


    @GetMapping(value = "/{id}/activities", name = "All Course Activities")
    private ResponseEntity getActivities(@PathVariable long id){
        return courseService.getActivities(id);
    }

    /**
     * COURSE CONTENTS / FILES
     */

    @GetMapping(value = "/{courseID}/content/read", name = "Get course contents/ files")
    private ResponseEntity getContents (@PathVariable long courseID ){
        return courseService.getContents(courseID);
    }


    @PutMapping(value = "/{courseID}/content/create/{userID}", name = "Create a course content")
    private ResponseEntity addContent (@RequestBody CourseUploads courseUpload, @PathVariable long courseID, @PathVariable long userID){
        return courseService.addContent(courseUpload, courseID, userID);
    }

    @DeleteMapping(value = "/{courseID}/content/delete/{contentID}", name = "Delete a course content")
    private ResponseEntity deleteContent (@PathVariable long courseID, @PathVariable long contentID){
        return courseService.deleteContent(courseID, contentID);
    }

}
