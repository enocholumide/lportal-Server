package com.enocholumide.services.users;

import com.enocholumide.domain.users.ApplicationUser;

import java.util.List;

public interface UserService {

    List<ApplicationUser> listAll();
}
