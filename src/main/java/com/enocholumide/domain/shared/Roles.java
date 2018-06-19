package com.enocholumide.domain.shared;

import com.enocholumide.domain.shared.enumerated.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private Role name;

    public Roles(Role name) {
        this.name = name;
    }
}
