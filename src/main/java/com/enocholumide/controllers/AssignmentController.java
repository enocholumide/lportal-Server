package com.enocholumide.controllers;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.enocholumide.services.assignments.AssignmentService;
import com.enocholumide.services.courses.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;


    @GetMapping(value = "/course/{courseID}", name = "Get Course Assignments")
    private ResponseEntity<List<Assignment>> getAssignments(@PathVariable long courseID){
        return assignmentService.getCourseAssignments(courseID);
    }

    @DeleteMapping(value = "/{assID}/delete")
    private ResponseEntity deleteAssignment(@PathVariable long assID) {
        return assignmentService.deleteAssignment(assID);
    }

    @PutMapping(value = "/create/course/{courseID}/teacher/{teacherID}", name = "Create a new assignment")
    private ResponseEntity<?> createAssignment(@RequestBody Assignment assignment, @PathVariable long courseID, @PathVariable long teacherID ) {
        return this.assignmentService.createAssignment(assignment, courseID, teacherID);
    }

    @PutMapping(value = "/{assgID}/update/course/{courseID}/teacher/{teacherID}", name = "Update an assignment")
    private ResponseEntity<Set<Assignment>> updateAssignment( @RequestBody Assignment assignment, @PathVariable long assgID, @PathVariable long courseID, @PathVariable long teacherID ) {
        return assignmentService.updateAssignment(assignment, assgID, courseID, teacherID);
    }

    @GetMapping(value = "/{assgID}/submissions", name = "Get all assignment submissions")
    private ResponseEntity submitAssignment(@PathVariable long assgID){
        return assignmentService.getSubmissionsForTeachers(assgID, UploadType.HANDIN);
    }

    @PutMapping(value = "/{assgID}/submissions/create/{studentID}", name = "Submit an assignment handIn")
    private ResponseEntity submitAssignment(@RequestBody CourseUploads courseUpload, @PathVariable long assgID, @PathVariable long studentID){
        return assignmentService.submitAnAssignment(courseUpload, assgID, studentID);
    }

    @GetMapping(value = "/{assgID}/submissions/read/{studentID}", name = "Get assignment submissions for a student")
    private ResponseEntity getStudentAssignmentSubmissions( @PathVariable long assgID, @PathVariable long studentID){
        return assignmentService.getStudentAssignmentSubmissions(assgID, studentID);
    }

    @DeleteMapping(value = "/submissions/delete/{handInID}", name = "Delete an assignment handIn")
    private ResponseEntity deleteSubmittedAssignment(@PathVariable long handInID){
        return assignmentService.deleteStudentSubmission(handInID);
    }
}
