package com.ird.faa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.ird.faa.bean.Grade;


@Repository
public interface GradeDao extends JpaRepository<Grade,Long> {




    Grade findByReference(String reference);

    int deleteByReference(String reference);



}
