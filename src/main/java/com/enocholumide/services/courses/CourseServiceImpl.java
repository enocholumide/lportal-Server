package com.enocholumide.services.courses;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseFilter;
import com.enocholumide.domain.school.course.CourseNews;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.CourseNewsType;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.domain.users.Staff;
import com.enocholumide.domain.users.Student;
import com.enocholumide.repositories.AssignmentsRepository;
import com.enocholumide.repositories.CourseUplaodsRepository;
import com.enocholumide.repositories.CoursesRepository;
import com.enocholumide.repositories.UsersRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private AssignmentsRepository assignmentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CourseUplaodsRepository courseUplaodsRepository;

    @Override
    public List<Course> listAll() {
        return coursesRepository.findAll();
    }

    @Override
    public Course getCourse(long id) {
        return this.coursesRepository.getOne(id);
    }

    @Override
    public Set<Student> getStudents(long id) {
        return this.coursesRepository.getOne(id).getStudents();
    }

    @Override
    public ResponseEntity getAssignments(long courseID) {

        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);

        if(!courseOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.ok().body(coursesRepository.getOne(courseID).getAssignments());
    }

    @Override
    public ResponseEntity addContent(CourseUploads courseUpload, long courseID, long userID) {

        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);
        Optional<ApplicationUser> userOptional = this.usersRepository.findById(userID);

        if(!courseOptional.isPresent() || !userOptional.isPresent())
            return ResponseEntity.notFound().build();

        if(!userOptional.get().getRole().equals(Role.STAFF))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        courseUpload.setApplicationUser(userOptional.get());
        courseUpload.setCourse(courseOptional.get());
        courseUpload.setContent(UploadType.CONTENT);

        Course course = courseOptional.get();
        course.makeActivity(courseUpload.getName(), CourseNewsType.FILE_ADDED);

        course.getUploads().add(courseUpload);
        this.coursesRepository.save(course);

        return ResponseEntity.ok().body(this.coursesRepository.getCourseContent(course, UploadType.CONTENT));

    }

    @Override
    public ResponseEntity getContents(long courseID) {

        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);

        if(!courseOptional.isPresent())
            return ResponseEntity.notFound().build();

        Course course = courseOptional.get();
        UploadType uploadType = UploadType.CONTENT;

        List<CourseUploads> courseUploadsList = this.coursesRepository.getCourseContent(course, uploadType);

        return ResponseEntity.ok().body(courseUploadsList);
    }

    @Override
    public ResponseEntity deleteContent(long courseID, long contentID) {

        Optional<CourseUploads> courseUploadsOptional = this.courseUplaodsRepository.findById(contentID);

        if(!courseUploadsOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Content does not exist or have been deleted");

        CourseUploads courseUpload = courseUploadsOptional.get();

        if(!courseUpload.getApplicationUser().getRole().equals(Role.STAFF))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


        this.makeActivity(courseUpload.getCourse(), courseUpload, CourseNewsType.FILE_REMOVED);

        courseUploadsOptional.get().getCourse().makeActivity(courseUpload.getName(), CourseNewsType.FILE_REMOVED);

        this.courseUplaodsRepository.delete(courseUploadsOptional.get());

        List<CourseUploads> courseUploads = this.coursesRepository.getCourseContent(courseUpload.getCourse(), UploadType.CONTENT);

        return ResponseEntity.ok().body(courseUploads);
    }

    @Override
    public ResponseEntity getActivities(long courseID) {

        Optional<Course> courseOptional = this.coursesRepository.findById(courseID);

        if(!courseOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This course does not exist");

        return ResponseEntity.ok().body(courseOptional.get().getNews());
    }

    @Override
    public ResponseEntity getTeacherCourses(long teacherID) {

        try {
            Optional<ApplicationUser> user = this.usersRepository.findById(teacherID);
            Staff staff = (Staff) user.get();
            List<Course> list = this.coursesRepository.findCoursesByLecturersContaining(staff);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private void makeActivity(Course course, CourseUploads courseUpload, CourseNewsType courseNewsType) {

        CourseNews courseNews = new CourseNews();
        courseNews.setText(courseUpload.getName());
        courseNews.setCourse(course);
        courseNews.setType(courseNewsType);

        course.getNews().add(courseNews);

    }
}
