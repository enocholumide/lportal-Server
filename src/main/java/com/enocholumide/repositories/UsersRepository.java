package com.enocholumide.repositories;

import com.enocholumide.domain.users.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<ApplicationUser, Long> {
}
