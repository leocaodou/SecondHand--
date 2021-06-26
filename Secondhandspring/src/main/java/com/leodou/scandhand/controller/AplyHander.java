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

import com.leodou.scandhand.entity.aply;
import com.leodou.scandhand.repository.AplyRepository;

@RestController
@RequestMapping("/aply")
public class AplyHander {
	@Autowired
	private AplyRepository aplyRepository;
	
	@GetMapping("/findAll")
	public List<aply> findAll(){
		return aplyRepository.findAll();
	}
	
	@PostMapping("/save")
	public aply save(@RequestBody aply aply) {
		return aplyRepository.save(aply);
	}
	
	@DeleteMapping("/deleteBystuid/{stuid}")
	public void deleteBystuid(@PathVariable("stuid") int stuid){
		aplyRepository.deleteBystuid(stuid);
	}
}
