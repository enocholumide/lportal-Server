package com.enocholumide.domain.school;

import com.enocholumide.domain.news.News;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.enocholumide.domain.users.Staff;
import com.enocholumide.domain.users.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"programs", "news", "staffs", "students", "school"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties("departments")
    private School school;

    @OneToMany( mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Program> programs = new HashSet<>();

    @OneToMany( mappedBy = "department", cascade = CascadeType.ALL)
    private Set<News> news = new HashSet<>();

    @Transient
    private Set<String> programsOffered = new HashSet<>(); // need to find a better way to do this

    public Department(String name, School school) {
        this.name = name;
        this.school = school;
    }
}
