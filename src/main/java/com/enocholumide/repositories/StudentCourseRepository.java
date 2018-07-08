package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.StudentCourse;
import com.enocholumide.domain.shared.enumerated.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {


    List<StudentCourse> findAllByCourseIdAndStatusEquals(long courseId, Status status);

    List<StudentCourse> findAllByStudentIdAndCourseProgramDepartmentSchoolOrganisationIdAndStatusEquals(long studID, long orgId, Status status);

    List<StudentCourse> findAllByCourseIdAndStudentIdAndStatusEquals(long courseId, long studentId, Status status);

    List<StudentCourse> findAllByStudentIdAndStatusEquals  ( long studentId, Status status);
}
