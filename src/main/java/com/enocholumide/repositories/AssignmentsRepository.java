package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentsRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findAllByCourseId(long courseID);
    Boolean existsByTitle(String title);

    Boolean existsByIdAndCourseId(long assID, long courseID);

}
