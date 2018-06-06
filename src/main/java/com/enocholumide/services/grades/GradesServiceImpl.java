package com.enocholumide.services.grades;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.grade.Grade;
import com.enocholumide.domain.school.grade.GradeDetails;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.domain.users.Student;
import com.enocholumide.repositories.CoursesRepository;
import com.enocholumide.repositories.GradeRepository;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradesServiceImpl implements GradesService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private CoursesRepository courseRepository;


    @Override
    public ResponseEntity findByStudent(long studentId) {

        Optional<ApplicationUser> userOptional = userRepository.findById(studentId);

        if(!userOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");

        if(!userOptional.get().getRole().equals(Role.STUDENT))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not a student");

        Student student = (Student) userOptional.get();

        List<Grade> studentGrades = this.gradeRepository.findByStudent(student);

        return ResponseEntity.ok().body(studentGrades);

    }

    @Override
    public ResponseEntity getCourseStatistics(long courseID) {

        Optional<Course> courseOptional = this.courseRepository.findById(courseID);

        if(!courseOptional.isPresent())
            return ResponseEntity.notFound().build();

        Course course = courseOptional.get();

        List<Grade> statsGrade = this.gradeRepository.getGradeStats(course);
        return ResponseEntity.ok().body(new GradeDetails(statsGrade));
    }
}