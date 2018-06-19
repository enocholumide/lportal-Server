package com.enocholumide.domain.organisation;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Status;
import com.enocholumide.domain.users.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserOrganisations extends DateAudit {

    @ManyToOne
    private Organisation organisation;

    @ManyToOne
    @JsonIgnore
    private ApplicationUser applicationUser;

    @Enumerated(EnumType.STRING)
    private Status status = Status.JOINED;

    public UserOrganisations(Organisation organisation, ApplicationUser applicationUser) {
        this.organisation = organisation;
        this.applicationUser = applicationUser;
    }
}
