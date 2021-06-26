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
public class comments {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int commentid;
	private String content;
	private int id;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;
	private int goodsid;
	private int sellerid;
	private int score;
}
