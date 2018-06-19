package com.enocholumide.repositories;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisations;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.users.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);
    Boolean existsByEmail(String email);

    List<ApplicationUser> findApplicationUsersByOrganisationsContainingAndRolesEquals(UserOrganisations userOrganisation, Role role);

}
