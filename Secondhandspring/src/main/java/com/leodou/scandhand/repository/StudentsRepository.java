package com.leodou.scandhand.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leodou.scandhand.entity.student;


public interface StudentsRepository extends JpaRepository<student,Integer>{
	
	@Query(nativeQuery = true,value = "select * from student where id = ?1 and password = ?2")
	student findUserByIdAndPassword(int id, String password);
	
	@Transactional
	void deleteByid(int id);
}
