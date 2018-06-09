package com.enocholumide.services.courses;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.school.course.Schedule;
import com.enocholumide.domain.users.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface CourseService {

    List<Course> listAll();
    Course getCourse(long id);
    Set<Student> getStudents(long id);
    ResponseEntity getAssignments(long id);
    ResponseEntity addContent(CourseUploads courseUpload, long courseID, long userID);
    ResponseEntity getContents(long courseID);
    ResponseEntity deleteContent(long courseID, long contentID);
    ResponseEntity getActivities(long courseID);
    ResponseEntity getTeacherCourses(long teacherID);

}
