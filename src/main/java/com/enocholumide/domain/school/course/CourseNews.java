package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.CourseNewsType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CourseNews extends DateAudit {

    @ManyToOne
    @JsonIgnore
    private Course course;

    @Enumerated(EnumType.STRING)
    private CourseNewsType type;

    @Type(type = "text")
    private String link;

    private String text;
}

