package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.UploadType;
import com.enocholumide.domain.users.ApplicationUser;
import com.enocholumide.domain.users.Staff;
import com.enocholumide.domain.users.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoursesRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM CourseUploads c WHERE c.course = :course AND c.content = :content")
    List<CourseUploads> getCourseContent (@Param("course") Course course, @Param("content") UploadType uploadType);

    @Query("SELECT c FROM CourseUploads c WHERE c.course = :course AND c.applicationUser = :applicationUser AND c.assignment = :assignment AND c.content = :content")
    List<CourseUploads> findUserUploads (@Param("course") Course course, @Param("applicationUser") ApplicationUser user, @Param("assignment")Assignment assignment, @Param("content") UploadType uploadType);

    @Query("SELECT c FROM CourseUploads c WHERE c.course = :course AND c.applicationUser = :applicationUser AND c.assignment = :assignment")
    List<CourseUploads> findByCourseAndUser(@Param("course") Course course, @Param("applicationUser") ApplicationUser user, @Param("assignment")Assignment assignment);

    List<Course> findCoursesByLecturersContaining(Staff staff);

}
