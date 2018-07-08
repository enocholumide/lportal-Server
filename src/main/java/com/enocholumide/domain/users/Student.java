package com.enocholumide.domain.users;

import com.enocholumide.domain.school.Program;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.StudentCourse;
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
@JsonIgnoreProperties({"grades", "created", "updated", "program"})
public class Student extends ApplicationUser {

    @ManyToOne
    private Program program;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "studentenrolledcourses",
            joinColumns = @JoinColumn(name = "applicationuser_id"),
            inverseJoinColumns = @JoinColumn(name = "studentcourse_id"))
    private Set<StudentCourse> studentCourses= new HashSet<>();

    @PrePersist
    @PreUpdate
    public void validate(){
        if(this.getRole().equals(Role.STUDENT)){
            if(this.registrationID.isEmpty()) {
                throw new RuntimeException("The matric number of the student cannot be null");
            }
        }
    }
    public Student(String firstName, String lastName, String email, String photoUrl, Program program, String registrationID) {
        super(firstName, lastName, photoUrl, program.getDepartment());
        super.setEmail(email);
        this.setRole(Role.STUDENT);
        this.getRoles().add(Role.STUDENT);
        this.registrationID = registrationID;
        this.program = program;
    }


}