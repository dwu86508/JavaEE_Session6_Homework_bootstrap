package com.training.vo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.training.model.Goods;

public class BuyOrderRtn {
	
	private String customerID;	
	private int inputMoney;	
	private int buyAmount;	
	private int buyTotal;	
	private int orderChange;	
	private boolean buyYn;
	private List<ShoppingCartGoods> buyGoodsRtn;
	private  Map<BigDecimal,ShoppingCartGoods> goodsOrdersRtn ;
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public int getInputMoney() {
		return inputMoney;
	}
	public void setInputMoney(int inputMoney) {
		this.inputMoney = inputMoney;
	}
	public int getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
	public int getBuyTotal() {
		return buyTotal;
	}
	public void setBuyTotal(int buyTotal) {
		this.buyTotal = buyTotal;
	}
	public int getOrderChange() {
		return orderChange;
	}
	public void setOrderChange(int orderChange) {
		this.orderChange = orderChange;
	}
	public boolean isBuyYn() {
		return buyYn;
	}
	public void setBuyYn(boolean buyYn) {
		this.buyYn = buyYn;
	}
	public List<ShoppingCartGoods> getBuyGoodsRtn() {
		return buyGoodsRtn;
	}
	public void setBuyGoodsRtn(List<ShoppingCartGoods> buyGoodsRtn) {
		this.buyGoodsRtn = buyGoodsRtn;
	}
	public Map<BigDecimal, ShoppingCartGoods> getGoodsOrdersRtn() {
		return goodsOrdersRtn;
	}
	public void setGoodsOrdersRtn(Map<BigDecimal, ShoppingCartGoods> goodsOrdersRtn) {
		this.goodsOrdersRtn = goodsOrdersRtn;
	}
	@Override
	public String toString() {
		return "BuyOrderRtn [customerID=" + customerID + ", inputMoney=" + inputMoney + ", buyAmount=" + buyAmount
				+ ", buyTotal=" + buyTotal + ", orderChange=" + orderChange + ", buyYn=" + buyYn + ", buyGoodsRtn="
				+ buyGoodsRtn + ", goodsOrdersRtn=" + goodsOrdersRtn + "]";
	}
	
	
	
}
