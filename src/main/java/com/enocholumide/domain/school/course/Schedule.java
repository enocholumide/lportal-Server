package com.enocholumide.domain.school.course;

import com.enocholumide.domain.shared.DateAudit;
import com.enocholumide.domain.shared.enumerated.Period;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Schedule extends DateAudit {

    @ManyToOne
    private Course course;

    // @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView
    private Date startdate;

    //@Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView
    private Date enddate;

    @Enumerated(EnumType.STRING)
    private Period period = Period.WEEKLY;

    public Schedule(Date startdate, Date enddate, Period period) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.period = period;
    }
}
