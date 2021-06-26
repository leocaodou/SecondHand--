package com.leodou.scandhand.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leodou.scandhand.entity.CartKey;
import com.leodou.scandhand.entity.cart;

public interface CartRepository extends JpaRepository<cart,CartKey>{

	@Query(nativeQuery = true,value = "select * from cart where buyerid = ?1")
	List<cart> findBuyerCart(int buyerid);
	
	@Query(nativeQuery = true,value = "select count(*) from cart where buyerid = ?1")
	int getCartNum(int buyerid);
	
	@Transactional
	void deleteBysellerid(int sellerid);
	
	@Transactional
	void deleteBybuyerid(int buyerid);
}
