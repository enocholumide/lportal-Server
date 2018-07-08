package com.enocholumide.services.courses;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.school.course.StudentCourse;
import com.enocholumide.domain.users.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    List<Course> listAll(long orgID);
    Course getCourse(long id);
    List<Student> getStudents(long id);
    ResponseEntity getAssignments(long id);
    ResponseEntity addContent(CourseUploads courseUpload, long courseID, long userID);
    ResponseEntity getContents(long courseID);
    ResponseEntity deleteContent(long courseID, long contentID);
    ResponseEntity getActivities(long courseID);
    ResponseEntity getTeacherCourses(long teacherID);
    ResponseEntity<List<Course>> getTeacherCoursesByOrganisation(long teacherID, long orgID);

    List<StudentCourse> getStudentCourses(long orgID, long studentID);

    ResponseEntity findAllByProgram(long programID);

}
