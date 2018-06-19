package com.enocholumide.domain.organisation;

import com.enocholumide.domain.school.School;
import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Organisation extends DateAudit {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Type(type="text")
    private String logoUrl;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<School> schools = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<OrganisationActivities> activities= new HashSet<>();

    public Organisation(@NotNull String name) {
        this.name = name;
    }

    @PrePersist
    public void addNews(){
        OrganisationActivities organisationActivities = new OrganisationActivities();
        organisationActivities.setOrganisation(this);
        organisationActivities.setEmail("enocholumide@gmail.com");
        organisationActivities.setTimeStamp(Instant.now());
        organisationActivities.setType(OrganisationActivityType.CREATED);
        organisationActivities.setDescription((this.getName()));
        organisationActivities.setUserIcon("https://media.licdn.com/dms/image/C5603AQHnQxbadUX5_Q/profile-displayphoto-shrink_100_100/0?e=1531958400&v=beta&t=rc8O0NrheHHILd9KciSZLsXl7uoDNCUtnnkucT6JnFY");
        organisationActivities.setTypeIcon("https://www.freeiconspng.com/uploads/review-icon-png-21.png");
        organisationActivities.setRole(Role.TEACHER);
        activities.add(organisationActivities);
    }
}
