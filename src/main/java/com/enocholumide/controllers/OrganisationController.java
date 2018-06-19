package com.enocholumide.controllers;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisations;
import com.enocholumide.payloads.reponses.OrganisationPayload;
import com.enocholumide.services.organisations.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @GetMapping(value = "/api/organisations", name = "All Registered Organisations")
    private ResponseEntity<List<Organisation>> getAllOrganisations(){
        return organisationService.getAllOrganisations();
    }

    @GetMapping(value = "/api/organisations/{id}", name = "All User Organisations")
    private ResponseEntity<List<UserOrganisations>> getUserOrganisations(@PathVariable long id){
        return organisationService.getUserOrganisations(id);
    }

    @GetMapping(value = "/api/organisations/{org_id}/user/{user_id}", name = "User Organisation Details")
    private ResponseEntity<OrganisationPayload> getUserOrganisationsDetails(@PathVariable long org_id, @PathVariable long user_id){
        return organisationService.getUserOrganisationDetails(org_id, user_id);
    }

    @PutMapping(value = "/api/organisations/create", name = "Create a new Organisations")
    private ResponseEntity<Organisation> getAllOrganisations(@RequestBody Organisation organisation){
        return organisationService.createOrganisation(organisation);
    }
}
