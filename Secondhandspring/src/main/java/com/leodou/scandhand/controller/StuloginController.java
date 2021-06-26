package com.leodou.scandhand.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leodou.scandhand.entity.Admin;
import com.leodou.scandhand.entity.Result;
import com.leodou.scandhand.entity.Stulogin;
import com.leodou.scandhand.repository.AdminRepository;
import com.leodou.scandhand.repository.StudentsRepository;
import com.leodou.scandhand.tool.MD5Utils;

@RequestMapping("/login")
@RestController
public class StuloginController {
	@Autowired
	private StudentsRepository studentRepository;

	@Autowired
	private AdminRepository adminRepository;
	
	@PostMapping("/findByUsernameAndPassword")
	public Result findByUsernameAndPassword(@RequestBody Stulogin login){
		Result result = new Result();
		String pas = MD5Utils.md5(login.getPassword());
		if(studentRepository.findUserByIdAndPassword(login.getId(),pas) != null) {
			result.setData(studentRepository.findUserByIdAndPassword(login.getId(),pas));
			result.setMessage("success");
			result.setCode(1);
		}
		return result;
	}
	
	@PostMapping("/findAdmin")
	public Result findAdmin(@RequestBody Admin admin){
		Result result = new Result();
		System.out.println(admin.getId());
		Optional<Admin> ad = adminRepository.findById(admin.getId());
		if(!ad.equals(Optional.empty())) {
			Admin res = ad.get();
			if(res.getPassword().equals(admin.getPassword())) {
				result.setData(res);
				result.setMessage("success");
				result.setCode(1);
			}
		}
		return result;
	}
}
