package com.web.springboot.repository;

import com.web.springboot.entity.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RemarkRepository extends JpaRepository<Remark, Integer> {
    List<Remark> findByCourseId(Integer courseId);
    Remark findById(int id);


}
