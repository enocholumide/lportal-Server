package com.enocholumide.repositories;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.OrganisationActivities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    Optional<Organisation> findByName(String name);



}
