package com.enocholumide.services.assignments;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseNews;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.CourseNewsType;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.repositories.AssignmentsRepository;
import com.enocholumide.repositories.CourseUplaodsRepository;
import com.enocholumide.repositories.CoursesRepository;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    private CourseUplaodsRepository courseUplaodsRepository;

    @Override
    public ResponseEntity createAssignment(com.enocholumide.domain.school.course.Assignment assignment, long courseID, long teacherID) {

        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);
        Optional<ApplicationUser> userOptional = this.usersRepository.findById(teacherID);

        if(!courseOptional.isPresent() || !userOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find course or applicationUser");

        if(!userOptional.get().getRole().equals(Role.STAFF))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only a teacher can create an assignment");

        boolean notAllowed = true;
        for(ApplicationUser applicationUser : courseOptional.get().getLecturers()){
            if(applicationUser.equals(userOptional.get())){
                notAllowed = false;
                break;
            }
        }

        if(notAllowed)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The teacher is not authorized to create an assignment");

        Course course = courseOptional.get();
        course.addAssignment(assignment);
        course.makeActivity(assignment.getTitle(), CourseNewsType.ASSIGNMENT_ADDED);

        this.coursesRepository.save(course);

        return ResponseEntity.ok().body(course.getAssignments());
    }

    @Override
    public ResponseEntity updateAssignment(Assignment assignment, long courseID, long teacherID, long assID) {

        ResponseEntity responseEntity = this.validateCourseAssignment(courseID, teacherID, assID);

        if(!responseEntity.getStatusCode().equals(HttpStatus.OK))
            return responseEntity;

        Course course = this.coursesRepository.findById(courseID).get();
        Assignment assignmentEdited = this.assignmentsRepository.findById(assID).get();

        if(!course.getAssignments().contains(assignmentEdited))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This assignment was not found in the course");

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
    public ResponseEntity submitAssignment(CourseUploads courseUpload, long courseID, long assignmentID, long studentID) {

        // Find the assignment and the course and also the student
        Optional<Course> courseOptional = coursesRepository.findById(courseID);
        Optional<ApplicationUser> applicationUser = usersRepository.findById(studentID);
        Optional<Assignment> assignmentOptional = assignmentsRepository.findById(assignmentID);

        if(!courseOptional.isPresent() || !applicationUser.isPresent() || !assignmentOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find course or applicationUser or assignment");

        Course course = courseOptional.get();
        Assignment assignment = assignmentOptional.get();

        if(!course.getAssignments().contains(assignment))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mismatch, course given does not contain the assignment given");

        // Compare dates
        if( assignmentOptional.get().getDeadline().compareTo(new Date()) < 0)
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deadline has passed");

        courseUpload.setApplicationUser(applicationUser.get());
        courseUpload.setCourse(courseOptional.get());
        courseUpload.setContent(UploadType.HANDIN);
        courseUpload.setAssignment(assignmentOptional.get());

        course.getUploads().add(courseUpload);

        this.coursesRepository.save(course);

        return ResponseEntity.ok().body(assignment.getUploads());
    }

    @Override
    public ResponseEntity deleteAssignment(long assID, long courseID, long studentID) {

        ResponseEntity responseEntity = this.validateCourseAssignment(courseID, studentID, assID);

        if(!responseEntity.getStatusCode().equals(HttpStatus.OK))
            return responseEntity;

        Course course = this.coursesRepository.getOne(courseID);
        Assignment assignment = this.assignmentsRepository.getOne(assID);

        CourseNews courseNews = new CourseNews();
        courseNews.setCourse(course);
        courseNews.setType(CourseNewsType.ASSIGNMENT_DELETED);
        courseNews.setText(assignment.getTitle());

        course.getNews().add(courseNews);
        course.getAssignments().remove(assignment);

        this.coursesRepository.save(course);

        return ResponseEntity.ok().body(course.getAssignments());
    }

    @Override
    public ResponseEntity getSubmissions(long courseID, long studentID, long assignmentID) {

        // Find the assignment and the course and also the student
        Optional<Course> courseOptional = coursesRepository.findById(courseID);
        Optional<ApplicationUser> applicationUser = usersRepository.findById(studentID);
        Optional<Assignment> assignmentOptional = assignmentsRepository.findById(assignmentID);

        if(!courseOptional.isPresent() || !applicationUser.isPresent() || !assignmentOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find course or applicationUser or assignment");

        if(!applicationUser.get().getRole().equals(Role.STUDENT))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This applicationUser is not a student");

        if(!courseOptional.get().getStudents().contains(applicationUser.get()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This student is not enrolled in this course");


        Course course = courseOptional.get();
        ApplicationUser user = applicationUser.get();
        Assignment assignment = assignmentOptional.get();

        List<CourseUploads> courseUploads = this.coursesRepository.findByCourseAndUser(course, user, assignment);

        return ResponseEntity.ok().body(courseUploads);
    }

    @Override
    public ResponseEntity deleteSubmittedAssignment(long handinID) {

        Optional<CourseUploads> courseUploadsOptional = this.courseUplaodsRepository.findById(handinID);

        if(!courseUploadsOptional.isPresent())
            return ResponseEntity.notFound().build();

        CourseUploads courseUpload = courseUploadsOptional.get();

        Course course = courseUpload.getCourse();
        ApplicationUser applicationUser = courseUpload.getApplicationUser();

        this.courseUplaodsRepository.delete(courseUploadsOptional.get());

        List<CourseUploads> courseUploads = this.coursesRepository.findUserUploads(course, applicationUser, courseUpload.getAssignment(), UploadType.HANDIN);

        return ResponseEntity.ok().body(courseUploads);
    }

    private ResponseEntity validateCourseAssignment(long courseID, long userID, long assID){


        Optional<Assignment> assignmentOptional = this.assignmentsRepository.findById(assID);
        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);
        Optional<ApplicationUser> userOptional = this.usersRepository.findById(userID);

        if(!courseOptional.isPresent() || !userOptional.isPresent() || !assignmentOptional.isPresent()){

            List<String> messages = new ArrayList<>();
            if(!courseOptional.isPresent())
                messages.add("This course doesn't exist or have been deleted");

            if(!userOptional.isPresent())
                messages.add("This applicationUser doesn't exist or have been deleted");

            if(!assignmentOptional.isPresent())
                messages.add("This assignment doesn't exist or have been deleted");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messages);
        }

        if(!userOptional.get().getRole().equals(Role.STAFF))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This applicationUser is not a staff");


        if(!courseOptional.get().getLecturers().contains(userOptional.get()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This staff is not authorized to edit the content of this course");

        return ResponseEntity.ok().build();
    }
}
