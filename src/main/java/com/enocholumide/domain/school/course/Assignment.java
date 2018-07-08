package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Assignment extends DateAudit {

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Course course;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Instant deadline;

    @Type(type = "text")
    @Length(min = 6)
    @Column(nullable = false)
    private String title ="";

    @Type(type = "text")
    private String description ="";

    @Type(type = "text")
    private String notes ="";

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<CourseUploads> uploads = new HashSet<>();

    public Assignment(String type, Instant deadline, String title, String description, String notes) {
        this.type = type;
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.notes = notes;
    }

    @PrePersist
    @PreUpdate
    public void validate(){

        Instant deadline = getDeadline().truncatedTo(ChronoUnit.DAYS);
        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);

        if(deadline.compareTo(now) < 1 )
            throw new RuntimeException("Assignment deadline must be in the future");
    }
}
