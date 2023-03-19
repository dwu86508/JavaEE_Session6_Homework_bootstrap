package com.training.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.training.dao.FrontEndDao;
import com.training.model.Goods;
import com.training.vo.BuyOrderRtn;
import com.training.vo.GoodsPage;
import com.training.vo.ShoppingCartGoods;

public class FrontEndService {

	private static FrontEndService frontEndService = new FrontEndService();

	private FrontEndService(){ }
	
	private FrontEndDao frontEndDao = FrontEndDao.getInstance();
	
	public static FrontEndService getInstance(){
		return frontEndService;
	}	
	
	public GoodsPage searchGoodsPage(String searchKeyword,String page) {
		GoodsPage goodsPage = new GoodsPage();
		int pageNo = page== null ? 1 : Integer.parseInt(page);  
		int endRowNo = pageNo*6;
		int startRowNO = endRowNo-5;
		if(searchKeyword == null) {
			searchKeyword="";
		}
		int pagePage = pageNo/3==0 ? 1 : (int)Math.ceil((double)pageNo/3);
		goodsPage.setPage(pageNo);
		goodsPage.setPagePage(pagePage);
		goodsPage.setSearchKeyWord(searchKeyword);
		Set<Goods> goodsList = frontEndDao.searchGoods(searchKeyword, startRowNO, endRowNo);
		goodsPage.setGoodsList(goodsList);
		int goodsAmount = frontEndDao.goodsAmount(searchKeyword);
		int lastPage = goodsAmount%6==0 ? goodsAmount/6 : (goodsAmount/6)+1;
		lastPage = goodsAmount<=6 ? 0 : lastPage;
		goodsPage.setLastPage(lastPage);
		return goodsPage;
	}
	
	public Set<Goods> searchGoods(String searchKeyword, int pageNo) {
		int endRowNo = pageNo*6;
		int startRowNO = endRowNo-5;
		Set<Goods> goodsList = frontEndDao.searchGoods(searchKeyword,startRowNO, endRowNo);		
		return goodsList;
	}	
	

	public BuyOrderRtn buyGoods(BuyOrderRtn buyOrderRtn) {
		
		boolean buyYn = false;
		Set<BigDecimal> goodsInputQuantitySet = new  LinkedHashSet<>();
		//使用者購物車
		Map<BigDecimal, ShoppingCartGoods> shoppingCar = buyOrderRtn.getGoodsOrdersRtn();
		//確認後購物車
		Map<BigDecimal, ShoppingCartGoods> shoppingCarBuy = new LinkedHashMap<>();
		//計算總金額
		int buyAmount = shoppingCar.values().stream().mapToInt(g -> g.getGoodsPrice()*g.getBuyQuantity()).sum();
		goodsInputQuantitySet = shoppingCar.keySet();
		//取得資料庫內商品資訊
		Map<BigDecimal, Goods> buyGoodsInf = frontEndDao.queryBuyGoods(goodsInputQuantitySet);
		
		if(buyOrderRtn.getInputMoney()>=buyAmount) {
			buyOrderRtn.setBuyAmount(buyAmount);
			
			shoppingCar.forEach((key,goods) -> {
				//計算商品總價
				int goodBuyTotal = goods.getGoodsPrice()*goods.getBuyQuantity();
				//計算商品庫存是否大於等於購買數量
				int goodsQuantity = buyGoodsInf.get(key).getGoodsQuantity()-goods.getBuyQuantity();
				if(goodsQuantity>=0) {					
					buyGoodsInf.get(key).setGoodsQuantity(goodsQuantity);
					//存入正確購資訊	
					shoppingCarBuy.put(key, goods);
				//如果商品庫存小於購買數量	
				}else {
					//如果庫存數量大於零
					if(buyGoodsInf.get(key).getGoodsQuantity()>0) {					
						//存入正確購資訊
						goods.setBuyQuantity(buyGoodsInf.get(key).getGoodsQuantity());
						shoppingCarBuy.put(key, goods);
						//更新新庫存
						buyGoodsInf.get(key).setGoodsQuantity(0);
					}
				}				
			});			
			//計算總金額
			int buyTotal = shoppingCarBuy.values().stream().mapToInt(g -> g.getGoodsPrice()*g.getBuyQuantity()).sum();
			//建立訂單
			frontEndDao.batchCreateGoodsOrder(buyOrderRtn.getCustomerID(), shoppingCarBuy);
			//更新資料庫庫存
			frontEndDao.batchUpdateGoodsQuantity(buyGoodsInf.values().stream().collect(Collectors.toSet()));
			buyOrderRtn.setBuyTotal(buyTotal);
			buyOrderRtn.setOrderChange(buyOrderRtn.getInputMoney()-buyTotal);
			buyYn = true;
		}else {
			buyOrderRtn.setBuyTotal(buyOrderRtn.getBuyAmount());
			buyOrderRtn.setOrderChange(buyOrderRtn.getInputMoney());			
		}				
		buyOrderRtn.setGoodsOrdersRtn(shoppingCarBuy);
		buyOrderRtn.setBuyYn(buyYn);		
		return buyOrderRtn;		
	}
		

		
}


