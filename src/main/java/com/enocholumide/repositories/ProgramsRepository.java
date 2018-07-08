package com.enocholumide.repositories;


import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.school.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramsRepository extends JpaRepository<Program, Long> {

    @Query("SELECT p FROM Program p WHERE p.department = :department GROUP BY p.id, p.level")
    List<Program> getLevelsByDepartment(@Param("department") Department department);

    Boolean existsById(long programID);
}
