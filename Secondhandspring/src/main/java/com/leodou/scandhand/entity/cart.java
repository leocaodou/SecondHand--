package com.leodou.scandhand.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@Data
@IdClass(value = CartKey.class)
public class cart {
	@Id
	private int buyerid;
	@Id
	private int goodsid;
	private int sellerid;
	private int count;
}
