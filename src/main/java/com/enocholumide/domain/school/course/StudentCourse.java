package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Status;
import com.enocholumide.domain.users.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter @Setter @Entity @NoArgsConstructor
public class StudentCourse extends DateAudit {

    @ManyToOne
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties({"studentCourses", "role", "roles", "organisations", "status", "registrationID"})
    private Student student;

    private Status status = Status.PENDING;

    public StudentCourse(Course course, Student student, Status status) {
        this.course = course;
        this.student = student;
        this.status = status;
    }
}
