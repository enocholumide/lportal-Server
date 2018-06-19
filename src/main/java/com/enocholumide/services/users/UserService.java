package com.enocholumide.services.users;

import com.enocholumide.domain.users.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<ApplicationUser> listAll();
}
