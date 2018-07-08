package com.enocholumide.services.assignments;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseNews;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.CourseNewsType;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private AssignmentsRepository assignmentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CourseUplaodsRepository courseUploadsRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    @Override
    public ResponseEntity createAssignment(Assignment assignment, long courseID, long teacherID) {

        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);

        if(!this.teachersRepository.existsById(teacherID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Teacher with supplied ID not found");

        if(!this.coursesRepository.existsById(courseID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course with supplied ID not found");


        assignment.setCourse(courseOptional.get());

        Course course = courseOptional.get();
        course.addAssignment(assignment);
        course.makeActivity(assignment.getTitle(), CourseNewsType.ASSIGNMENT_ADDED);

        this.coursesRepository.save(course);

        return ResponseEntity.ok().body(course.getAssignments());
    }

    @Override
    public ResponseEntity updateAssignment(Assignment assignment,long assID, long courseID, long teacherID) {

        if(!this.assignmentsRepository.existsByIdAndCourseId(assID, courseID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This assignment was not found in the course specified");

        Course course = this.coursesRepository.findById(courseID).get();
        Assignment assignmentEdited = this.assignmentsRepository.findById(assID).get();

        assignmentEdited.setDeadline(assignment.getDeadline());
        assignmentEdited.setType(assignment.getType());
        assignmentEdited.setTitle(assignment.getTitle());
        assignmentEdited.setDescription(assignment.getDescription());
        assignmentEdited.setNotes(assignment.getNotes());

        course.makeActivity(assignment.getTitle(), CourseNewsType.ASSIGNMENT_UPDATED);

        this.coursesRepository.save(course);
        this.assignmentsRepository.save(assignmentEdited);

        return ResponseEntity.ok().body(course.getAssignments());
    }

    @Override
    public ResponseEntity submitAnAssignment(CourseUploads courseUpload, long assignmentID, long studentID) {

        // Find the assignment and the course and also the student
        Optional<ApplicationUser> applicationUser = usersRepository.findById(studentID);
        Optional<Assignment> assignmentOptional = assignmentsRepository.findById(assignmentID);

        if(!applicationUser.isPresent() || !assignmentOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find applicationUser or assignment");

        Course course = assignmentOptional.get().getCourse();
        Assignment assignment = assignmentOptional.get();

        if(!course.getAssignments().contains(assignment))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mismatch, course given does not contain the assignment given");

        // Compare dates
        if( assignmentOptional.get().getDeadline().compareTo(Instant.now()) < 0)
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deadline has passed");

        courseUpload.setApplicationUser(applicationUser.get());
        courseUpload.setCourse(course);
        courseUpload.setContent(UploadType.HANDIN);
        courseUpload.setAssignment(assignment);

        course.getUploads().add(courseUpload);

        this.coursesRepository.save(course);
        return ResponseEntity.ok().body(assignment.getUploads());
    }

    @Override
    public ResponseEntity deleteAssignment(long assID) {

        if(!this.assignmentsRepository.existsById(assID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment not found");



        Assignment assignment = this.assignmentsRepository.getOne(assID);
        long courseID = assignment.getCourse().getId();
        Course course = this.coursesRepository.getOne(courseID);

        CourseNews courseNews = new CourseNews();
        courseNews.setCourse(course);
        courseNews.setType(CourseNewsType.ASSIGNMENT_DELETED);
        courseNews.setText(assignment.getTitle());

        course.getNews().add(courseNews);

        course.getAssignments().remove(assignment);
        this.assignmentsRepository.delete(assignment);

        this.coursesRepository.save(course);
        return ResponseEntity.ok().body(getCourseAssignments(courseID));
    }

    @Override
    public ResponseEntity<List<CourseUploads>> getStudentAssignmentSubmissions(long assignmentID, long studentID) {

        return ResponseEntity.ok().body(this.courseUploadsRepository.findAllByAssignmentIdAndApplicationUserId(assignmentID, studentID));
    }

    @Override
    public ResponseEntity getSubmissionsForTeachers(long assID, UploadType uploadType) {
        List<CourseUploads> list = this.courseUploadsRepository.findAllByAssignmentIdAndContentEquals(assID, UploadType.HANDIN);
        return ResponseEntity.ok().body(list);
    }

    @Override
    public ResponseEntity deleteStudentSubmission(long handinID) {

        Optional<CourseUploads> courseUploadsOptional = this.courseUploadsRepository.findById(handinID);

        if(!courseUploadsOptional.isPresent())
            return ResponseEntity.notFound().build();

        CourseUploads courseUpload = courseUploadsOptional.get();

        Course course = courseUpload.getCourse();
        ApplicationUser applicationUser = courseUpload.getApplicationUser();

        this.courseUploadsRepository.delete(courseUploadsOptional.get());

        List<CourseUploads> courseUploads = this.coursesRepository.findUserUploads(course, applicationUser, courseUpload.getAssignment(), UploadType.HANDIN);

        return ResponseEntity.ok().body(courseUploads);
    }

    @Override
    public ResponseEntity<List<Assignment>> getCourseAssignments(long courseID) {
        return ResponseEntity.ok().body(this.assignmentsRepository.findAllByCourseId(courseID));
    }

}
