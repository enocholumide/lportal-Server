package com.enocholumide.domain.school.grade;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.GradeLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.enocholumide.domain.users.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NotNull
@JsonIgnoreProperties(value = {"created", "updated"})
public class Grade extends AbstractTimestampEntity {

    //@ManyToOne
    //@JsonIgnoreProperties(value = {"schedules", "program", "lecturers", "created", "updated", "semester", "level"})
    //private Course course;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Student student;

    private Date startdate;
    private Date enddate;

    @Enumerated(EnumType.STRING)
    private GradeLevel gradeLevel;

    private double score;

    public Grade(Course course, Student student, Date startdate, Date enddate, double score) {
        //this.course = course;
        //this.student = student;
        this.startdate = startdate;
        this.enddate = enddate;
        this.gradeLevel = GradeDetails.getGradeFromScore(score);
        this.score = score;
    }
}
