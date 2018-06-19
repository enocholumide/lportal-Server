package com.enocholumide.controllers;

import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.payloads.reponses.ApiResponse;
import com.enocholumide.payloads.reponses.JwtAuthenticationResponse;
import com.enocholumide.payloads.requests.LoginRequest;
import com.enocholumide.payloads.requests.SignUpRequest;
import com.enocholumide.repositories.RolesRepositroy;
import com.enocholumide.repositories.UsersRepository;
import com.enocholumide.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RolesRepositroy rolesRepositroy;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/signin/token/{token}")
    public ResponseEntity<?> authenticateUser(@PathVariable String token) {

        Long id = tokenProvider.getUserIdFromJWT(token);
        Optional<ApplicationUser> applicationUser = userRepository.findById(id);

        if(!applicationUser.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");

        return ResponseEntity.ok(applicationUser.get());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        ApplicationUser user = new ApplicationUser(signUpRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ApplicationUser applicationUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(applicationUser.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
