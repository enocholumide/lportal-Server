package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.enocholumide.domain.users.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CourseUploads extends DateAudit {

    @ManyToOne
    @JsonIgnore
    private Course course;

    @JsonIgnore
    @ManyToOne
    private Assignment assignment;

    private String name;
    private String type;
    private String size;

    @Type(type = "text")
    private String url;

    @Enumerated(EnumType.STRING)
    private UploadType content;

    @ManyToOne
    @JsonIgnore
    private ApplicationUser applicationUser;
}
