package com.enocholumide.repositories;

import com.enocholumide.domain.school.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
