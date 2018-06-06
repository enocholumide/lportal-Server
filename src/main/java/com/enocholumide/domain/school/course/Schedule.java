package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.enumerated.Period;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Schedule {

    // @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView
    private Date start;

    //@Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView
    private Date end;

    @Enumerated(EnumType.STRING)
    private Period period = Period.WEEKLY;

    public Schedule(Date start, Date end, Period period) {
        this.start = start;
        this.end = end;
        this.period = period;
    }
}
