package com.enocholumide.services.organisations;

import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisation;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.users.ApplicationUser;
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
    public ResponseEntity<List<UserOrganisation>> getUserOrganisations(Long id) {
        return ResponseEntity.ok().body(userOrganisationRepository.findAllByApplicationUser_Id(id));
    }

    @Override
    public ResponseEntity createOrganisation(Organisation organisation, long userID) {

        ResponseEntity validate = validateOrganisation(organisation);
        if(!validate.getStatusCode().equals(HttpStatus.OK))
            return validate;

        if(!usersRepository.existsById(userID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user does not exists");

        Optional<ApplicationUser> applicationUserOptional = this.usersRepository.findById(userID);
        ApplicationUser applicationUser = applicationUserOptional.get();

        UserOrganisation userOrganisation = new UserOrganisation(organisation, applicationUser);
        userOrganisation.getRoles().add(Role.ADMIN);
        userOrganisation.getRoles().add(Role.TEACHER);

        this.organisationRepository.save(organisation);
        applicationUser.getOrganisations().add(userOrganisation);

        this.usersRepository.save(applicationUser);

        return getUserOrganisations(applicationUser.getId());
    }

    @Override
    public ResponseEntity<?> getUserOrganisationDetails(long organisationID, long userID) {

        if(!this.userOrganisationRepository.existsByApplicationUserIdAndOrganisationId(userID, organisationID))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User or organisation does not exists");


        Optional<UserOrganisation> userOrganisation = this.userOrganisationRepository.findByApplicationUserIdAndOrganisationId(userID, organisationID);
        Organisation organisation = userOrganisation.get().getOrganisation();

        OrganisationPayload organisationPayload = new OrganisationPayload(organisation.getId(), organisation.getName()  );

        organisationPayload.setStaffs(usersRepository.findApplicationUsersByOrganisationsContainingAndRolesEquals(userOrganisation.get(), Role.STAFF));
        organisationPayload.setStudents(usersRepository.findApplicationUsersByOrganisationsContainingAndRolesEquals(userOrganisation.get(), Role.STUDENT));
        organisationPayload.setActivities(new ArrayList<>(organisation.getActivities()));
        organisationPayload.setTeacherActivities(organisationActivitiesRepository.findAllByOrganisationAndRoleEquals(organisation, Role.TEACHER));
        organisationPayload.setRoles(userOrganisation.get().getRoles());

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

        if(this.organisationRepository.existsByName(organisation.getName()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This name has been taking, consider using another name.");

        return ResponseEntity.ok().build();

    }
}
