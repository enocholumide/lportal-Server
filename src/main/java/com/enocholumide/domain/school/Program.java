package com.enocholumide.domain.school;

import com.enocholumide.domain.shared.AbstractTimestampEntity;
import com.enocholumide.domain.shared.enumerated.Levels;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Program extends AbstractTimestampEntity {

    @NotNull
    @ManyToOne
    private Department department;

    @NotNull
    private String name;

    @Lob
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Levels level;

    @NotNull
    private int semesters = 1;

    public Program(Department department, String name, Levels level, int semesters) {
        this.department = department;
        this.name = name;
        this.level = level;
        this.semesters = semesters;
    }
}
