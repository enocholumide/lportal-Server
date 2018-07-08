package com.enocholumide.services.assignments;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.UploadType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AssignmentService {

    ResponseEntity<List<Assignment>> getCourseAssignments(long courseID);

    ResponseEntity createAssignment(Assignment assignment, long courseID, long teacherID);
    ResponseEntity updateAssignment(Assignment assignment, long assID, long courseID, long teacherID);
    ResponseEntity deleteAssignment(long assID);

    ResponseEntity submitAnAssignment(CourseUploads courseUpload, long assID, long studentID );
    ResponseEntity getStudentAssignmentSubmissions(long assID, long studentID);
    ResponseEntity getSubmissionsForTeachers(long assID, UploadType uploadType);

    ResponseEntity deleteStudentSubmission(long handInID);
}
