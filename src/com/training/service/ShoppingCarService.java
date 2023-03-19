package com.training.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.dao.FrontEndDao;
import com.training.dao.ShoppingCarDao;
import com.training.model.Goods;
import com.training.vo.ShoppingCartGoods;
import com.training.vo.ShoppingCartGoodsInfo;

public class ShoppingCarService {
	
	private static ShoppingCarService shoppingCarService = new ShoppingCarService();

	private ShoppingCarService(){ }
	
	private ShoppingCarDao shoppingCarDao = ShoppingCarDao.getInstance();
	
	private FrontEndDao frontEndDao = FrontEndDao.getInstance();
	
	public static ShoppingCarService getInstance(){
		return shoppingCarService;
	}
	
	
	public  ShoppingCartGoods cartGoodsInf(String goodsID) {
		 ShoppingCartGoods good = shoppingCarDao.queryBuyGoods(goodsID);		
		return good;
	}
	
	//加入購物車
	public Map<BigDecimal,ShoppingCartGoods> addCartGoods(Map<BigDecimal,ShoppingCartGoods> shoppingCar, ShoppingCartGoods good) {
		Map<BigDecimal,ShoppingCartGoods> shoppingCarNew = new LinkedHashMap<>();
		if(shoppingCar==null) {
			//將商品加入購物車			
			shoppingCarNew.put(good.getGoodsID(), good);								
			//將購物車存入session			
		}else {			
			if(shoppingCar.containsKey(good.getGoodsID())) {
				good.setBuyQuantity(shoppingCar.get(good.getGoodsID()).getBuyQuantity()+good.getBuyQuantity());				
			}
			shoppingCar.put(good.getGoodsID(), good);
			shoppingCarNew.putAll(shoppingCar); 
		}
		return shoppingCarNew;
	}
	
	
	
	public List<ShoppingCartGoods> cartGoodsRtn(Map<BigDecimal,ShoppingCartGoods> shoppingCar) {
		List<ShoppingCartGoods> shoppingCartGoodsLt = new ArrayList<>();		
		shoppingCar.forEach((key,goods) -> {
			ShoppingCartGoods shoppingCartGoods = new ShoppingCartGoods();
			shoppingCartGoods.setGoodsID(goods.getGoodsID());
			shoppingCartGoods.setGoodsName(goods.getGoodsName());
			shoppingCartGoods.setGoodsPrice(goods.getGoodsPrice());
			shoppingCartGoods.setBuyQuantity(goods.getBuyQuantity());
			shoppingCartGoodsLt.add(shoppingCartGoods);
		});
		return shoppingCartGoodsLt;
	}
	
	public Map<BigDecimal,ShoppingCartGoods> cartGoodsShow(Map<BigDecimal,ShoppingCartGoods> shoppingCar) {
		Set<BigDecimal> goodsInputQuantitySet = new  LinkedHashSet<>();			
		goodsInputQuantitySet = shoppingCar.keySet();
		//從資料庫查詢最新的商品資訊
		Map<BigDecimal, Goods> buyGoodsInf = frontEndDao.queryBuyGoods(goodsInputQuantitySet);		
		shoppingCar.forEach((key,goods) -> {
			//更新購物車內的商品價格
			goods.setGoodsPrice(buyGoodsInf.get(key).getGoodsPrice());			
		});
		return shoppingCar;
	}
	
	public ShoppingCartGoodsInfo cartGoodsShowRtn(Map<BigDecimal,ShoppingCartGoods> shoppingCar) {
		Set<ShoppingCartGoods> shoppingCartGoodsLt = new HashSet<>();	
		ShoppingCartGoodsInfo shoppingCartGoodsInfo = new ShoppingCartGoodsInfo();
		shoppingCar.forEach((key,goods) -> {
			ShoppingCartGoods shoppingCartGoods = new ShoppingCartGoods();
			shoppingCartGoods.setGoodsID(goods.getGoodsID());
			shoppingCartGoods.setGoodsName(goods.getGoodsName());
			shoppingCartGoods.setGoodsPrice(goods.getGoodsPrice());
			shoppingCartGoods.setBuyQuantity(goods.getBuyQuantity());
			shoppingCartGoods.setGoodsImageName(goods.getGoodsImageName());
			shoppingCartGoodsLt.add(shoppingCartGoods);
		});
		//計算購物車內總金額
		shoppingCartGoodsInfo.setTotalAmount(shoppingCar.values().stream().mapToInt(g -> g.getGoodsPrice()*g.getBuyQuantity()).sum());
		shoppingCartGoodsInfo.setShoppingCartGoods(shoppingCartGoodsLt);
		return shoppingCartGoodsInfo;
	}
	
}
