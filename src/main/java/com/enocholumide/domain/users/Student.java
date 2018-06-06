package com.enocholumide.domain.users;

import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.school.grade.Grade;
import com.enocholumide.domain.shared.enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"grades", "matricNo", "created", "updated"})
public class Student extends ApplicationUser {

    @ManyToOne
    @JsonIgnoreProperties({"programsOffered", "school"})
    private Department department;

    @Column(unique = true)
    private String matricNo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Grade> grades = new HashSet<>();

    @PrePersist
    @PreUpdate
    public void validate(){
        if(this.getRole().equals(Role.STUDENT)){
            if(this.matricNo.isEmpty()) {
                throw new RuntimeException("The matric number of the student cannot be null");
            }
        }
    }
    public Student(String firstName, String lastName, String photoUrl, Department department, String matricNo) {
        super(firstName, lastName, photoUrl);
        super.setRole(Role.STUDENT);
        this.matricNo = matricNo;
        this.department = department;
    }
}