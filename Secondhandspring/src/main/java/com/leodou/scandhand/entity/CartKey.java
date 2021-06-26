package com.leodou.scandhand.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartKey implements Serializable{
	private int buyerid;

	private int goodsid;

}
