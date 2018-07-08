package com.enocholumide.services.organisations;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganisationService {

    ResponseEntity<List<Organisation>> getAllOrganisations();
    ResponseEntity<List<UserOrganisation>> getUserOrganisations(Long id);
    ResponseEntity<Organisation> createOrganisation(Organisation organisation, long userID);
    ResponseEntity<?> getUserOrganisationDetails(long organisationID, long userID);
    ResponseEntity<Organisation> editOrganisation(long organisationID);
    void removeOrganisation();

}
