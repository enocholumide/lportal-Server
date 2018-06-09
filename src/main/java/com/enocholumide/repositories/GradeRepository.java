package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.grade.Grade;
import com.enocholumide.domain.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("SELECT g FROM Grade g WHERE g.student = :student order by g.id, g.session desc ")
    List<Grade> findByStudent(@Param("student") Student student);

    @Query("SELECT g FROM Grade g WHERE g.course = :course order by g.id, g.gradeLevel desc ")
    List<Grade> getGradeStats(@Param("course") Course course);

    @Query("SELECT g FROM Grade g WHERE g.course = :course order by g.id, g.student.lastName desc ")
    List<Grade> getCourseGrades(@Param("course") Course course);

    @Query("SELECT g.student.registrationID FROM Grade g WHERE g.course = :course")
    List<Grade> getCourseStudentMatricNo(@Param("course") Course course);
}
