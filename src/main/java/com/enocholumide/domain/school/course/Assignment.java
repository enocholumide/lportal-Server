package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
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

    @ManyToOne
    @JsonIgnore
    private Course course;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Date deadline;

    @Type(type = "text")
    @Length(min = 6)
    @Column(nullable = false)
    private String title;

    @Type(type = "text")
    private String description;

    @Type(type = "text")
    private String notes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<CourseUploads> uploads = new HashSet<>();

    public Assignment(String type, Date deadline, String title, String description, String notes) {
        this.type = type;
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.notes = notes;
    }
}
