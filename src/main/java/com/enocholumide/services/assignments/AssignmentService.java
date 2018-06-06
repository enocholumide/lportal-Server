package com.enocholumide.services.assignments;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.CourseUploads;
import org.springframework.http.ResponseEntity;

public interface AssignmentService {
    ResponseEntity createAssignment(Assignment assignment, long courseID, long teacherID);
    ResponseEntity updateAssignment(Assignment assignment, long courseID, long teacherID, long assID);
    ResponseEntity submitAssignment(CourseUploads courseUpload, long courseID, long assignmentID, long studentID);
    ResponseEntity deleteAssignment(long assID, long courseID, long studentID);
    ResponseEntity getSubmissions(long courseID, long studentID, long assID);
    ResponseEntity deleteSubmittedAssignment(long assID);
}
