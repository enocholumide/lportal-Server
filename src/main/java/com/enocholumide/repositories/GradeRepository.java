package com.enocholumide.repositories;

import com.enocholumide.domain.school.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
