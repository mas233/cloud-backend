package com.web.springboot.repository;

import com.web.springboot.entity.user;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<user, Integer> {
    @Nullable
    user findByUsername(String userName);

    List<user> findByUsernameLikeOrderByContributionDesc(String name);

    @Transactional
    @Modifying
    @Query("update user u set u.password = ?1 where u.username = ?2")
    int updatePasswordByUsername(String password, String username);

    @Transactional
    @Modifying
    @Query(value = "update user1 set collects_id = collects_id||?2 where id = ?1", nativeQuery = true)
    void userAddCollectCourse(int Id, Integer courseId);
    @Nullable
    user findById(int id);

    @Transactional
    @Modifying
    @Query(value = "update user1 set collects_id=array_remove(collects_id,?2) where id=?1", nativeQuery = true)
    void deleteCollectsId(int Id, Integer courseId);
}
