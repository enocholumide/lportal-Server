package com.enocholumide.repositories;

import com.enocholumide.domain.school.School;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    List<School> findAllByOrganisationId(long orgID);

}