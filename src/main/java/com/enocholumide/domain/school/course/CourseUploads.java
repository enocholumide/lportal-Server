package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.enocholumide.domain.users.ApplicationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CourseUploads extends AbstractTimestampEntity {

    @ManyToOne
    @JsonIgnore
    private Course course;

    @JsonIgnore
    @ManyToOne
    private Assignment assignment;

    private String name;
    private String type;
    private String size;
    @Lob
    private String url;

    @Enumerated(EnumType.STRING)
    private UploadType content;

    @ManyToOne
    @JsonIgnore
    private ApplicationUser applicationUser;
}
