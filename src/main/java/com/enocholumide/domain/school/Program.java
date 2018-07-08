package com.enocholumide.domain.school;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Levels;
import com.enocholumide.domain.users.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "programs")
public class Program extends DateAudit {

    @NotNull
    @ManyToOne
    private Department department;

    @NotNull
    private String name;

    @Lob
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Levels level;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program")
    private Set<Course> courses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program")
    private Set<Student> students = new HashSet<>();

    @NotNull
    private int semesters = 1;

    public Program(Department department, String name, Levels level, int semesters) {
        this.department = department;
        this.name = name;
        this.level = level;
        this.semesters = semesters;
    }
}
