package com.leodou.scandhand.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leodou.scandhand.entity.goods;

public interface GoodsRepository extends JpaRepository<goods,Integer>{
	
	@Query(nativeQuery = true,value = "select * from goods where num > 0")
	List<goods> findAllWithout0();
	
	@Query(nativeQuery = true,value = "select * from goods where num > 0",countQuery = "select count(*) from goods where num != 0")
	Page<goods> findAllWithout0(PageRequest pageRequest);
	
	@Query(nativeQuery = true,value = "select * from goods where num > 0 and name like %?1%",countQuery = "select count(*) from goods where num != 0")
	Page<goods> findByNameWithout0(String name,PageRequest pageRequest);
	
	@Query(nativeQuery = true,value = "SELECT a.goodsid,NAME,id,price,num,des FROM cart a,goods WHERE a.goodsid = goods.goodsid GROUP BY a.goodsid ORDER BY count(buyerid) DESC LIMIT 4")
	List<goods> findTop4();
	
	@Query(nativeQuery = true,value = "select * from goods where num > 0 and id = ?1")
	List<goods> findBySellerIdWithout0(int id);

	@Transactional
	void deleteByid(int id);
}
