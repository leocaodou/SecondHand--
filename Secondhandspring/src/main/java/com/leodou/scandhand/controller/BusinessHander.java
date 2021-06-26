package com.leodou.scandhand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leodou.scandhand.entity.Result;
import com.leodou.scandhand.entity.business;
import com.leodou.scandhand.repository.BusinessRepository;


@RestController
@RequestMapping("/business")
public class BusinessHander {
	@Autowired
	private BusinessRepository businessRepository;
	
	@GetMapping("/findBuyerBusiness/{buyerid}")
	public List<business> findBuyerBusiness(@PathVariable("buyerid") int buyerid){
		return businessRepository.findBuyerBusiness(buyerid);
	}
	
	@GetMapping("/findSellerBusiness/{sellerid}")
	public List<business> findSellerBusiness(@PathVariable("sellerid") int sellerid){
		return businessRepository.findSellerBusiness(sellerid);
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody business bus) {
		Result res=new Result();
		business result = businessRepository.save(bus);
		if(result != null) {
			res.setCode(1);
			res.setMessage("success");
		}
			return res;
	}
	
	@GetMapping("/getScore/{buyerid}")
	public int getScore(@PathVariable("buyerid") int buyerid){
		return (int)businessRepository.getScore(buyerid);
	}
	
	@GetMapping("/getEarn/{sellerid}")
	public Double getEarn(@PathVariable("sellerid") int sellerid){
		System.out.println(businessRepository.getEarn(sellerid));
		return businessRepository.getEarn(sellerid);
	}
	
	@DeleteMapping("/deleteBysellerid/{sellerid}")
	public void deleteBysellerid(@PathVariable("sellerid") int sellerid){
		businessRepository.deleteBysellerid(sellerid);
	}
	
	@DeleteMapping("/deleteBybuyerid/{buyerid}")
	public void deleteBybuyerid(@PathVariable("buyerid") int buyerid){
		businessRepository.deleteBybuyerid(buyerid);
	}
}
