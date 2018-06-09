package com.enocholumide.domain.users;

import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.grade.Grade;
import com.enocholumide.domain.shared.enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"grades", "created", "updated"})
public class Student extends ApplicationUser {

    @ManyToOne
    @JsonIgnoreProperties({"programsOffered", "school"})
    private Department department;

    @Column(unique = true)
    private String registrationID;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="StudentGrades",
            joinColumns={@JoinColumn(name="fk_student", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="fk_group", referencedColumnName="id")})
    @MapKey(name = "id")
    private Map<Course, Grade> grades = new HashMap<Course, Grade>();

    @PrePersist
    @PreUpdate
    public void validate(){
        if(this.getRole().equals(Role.STUDENT)){
            if(this.registrationID.isEmpty()) {
                throw new RuntimeException("The matric number of the student cannot be null");
            }
        }
    }
    public Student(String firstName, String lastName, String photoUrl, Department department, String registrationID) {
        super(firstName, lastName, photoUrl);
        super.setRole(Role.STUDENT);
        this.registrationID = registrationID;
        this.department = department;
    }
}