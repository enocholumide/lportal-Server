package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Assignment extends AbstractTimestampEntity {

    @NotNull
    @ManyToOne
    private Course course;

    @NotNull
    @Column(nullable = false)
    private String type;

    @NotNull
    @Column(nullable = false)
    private Date deadline;

    @Lob
    @NotNull
    @Length(min = 6)
    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Lob
    private String notes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<CourseUploads> courseUploads = new HashSet<>();

    public Assignment(Course course, String type, Date deadline, String title, String description, String notes) {
        this.course = course;
        this.type = type;
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.notes = notes;
    }
}
