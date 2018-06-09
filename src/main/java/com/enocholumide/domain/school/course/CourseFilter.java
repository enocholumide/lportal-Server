package com.enocholumide.domain.school.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseFilter {

    @JsonIgnoreProperties({"programs"})
    private List<Course> courses = new ArrayList<>();


    public CourseFilter(List<Course> courses) {
        this.courses = courses;
    }

    @JsonIgnoreProperties({"programs"})
    public List<Course> getCourses() {
        return courses;
    }
}
