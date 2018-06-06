package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentsRepository extends JpaRepository<Assignment, Long> {
}
