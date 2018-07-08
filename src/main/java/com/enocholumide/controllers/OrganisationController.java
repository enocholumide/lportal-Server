package com.enocholumide.controllers;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisation;
import com.enocholumide.services.organisations.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organisations")
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @GetMapping(value = "/", name = "All Registered Organisations")
    private ResponseEntity<List<Organisation>> getAllOrganisations(){
        return organisationService.getAllOrganisations();
    }

    @GetMapping(value = "/{id}", name = "All User Organisations")
    private ResponseEntity<List<UserOrganisation>> getUserOrganisations(@PathVariable long id){
        return organisationService.getUserOrganisations(id);
    }

    @GetMapping(value = "/{org_id}/user/{user_id}", name = "User Organisation Details")
    private ResponseEntity<?> getUserOrganisationsDetails(@PathVariable long org_id, @PathVariable long user_id){
        return organisationService.getUserOrganisationDetails(org_id, user_id);
    }


    @PutMapping(value = "/user/{userId}/create", name = "Create a new Organisations")
    private ResponseEntity<Organisation> createAnOrganisation(@RequestBody Organisation organisation, @PathVariable long userId ){
        return organisationService.createOrganisation(organisation, userId  );
    }


}
