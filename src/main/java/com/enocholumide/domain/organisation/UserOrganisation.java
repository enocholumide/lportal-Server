package com.enocholumide.domain.organisation;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.Roles;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.domain.shared.enumerated.Status;
import com.enocholumide.domain.users.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "userorganisations")
public class UserOrganisation extends DateAudit {

    @ManyToOne
    private Organisation organisation;

    @ManyToOne
    @JsonIgnore
    private ApplicationUser applicationUser;

    @Enumerated(EnumType.STRING)
    private Status status = Status.JOINED;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "userorganisations_roles",
            joinColumns = {@JoinColumn(name = "userorganisation_id")})
    @Enumerated(EnumType.STRING)
    @Column(name = "role_id")
    private Set<Role> roles = new HashSet<>();

    public UserOrganisation(Organisation organisation, ApplicationUser applicationUser) {
        this.organisation = organisation;
        this.applicationUser = applicationUser;
    }
}
