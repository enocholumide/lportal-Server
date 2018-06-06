package com.enocholumide.domain.news;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.users.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Comment extends AbstractTimestampEntity {

    @ManyToOne
    private News news;

    @ManyToOne
    private ApplicationUser applicationUser;

    @Type(type = "text")
    private String text;

    @OneToMany
    private Set<ApplicationUser> likes = new HashSet<>();

}
