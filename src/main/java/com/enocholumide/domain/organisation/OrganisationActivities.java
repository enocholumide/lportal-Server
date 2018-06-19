package com.enocholumide.domain.organisation;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrganisationActivities extends DateAudit {

    @ManyToOne
    @JsonIgnore
    private Organisation organisation;

    @Enumerated(EnumType.STRING)
    private OrganisationActivityType type;

    private String userIcon;
    private String typeIcon;
    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email
    private String email;
    private Instant timeStamp;

    public OrganisationActivities(Organisation organisation, OrganisationActivityType type, @Email String email, Instant timeStamp) {
        this.organisation = organisation;
        this.type = type;
        this.email = email;
        this.timeStamp = timeStamp;
        this.setIcons();
    }

    private void setIcons(){

        OrganisationActivityType type = getType();

        switch (getType()) {
            case CREATED:  setTypeIcon("");
                break;
            default: setTypeIcon("https://www.freeiconspng.com/uploads/review-icon-png-21.png");
                break;
        }
    }


}
