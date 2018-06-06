package com.enocholumide.controllers;

import com.enocholumide.services.schools.SchoolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolsController {

    @Autowired
    private SchoolsService schoolsService;

    @GetMapping(value = "/api/schools", name = "All schools")
    private ResponseEntity getSchools(){
        return schoolsService.getSchools();
    }

    @GetMapping(value = "/api/departments", name = "All departments")
    private ResponseEntity getDepartments(){
        return schoolsService.getDepartments();
    }

    @GetMapping(value = "/api/programs", name = "All programs")
    private ResponseEntity getPrograms(){
        return schoolsService.getPrograms();
    }
}
