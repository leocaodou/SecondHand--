package com.leodou.scandhand.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class aply {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int stuid;
	private String banknumber;
}
