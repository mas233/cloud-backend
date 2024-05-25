package com.web.springboot.repository;

import com.web.springboot.entity.Resource;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    @Nullable
    List<Resource> findByCourseid(Integer courseid);
    Resource findByName(String name);

    Resource findById(int id);


//    @Transactional
//    @Modifying
//    @Query("update Course c set c.collectsId = c.collectsId||?1  WHERE c.Id=?2")
//    int AddCollectsId(int Id, Integer courseId);
    @Nullable
    List<Resource> findByNameLike(String name);
}
