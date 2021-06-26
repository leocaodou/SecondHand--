package com.leodou.scandhand.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class business {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int businessid;
	private int sellerid;
	private int buyerid;
	private int goodsid;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	private int count;
	private int iscomment;
}
