package com.web.springboot.repository;

import com.web.springboot.entity.Course;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Nullable
    List<Course> findByCoursenameLike(String coursename);

    Page<Course> findAllByOrderByViewtimesDesc(Pageable pageable);


    Page<Course> findAllByOrderByStarAverageDesc(Pageable pageable);

    @Query(value = "SELECT c.* FROM course c CROSS JOIN UNNEST(c.studytime) t ORDER BY t ASC, c.viewtimes DESC",
            countQuery = "SELECT COUNT(*) FROM course", nativeQuery = true)
    Page<Course> findAllSortedByStudytimeAndViewtimes(Pageable pageable);
    Course findByCoursename(String coursename);
    @Transactional
    @Modifying
    @Query(value = "update course set collects_id = collects_id||?1 where id = ?2", nativeQuery = true)
    void AddCollectsId(int Id, Integer courseId);

    @Transactional
    @Modifying
    @Query(value = "update course set collects_id=array_remove(collects_id,?1) where id=?2", nativeQuery = true)
    void DeleteCollectsId(int Id, Integer courseId);
    @Transactional
    @Modifying
    @Query(value = "update course set collects_num = collects_num - 1 where id=?1", nativeQuery = true)
    void AfterDeleteCollectsId(Integer courseId);

    Course findById(int id);
}
