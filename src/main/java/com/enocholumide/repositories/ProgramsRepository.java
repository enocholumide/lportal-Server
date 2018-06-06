package com.enocholumide.repositories;


import com.enocholumide.domain.school.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramsRepository extends JpaRepository<Program, Long> {
}
