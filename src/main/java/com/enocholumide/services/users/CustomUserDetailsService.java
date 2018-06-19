package com.enocholumide.services.users;

import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.domain.users.UserPrincipal;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser applicationUser = usersRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );
        return UserPrincipal.create(applicationUser);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        ApplicationUser user = usersRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
