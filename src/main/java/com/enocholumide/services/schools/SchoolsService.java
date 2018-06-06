package com.enocholumide.services.schools;

import org.springframework.http.ResponseEntity;

public interface SchoolsService {

    ResponseEntity getSchools();
    ResponseEntity getDepartments();
    ResponseEntity getPrograms();
}
