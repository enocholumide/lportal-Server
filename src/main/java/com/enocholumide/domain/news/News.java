package com.enocholumide.domain.news;

import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.users.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class News extends DateAudit {

    @ManyToOne
    @JsonIgnoreProperties({"programsOffered"})
    private Department department;

    @ManyToOne
    private ApplicationUser applicationUser;

    private String title;

    @Type(type = "text")
    private String body;

    @Type(type = "text")
    private String photoUrl;

    @OneToMany( mappedBy = "news", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany
    private Set<ApplicationUser> likes = new HashSet<>();

    public News(Department department, ApplicationUser applicationUser, String title, String body, String photoUrl) {
        this.department = department;
        this.applicationUser = applicationUser;
        this.title = title;
        this.body = body;
        this.photoUrl = photoUrl;
    }
}
