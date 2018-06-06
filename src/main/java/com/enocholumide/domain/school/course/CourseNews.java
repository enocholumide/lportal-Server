package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.CourseNewsType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CourseNews extends AbstractTimestampEntity {

    @ManyToOne
    @JsonIgnore
    private Course course;

    @Enumerated(EnumType.STRING)
    private CourseNewsType type;

    @Lob
    private String link;

    private String text;
}

