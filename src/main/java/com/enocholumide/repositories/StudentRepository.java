package com.enocholumide.repositories;

import com.enocholumide.domain.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Boolean existsById(long id);
    Boolean existsByEmail(String email);
}
