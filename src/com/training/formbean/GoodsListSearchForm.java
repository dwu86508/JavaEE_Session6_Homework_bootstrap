package com.training.formbean;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class GoodsListSearchForm extends ActionForm {
	
	private String goodsID;
	private String goodsName;
	private int goodsPrice;	
	private String goodsImageName;
	private int lowestPrice;
	private int highestPrice;
	private String priceOrderby;
	private int goodsQuantity;
	private String status;
	private int page;	
	private FormFile goodsImage;
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsImageName() {
		return goodsImageName;
	}
	public void setGoodsImageName(String goodsImageName) {
		this.goodsImageName = goodsImageName;
	}
	public int getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(int lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public int getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(int highestPrice) {
		this.highestPrice = highestPrice;
	}
	public String getPriceOrderby() {
		return priceOrderby;
	}
	public void setPriceOrderby(String priceOrderby) {
		this.priceOrderby = priceOrderby;
	}
	public int getGoodsQuantity() {
		return goodsQuantity;
	}
	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public FormFile getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(FormFile goodsImage) {
		this.goodsImage = goodsImage;
	}
	@Override
	public String toString() {
		return "GoodsListSearchForm [goodsID=" + goodsID + ", goodsName=" + goodsName + ", goodsPrice=" + goodsPrice
				+ ", goodsImageName=" + goodsImageName + ", lowestPrice=" + lowestPrice + ", highestPrice="
				+ highestPrice + ", priceOrderby=" + priceOrderby + ", goodsQuantity=" + goodsQuantity + ", status="
				+ status + ", page=" + page + ", goodsImage=" + goodsImage + "]";
	}
	
	
	
}
