package com.leodou.scandhand.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class goods {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int goodsid;
	private String name;
	private int id;
	private double price;
	private int num;
	private String des;
}
