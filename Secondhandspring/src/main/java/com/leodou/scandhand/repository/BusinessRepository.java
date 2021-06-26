package com.leodou.scandhand.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leodou.scandhand.entity.business;

public interface BusinessRepository extends JpaRepository<business,Integer>{
	@Query(nativeQuery = true,value = "select * from business where buyerid = ?1")
	List<business> findBuyerBusiness(int buyerid);

	@Query(nativeQuery = true,value = "select * from business where sellerid = ?1")
	List<business> findSellerBusiness(int sellerid);
	
	@Query(nativeQuery = true,value = "SELECT SUM(b.price * a.count ) FROM business a, goods b WHERE a.goodsid=b.goodsid and buyerid = ?1")
	double getScore(int buyerid);
	
	@Query(nativeQuery = true,value = "SELECT SUM(b.price * a.count ) FROM business a, goods b WHERE a.goodsid=b.goodsid and sellerid = ?1")
	Double getEarn(int sellerid);
	
	@Transactional
	void deleteBysellerid(int sellerid);
	
	@Transactional
	void deleteBybuyerid(int buyerid);
}
