package com.leodou.scandhand.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leodou.scandhand.entity.CartKey;
import com.leodou.scandhand.entity.Result;
import com.leodou.scandhand.entity.cart;
import com.leodou.scandhand.repository.CartRepository;
@RestController
@RequestMapping("/cart")
public class CartHander {
	@Autowired
	private CartRepository cartRepository;
	
	@GetMapping("/findBuyerCart/{buyerid}")
	public List<cart> findBuyerCart(@PathVariable("buyerid") int buyerid){
		return cartRepository.findBuyerCart(buyerid);
	}
	
	@GetMapping("/findById/{buyerid}/{goodsid}")
	public Result findById(@PathVariable("buyerid") int buyerid,@PathVariable("goodsid") int goodsid){
		CartKey cartkey = new CartKey(buyerid,goodsid);
		Result result = new Result();
		Optional<cart> res =  cartRepository.findById(cartkey);
		if(res.equals(Optional.empty()))
			return result;
		else {
			result.setCode(1);
			result.setData(res.get());
			result.setMessage("success");
			return result;
		}
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody cart usercart) {
		Result res = new Result();
		cart result = cartRepository.save(usercart);
		if(result != null) {
			res.setCode(1);
			res.setMessage("success");
		}
			return res;
	}
	
	@GetMapping("/count/{buyerid}")
	public int getCartNum(@PathVariable("buyerid") int buyerid) {
		return cartRepository.getCartNum(buyerid);
	}
	
	@DeleteMapping("/deleteById/{buyerid}/{goodsid}")
	public void deleteById(@PathVariable("buyerid") int buyerid,@PathVariable("goodsid") int goodsid) {
		CartKey cartkey = new CartKey(buyerid,goodsid);
		cartRepository.deleteById(cartkey);
	}
	
	@DeleteMapping("/deleteBysellerid/{sellerid}")
	public void deleteBysellerid(@PathVariable("sellerid") int sellerid){
		cartRepository.deleteBysellerid(sellerid);
	}
	
	@DeleteMapping("/deleteBybuyerid/{buyerid}")
	public void deleteBybuyerid(@PathVariable("buyerid") int buyerid){
		cartRepository.deleteBybuyerid(buyerid);
	}
}
