package com.enocholumide.repositories;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrganisationRepository extends JpaRepository<UserOrganisations, Long> {

    List<UserOrganisations> findAllByApplicationUser_Id(Long id);
    Optional<UserOrganisations> findByApplicationUserIdAndOrganisationId(long applicationUserId, long organisationId);
}
