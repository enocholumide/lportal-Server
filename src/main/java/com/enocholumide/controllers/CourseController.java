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
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private UsersRepository usersRepository;



    @GetMapping(value = "/api/courses", name = "All Courses")
    private ResponseEntity getAllCourses(){
        return ResponseEntity.ok().body(courseService.listAll());
    }

    @GetMapping(value = "/api/courses/{id}", name = "A Course")
    private ResponseEntity getCourse(@PathVariable long id){
        Optional<Course> courseOptional = coursesRepository.findById(id);

        if(!courseOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

    @GetMapping(value = "/api/courses/{id}/students", name = "Course Students")
    private ResponseEntity getAllCourseStudents(@PathVariable long id){

        ResponseEntity responseEntity = getCourse(id);

        if(!responseEntity.getStatusCode().equals(HttpStatus.OK))
            return responseEntity;

        return ResponseEntity.ok().body(courseService.getStudents(id));
    }

    @GetMapping(value = "/api/teachers/{id}/courses", name = "Get course containing a staff")
    private ResponseEntity getTeacherCourses(@PathVariable long id) {
        return courseService.getTeacherCourses(id);
    }

    /**
     * ASSIGNMENTS
     */


    @GetMapping(value = "/api/courses/{id}/assignments", name = "Get Course Assignments")
    private ResponseEntity getAssignments(@PathVariable long id){
        return courseService.getAssignments(id);
    }

    @PutMapping(value = "/api/courses/{c_id}/assignments/create/{u_id}", name = "Create a new assignment")
    private ResponseEntity<Set<Assignment>> createAssignment( @RequestBody Assignment assignment, @PathVariable long c_id, @PathVariable long u_id ) {
        return this.assignmentService.createAssignment(assignment, c_id, u_id);
    }

    @PutMapping(value = "/api/courses/{c_id}/assignments/{a_id}/update/{u_id}", name = "Update an assignment")
    private ResponseEntity<Set<Assignment>> updateAssignment( @RequestBody Assignment assignment, @PathVariable long c_id, @PathVariable long u_id, @PathVariable long a_id ) {
        return assignmentService.updateAssignment(assignment, c_id, u_id, a_id);
    }

    @PutMapping(value = "/api/courses/{id}/assignment/{a_id}/submit/{s_id}", name = "Submit an assignment handIn")
    private ResponseEntity submitAssignment(@RequestBody CourseUploads courseUpload, @PathVariable long id, @PathVariable long a_id, @PathVariable long s_id){
        return assignmentService.submitAssignment(courseUpload, id, a_id, s_id);
    }

    @DeleteMapping(value = "/api/courses/handin/delete/{h_id}", name = "Delete an assignment handIn")
    private ResponseEntity deleteSubmittedAssignment(@PathVariable long h_id){
        return assignmentService.deleteSubmittedAssignment(h_id);
    }

    @GetMapping(value = "/api/courses/{id}/assignment/{a_id}/submissions/{s_id}", name = "Get assignment submission for a student")
    private ResponseEntity getStudentAssignments(@PathVariable long id, @PathVariable long a_id, @PathVariable long s_id){
        return assignmentService.getSubmissions(id, s_id, a_id);
    }

    @DeleteMapping(value = "/api/courses/{c_id}/assignments/{a_id}/delete/{u_id}", name = "Delete an assignment")
    private ResponseEntity<Set<Assignment>> deleteAssignment( @PathVariable long a_id, @PathVariable long c_id, @PathVariable long u_id ) {
        return this.assignmentService.deleteAssignment(a_id, c_id, u_id);
    }


    /**
     * ACTIVITIES
     */


    @GetMapping(value = "/api/courses/{id}/activities", name = "All Course Activities")
    private ResponseEntity getActivities(@PathVariable long id){
        return courseService.getActivities(id);
    }

    @GetMapping(value = "/api/courses/{id}/contents", name = "Get course contents/ files ")
    private ResponseEntity getContents(@PathVariable long id){
        return courseService.getContents(id);
    }


    /**
     * COURSE CONTENT
     */


    @PutMapping(value = "/api/courses/{id}/content/{u_id}/create", name = "Create a course content")
    private ResponseEntity addContent (@RequestBody CourseUploads courseUpload, @PathVariable long id, @PathVariable long u_id){
        return courseService.addContent(courseUpload, id, u_id);
    }

    @DeleteMapping(value = "/api/courses/{id}/content/{co_id}/delete", name = "Delete a course content")
    private ResponseEntity deleteContent (@PathVariable long id, @PathVariable long co_id){
        return courseService.deleteContent(id, co_id);
    }




}
