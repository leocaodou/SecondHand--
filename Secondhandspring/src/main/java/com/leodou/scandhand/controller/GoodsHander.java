package com.leodou.scandhand.controller;

import java.util.List;

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
import com.leodou.scandhand.entity.goods;
import com.leodou.scandhand.repository.GoodsRepository;

@RestController
@RequestMapping("/goods")
public class GoodsHander {
	@Autowired
	private GoodsRepository goodsRepository;
	
	@GetMapping("/findAll")
	public List<goods> findAll(){
		return goodsRepository.findAll();
	}
	
	@GetMapping("/findTop4")
	public List<goods> findTop4(){
		return goodsRepository.findTop4();
	}
	
	@GetMapping("/findAllWithout0")
	public List<goods> findAllWithout0(){
		return goodsRepository.findAllWithout0();
	}
	
	@GetMapping("/findById/{id}")
	public goods findById(@PathVariable("id") int goodsid){
		return goodsRepository.findById(goodsid).get();
	}
	
	@GetMapping("/findAll/{page}/{size}")
	public Page<goods> findAll(@PathVariable("page") int page,@PathVariable("size") int size){
		PageRequest pageRequest = PageRequest.of(page-1, size);
		return goodsRepository.findAll(pageRequest);
	}
	
	@GetMapping("/findAllWithout0/{page}/{size}")
	public Page<goods> findAllWithout0(@PathVariable("page") int page,@PathVariable("size") int size){
		PageRequest pageRequest = PageRequest.of(page-1, size);
		return goodsRepository.findAllWithout0(pageRequest);
	}
	
	@GetMapping("/findByNameWithout0/{name}/{page}/{size}")
	public Page<goods> findByNameWithout0(@PathVariable("name") String name,@PathVariable("page") int page,@PathVariable("size") int size){
		PageRequest pageRequest = PageRequest.of(page-1, size);
		return goodsRepository.findByNameWithout0(name,pageRequest);
	}
	
	@GetMapping("/findBySellerIdWithout0/{id}")
	public List<goods> findBySellerIdWithout0(@PathVariable("id") int id){
		return goodsRepository.findBySellerIdWithout0(id);
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody goods good) {
		Result res=new Result();
		goods result = goodsRepository.save(good);
		if(result != null) {
			res.setCode(1);
			res.setMessage("success");
			res.setData(result);
		}
			return res;
	}
	
	@DeleteMapping("/deleteById/{goodsid}")
	public void deleteById(@PathVariable("goodsid") int goodsid) {
		goodsRepository.deleteById(goodsid);
	}
	
	@DeleteMapping("/deleteByid/{id}")
	public void deleteByid(@PathVariable("id") int id){
		goodsRepository.deleteByid(id);
	}
	
}
