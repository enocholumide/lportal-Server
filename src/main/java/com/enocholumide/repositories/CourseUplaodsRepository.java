package com.enocholumide.repositories;

import com.enocholumide.domain.school.course.CourseUploads;
import com.enocholumide.domain.shared.enumerated.UploadType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseUplaodsRepository extends JpaRepository<CourseUploads, Long> {

    List<CourseUploads> findAllByAssignmentIdAndApplicationUserId(long assID, long userID);

    List<CourseUploads> findAllByAssignmentIdAndContentEquals(long id, UploadType uploadType);
}
