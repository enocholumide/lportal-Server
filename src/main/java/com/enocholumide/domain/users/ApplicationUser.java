package com.enocholumide.domain.users;

import com.enocholumide.domain.organisation.UserOrganisation;
import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Role;
import com.enocholumide.payloads.requests.SignUpRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUser extends DateAudit {

    @ManyToOne
    @JsonIgnoreProperties({"programsOffered"})
    private Department department;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "applicationuser_organisations",
            joinColumns = @JoinColumn(name = "applicationuser_id"),
            inverseJoinColumns = @JoinColumn(name = "userorganisations_id"))
    private Set<UserOrganisation> organisations= new HashSet<>();

    @NaturalId @NotBlank @Size(max = 40)
    @Email
    private String email;

    @Type(type = "text")
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "applicationuser_roles",
            joinColumns = @JoinColumn(name = "applicationuser_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
    */

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "applicationuser_roles",
            joinColumns = @JoinColumn(name = "applicationuser_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_id")
    private Set<Role> roles = new HashSet<>();

    public ApplicationUser(String firstName, String lastName, String photoUrl, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.department = department;
    }

    public ApplicationUser(SignUpRequest signUpRequest){
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.email = signUpRequest.getEmail();
        this.password = signUpRequest.getPassword();
        this.role = signUpRequest.getRole();
        this.roles.add(Role.STUDENT);
    }
}
