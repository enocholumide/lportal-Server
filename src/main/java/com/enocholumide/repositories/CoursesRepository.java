package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Course, Long> {
}
