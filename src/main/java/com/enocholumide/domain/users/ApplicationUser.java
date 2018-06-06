package com.enocholumide.domain.users;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ApplicationUser extends AbstractTimestampEntity {

    private String firstName;
    private String lastName;

    @Type(type = "text")
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    public ApplicationUser(String firstName, String lastName, String photoUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
    }

}
