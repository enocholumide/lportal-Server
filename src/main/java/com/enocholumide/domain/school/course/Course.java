package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.Levels;
import com.enocholumide.domain.shared.enumerated.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.enocholumide.domain.school.Program;
import com.enocholumide.domain.school.grade.Grade;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

import com.enocholumide.domain.users.Staff;
import com.enocholumide.domain.users.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Course extends AbstractTimestampEntity {

    @NotNull
    private String name;

    @NotNull
    private String code;

    private int semester = 1;

    @Enumerated(EnumType.STRING)
    private Levels level;

    @Enumerated(EnumType.STRING)
    private Session session;

    //@ElementCollection(fetch = FetchType.LAZY)
    //@CollectionTable(name = "course_schedules", joinColumns = @JoinColumn(name = "course_id"))
    //private Set<Schedule> schedules = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturers")
    private Set<Staff> lecturers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "students")
    private Set<Student> students = new HashSet<>();

    @ManyToMany()
    @JoinColumn(name = "programs")
    private Set<Program> programs = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Set<CourseUploads> courseUploads = new HashSet<>();

    public Course(@NotNull String name, @NotNull String code, int semester,Levels leveL, Session session) {
        this.name = name;
        this.code = code;
        this.semester = semester;
        this.level = level;
        this.session = session;
    }

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        this.validate();
    }
    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
        this.validate();
    }

    public void validate(){
        if(this.getLecturers().size() < 1) {
            throw new RuntimeException("At least a lecturer must be attached to a course");
        }
    }
}
