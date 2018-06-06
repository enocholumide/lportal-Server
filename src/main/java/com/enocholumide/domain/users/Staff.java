package com.enocholumide.domain.users;

import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.shared.enumerated.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Staff extends ApplicationUser {

    @ManyToOne
    @JsonIgnoreProperties({"programsOffered"})
    private Department department;

    @Column(unique = true)
    private String staffID;

    @PrePersist
    @PreUpdate
    public void validate(){
        if(this.getRole().equals(Role.STAFF)){
            if(this.staffID.isEmpty()) {
                throw new RuntimeException("The staff number of the staff cannot be empty");
            }
        }
    }

    public Staff(String firstName, String lastName, String photoUrl, Department department, String staffID) {
        super(firstName, lastName, photoUrl);
        super.setRole(Role.STAFF);
        this.staffID = staffID;
        this.department = department;
    }

    @JsonIgnore
    public String getStaffID() {
        return staffID;
    }
}