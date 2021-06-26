package com.leodou.scandhand.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class student {
	@Id
	private int id;
	private String username;
	private String password;
	private String region;
	private String loc;
	private String tel;
	private boolean isbusiness = false;
	private int score;
}
