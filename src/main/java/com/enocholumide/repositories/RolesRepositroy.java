package com.enocholumide.repositories;

import com.enocholumide.domain.shared.Roles;
import com.enocholumide.domain.shared.enumerated.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepositroy extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(Role role);
}
