package com.enocholumide.payloads.reponses;

import com.enocholumide.domain.organisation.OrganisationActivities;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.domain.users.Staff;
import com.enocholumide.domain.users.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrganisationPayload {

    private long id;
    private String name;
    private List<ApplicationUser> students = new ArrayList<>();
    private List<ApplicationUser> staffs = new ArrayList<>();
    private List<OrganisationActivities> activities = new ArrayList<>();
    private List<OrganisationActivities> teacherActivities= new ArrayList<>();

    public OrganisationPayload(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
