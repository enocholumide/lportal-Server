package com.enocholumide.services.organisations;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisations;
import com.enocholumide.payloads.reponses.OrganisationPayload;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganisationService {

    ResponseEntity<List<Organisation>> getAllOrganisations();
    ResponseEntity<List<UserOrganisations>> getUserOrganisations(Long id);
    ResponseEntity<Organisation> createOrganisation(Organisation organisation);
    ResponseEntity<OrganisationPayload> getUserOrganisationDetails(long organisationID, long userID);
    ResponseEntity<Organisation> editOrganisation(long organisationID);
    void removeOrganisation();

}
