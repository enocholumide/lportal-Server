package com.enocholumide.repositories;

import com.enocholumide.domain.users.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepository extends JpaRepository<Staff, Long> {

    boolean existsById(long id);
}
