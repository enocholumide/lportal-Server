package com.enocholumide.domain.news;

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
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"news"})
public class Comment extends DateAudit {

    @ManyToOne
    private News news;

    @ManyToOne
    private ApplicationUser applicationUser;

    @Type(type = "text")
    private String text;

    @Type(type = "text")
    private String extraImages = ""; // Separated with '&'

    @OneToMany
    private Set<ApplicationUser> likes = new HashSet<>();

}
