package com.nxtappz.nspace.repositories.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(String courseId);
}
