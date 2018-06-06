package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.grade.Grade;
import com.enocholumide.domain.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("SELECT g FROM Grade g WHERE g.student = :student order by g.id, g.course.session, g.startDate asc ")
    List<Grade> findByStudent(@Param("student") Student student);

    @Query("SELECT g FROM Grade g WHERE g.course = :course order by g.id, g.gradeLevel desc ")
    List<Grade> getGradeStats(@Param("course") Course course);

}
