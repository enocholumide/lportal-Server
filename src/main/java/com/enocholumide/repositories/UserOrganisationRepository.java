package com.enocholumide.repositories;

import com.enocholumide.domain.organisation.UserOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrganisationRepository extends JpaRepository<UserOrganisation, Long> {

    List<UserOrganisation> findAllByApplicationUser_Id(Long id);
    Optional<UserOrganisation> findByApplicationUserIdAndOrganisationId(long applicationUserId, long organisationId);

    Boolean existsByApplicationUserIdAndOrganisationId(long userID, long orgID);
}
