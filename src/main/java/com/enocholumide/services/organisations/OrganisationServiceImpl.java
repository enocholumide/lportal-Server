package com.enocholumide.services.organisations;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisations;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.payloads.reponses.OrganisationPayload;
import com.enocholumide.repositories.OrganisationActivitiesRepository;
import com.enocholumide.repositories.OrganisationRepository;
import com.enocholumide.repositories.UserOrganisationRepository;
import com.enocholumide.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private UserOrganisationRepository userOrganisationRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrganisationActivitiesRepository organisationActivitiesRepository;

    @Override
    public ResponseEntity<List<Organisation>> getAllOrganisations() {
        return ResponseEntity.ok().body(organisationRepository.findAll());
    }

    @Override
    public ResponseEntity<List<UserOrganisations>> getUserOrganisations(Long id) {
        return ResponseEntity.ok().body(userOrganisationRepository.findAllByApplicationUser_Id(id));
    }

    @Override
    public ResponseEntity createOrganisation(Organisation organisation) {

        ResponseEntity validate = validateOrganisation(organisation);
        if(!validate.getStatusCode().equals(HttpStatus.OK))
            return validate;

        this.organisationRepository.save(organisation);
        return ResponseEntity.ok().body(this.organisationRepository.findByName(organisation.getName()));
    }

    @Override
    public ResponseEntity<OrganisationPayload> getUserOrganisationDetails(long organisationID, long userID) {

        Optional<UserOrganisations> userOrganisation = this.userOrganisationRepository.findByApplicationUserIdAndOrganisationId(organisationID, userID);
        if(!userOrganisation.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Organisation organisation = userOrganisation.get().getOrganisation();

        OrganisationPayload organisationPayload = new OrganisationPayload(organisation.getId(), organisation.getName()  );

        organisationPayload.setStaffs(usersRepository.findApplicationUsersByOrganisationsContainingAndRolesEquals(userOrganisation.get(), Role.STAFF));
        organisationPayload.setStudents(usersRepository.findApplicationUsersByOrganisationsContainingAndRolesEquals(userOrganisation.get(), Role.STUDENT));
        organisationPayload.setActivities(new ArrayList<>(organisation.getActivities()));
        organisationPayload.setTeacherActivities(organisationActivitiesRepository.findAllByOrganisationAndRoleEquals(organisation, Role.TEACHER));

        return ResponseEntity.ok().body(organisationPayload);
    }

    @Override
    public ResponseEntity<Organisation> editOrganisation(long organisationID) {
        return null;
    }

    @Override
    public void removeOrganisation() {

    }

    private ResponseEntity validateOrganisation(Organisation organisation){

        if(!(organisation.getName().length() >= 8))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("At least 8 characters are required for the organisation name.");

        if(this.organisationRepository.findByName(organisation.getName()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This name has been taking, consider using another name.");

        return ResponseEntity.ok().build();

    }
}
