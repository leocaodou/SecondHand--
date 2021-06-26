package com.leodou.scandhand.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leodou.scandhand.entity.Result;
import com.leodou.scandhand.entity.student;
import com.leodou.scandhand.repository.StudentsRepository;
import com.leodou.scandhand.tool.MD5Utils;



@RestController
@RequestMapping("/student")
public class StudentsHandler {
	@Autowired
	private StudentsRepository studentRepository;
	
	@GetMapping("/findAll")
	public List<student> findAll(){
		return studentRepository.findAll();
	}
	
	@GetMapping("/findById/{id}")
	public student findById(@PathVariable("id")int id){
		Optional<student> res = studentRepository.findById(id);
		if(res.equals(Optional.empty()))
			return null;
		else
			return res.get();
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody student stu) {
		stu.setPassword(MD5Utils.md5(stu.getPassword()));
		Result res=new Result();
		if(findById(stu.getId()) != null) {
			return res;
		}
		student result = studentRepository.save(stu);
		if(result != null) {
			res.setCode(1);
			res.setMessage("success");
		}
			return res;
	}
	
	@PutMapping("/update")
	public String update(@RequestBody student stu) {
		student result = studentRepository.save(stu);
		if(result != null) {
			return "success";
		}
		else
			return "failed";
	}
	
	@DeleteMapping("/deleteByid/{id}")
	public void deleteByid(@PathVariable("id") int id){
		studentRepository.deleteByid(id);
	}
	
}
