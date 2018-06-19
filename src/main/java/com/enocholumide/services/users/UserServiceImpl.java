package com.enocholumide.services.users;

import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.domain.users.UserPrincipal;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<ApplicationUser> listAll() {
        return usersRepository.findAll();
    }

}
