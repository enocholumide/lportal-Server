package com.enocholumide.repositories;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.OrganisationActivities;
import com.enocholumide.domain.shared.enumerated.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganisationActivitiesRepository extends JpaRepository<OrganisationActivities, Long> {

    List<OrganisationActivities> findAllByOrganisationAndRoleEquals(Organisation organisation, Role role);

}
