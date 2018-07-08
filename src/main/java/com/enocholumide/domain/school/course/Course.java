package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.CourseNewsType;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends DateAudit {

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Program program;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String code;

    private int semester = 1;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Levels level;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Session session;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnoreProperties({"course"})
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturers")
    private Set<Staff> lecturers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "students")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Set<CourseUploads> uploads = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonIgnore
    private Set<CourseNews> news = new HashSet<>();

    public Course(Program program, @NotNull String name, @NotNull String code, int semester,Levels leveL, Session session) {
        this.program = program;
        this.name = name;
        this.code = code;
        this.semester = semester;
        this.level = program.getLevel();
        this.session = session;
    }

    public void validate(){
        if(this.getLecturers().size() < 1) {
            throw new RuntimeException("At least a lecturer must be attached to a course");
        }
    }

    public void addSchedule(Schedule schedule){
        schedule.setCourse(this);
        schedules.add(schedule);
    }

    public void addAssignment(Assignment assignment){
        assignment.setCourse(this);
        assignments.add(assignment);
    }

    public void addGrade(Grade grade){
        if(students.contains(grade.getStudent()))
            grade.getStudent().getGrades().put(this, grade);
    }

    public void addStudent(Student student) {

        students.add(student);
        student.getGrades().put(this, new Grade(this, student, this.session, 0));

    }

    public void makeActivity(String title, CourseNewsType courseNewsType) {

        CourseNews courseNews = new CourseNews();
        courseNews.setText(title);
        courseNews.setCourse(this);
        courseNews.setType(courseNewsType);
        this.getNews().add(courseNews);
    }
}
