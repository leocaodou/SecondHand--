package com.leodou.scandhand.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.leodou.scandhand.entity.aply;

public interface AplyRepository extends JpaRepository<aply,Integer>{
	
	@Transactional
	void deleteBystuid(int stuid);
}
