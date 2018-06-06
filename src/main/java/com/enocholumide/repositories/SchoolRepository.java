package com.enocholumide.repositories;

import com.enocholumide.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

}