package com.leodou.scandhand.repository;


import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.leodou.scandhand.entity.comments;

public interface CommentsRepository extends JpaRepository<comments,Integer>{

	@Query(nativeQuery = true,value = "select * from comments where goodsid = ?1",countQuery = "select count(*) from comments where goodsid = ?1")
	Page<comments> findByGoodsid(int goodsid,PageRequest pageRequest);
	
	@Transactional
	void deleteByid(int id);
	
	@Transactional
	void deleteBysellerid(int sellerid);
}
