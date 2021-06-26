package com.leodou.scandhand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leodou.scandhand.entity.Result;
import com.leodou.scandhand.entity.comments;
import com.leodou.scandhand.repository.CommentsRepository;

@RestController
@RequestMapping("/comments")
public class CommentsHandler {
	@Autowired
	private CommentsRepository commentsRepository;
	
	@GetMapping("/findByGoodsid/{goodsid}/{page}/{size}")
	public Page<comments> findBuyerCart(@PathVariable("goodsid") int goodsid,@PathVariable("page") int page,@PathVariable("size") int size){
		PageRequest pageRequest = PageRequest.of(page-1, size);
		return commentsRepository.findByGoodsid(goodsid,pageRequest);
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody comments comment) {
		Result res = new Result();
		comments result = commentsRepository.save(comment);
		if(result != null) {
			res.setCode(1);
			res.setMessage("success");
		}
			return res;
	}
	
	@DeleteMapping("/deleteByid/{id}")
	public void deleteByid(@PathVariable("id") int id){
		commentsRepository.deleteByid(id);
	}
	
	@DeleteMapping("/deleteBysellerid/{sellerid}")
	public void deleteBysellerid(@PathVariable("sellerid") int sellerid){
		commentsRepository.deleteBysellerid(sellerid);
	}
	
}
