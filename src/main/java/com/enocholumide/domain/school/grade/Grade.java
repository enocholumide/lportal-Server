package com.enocholumide.domain.school.grade;

import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.GradeLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JsonIgnoreProperties(value = {"schedules", "programs", "lecturers", "created", "updated", "semester", "level"})
    private Course course;

    @ManyToOne
    @JsonIgnore
    private Student student;

    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private GradeLevel gradeLevel;

    private double score;

    public Grade(Course course, Student student, Date startDate, Date endDate, double score) {
        this.course = course;
        this.student = student;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gradeLevel = GradeDetails.getGradeFromScore(score);
        this.score = score;
    }
}
