package com.training.vo;

import java.math.BigDecimal;
import java.util.Set;

import com.training.model.Goods;

public class GoodsListSearch {
	private BigDecimal goodsID;
	private String goodsName;
	private int lowestPrice;
	private int highestPrice;
	private String priceOrderby;
	private int goodsQuantity;
	private String status;
	private int page;
	private int lastPage;	
	private int pagePage;
	private int listTotal;
	private Set<Goods> goodsList;
	public BigDecimal getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(BigDecimal goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getPagePage() {
		return pagePage;
	}
	public void setPagePage(int pagePage) {
		this.pagePage = pagePage;
	}
	public int getListTotal() {
		return listTotal;
	}
	public void setListTotal(int listTotal) {
		this.listTotal = listTotal;
	}
	public Set<Goods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(Set<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	@Override
	public String toString() {
		return "GoodsListSearch [goodsID=" + goodsID + ", goodsName=" + goodsName + ", lowestPrice=" + lowestPrice
				+ ", highestPrice=" + highestPrice + ", priceOrderby=" + priceOrderby + ", goodsQuantity="
				+ goodsQuantity + ", status=" + status + ", page=" + page + ", lastPage=" + lastPage + ", pagePage="
				+ pagePage + ", listTotal=" + listTotal + ", goodsList=" + goodsList + "]";
	}
	
	
}
