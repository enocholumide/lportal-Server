package com.enocholumide.controllers;

import com.enocholumide.services.schools.SchoolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organisations/{orgID}/schools")
public class SchoolsController {

    @Autowired
    private SchoolsService schoolsService;

    @GetMapping(value = "", name = "All Organisation schools")
    private ResponseEntity getOrganisationSchools(@PathVariable long orgID){
        return schoolsService.getOrganisationSchools(orgID);
    }


}
