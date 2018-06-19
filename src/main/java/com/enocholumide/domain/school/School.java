package com.enocholumide.domain.school;

import com.enocholumide.domain.organisation.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Organisation organisation;

    @NotNull
    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "school")
    private Set<Department> departments = new HashSet<>();


    public School(@NotNull Organisation organisation, String name) {
        this.organisation = organisation;
        this.name = name;
    }
}
