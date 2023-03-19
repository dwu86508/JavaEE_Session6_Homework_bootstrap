package com.training.service;

import java.util.Set;

import com.training.dao.BackEndDao;
import com.training.model.Goods;
import com.training.vo.GoodsListSearch;
import com.training.vo.SalesReport;

public class BackEndService {
	
	private static BackEndService backEndService = new BackEndService();

	private BackEndService(){ }
	
	private BackEndDao backEndDao = BackEndDao.getInstance();
	
	public static BackEndService getInstance(){
		return backEndService;
	}
	
	public GoodsListSearch queryGoodsSearch(GoodsListSearch goodsListSearch) {
		// 1.後臺管理商品列表
		int pageNo = goodsListSearch.getPage()== 0 ? 1 : goodsListSearch.getPage();
		//計算頁中頁
		int pagePage = pageNo/3==0 ? 1 : (int)Math.ceil((double)pageNo/3);
		goodsListSearch.setPage(pageNo);
		goodsListSearch.setPagePage(pagePage);
		//取得總筆數
		goodsListSearch = backEndDao.queryGoodsAmount(goodsListSearch);
		//取得該頁中good資訊
		Set<Goods> goodsList = backEndDao.queryGoods(goodsListSearch);
		goodsListSearch.setGoodsList(goodsList);
		int goodsAmount = goodsListSearch.getListTotal();
		//計算最後頁
		int lastPage = goodsAmount%15==0 ? goodsAmount/15 : (goodsAmount/15)+1;
		lastPage = goodsAmount<=15 ? 0 : lastPage;
		goodsListSearch.setLastPage(lastPage);
		return goodsListSearch;
	}

	public Set<Goods> queryGoods() {
		// 1.後臺管理商品列表
		Set<Goods> goodsList = backEndDao.queryGoods(null);		
		return goodsList;
	}
	
	public Goods queryGoodsById(String goodsID) {
		
		Goods goods = backEndDao.queryGoodsById(goodsID);		
		return goods;
	}
	
	public boolean updateGoods(Goods goods) {
		// 1.後臺管理商品列表
		int goodsQuantityBf = backEndDao.goodsQuantity(goods);
		goods.setGoodsQuantity(goodsQuantityBf + goods.getGoodsQuantity()); 
		boolean updateinf = backEndDao.updateGoods(goods);
		return updateinf;
	}
	
	public int addGoods(Goods goods) {
		// 1.後臺管理商品列表
		int goodsID = backEndDao.addGoods(goods);
		return goodsID;
	}
	
	public Set<SalesReport> queryOrderBetweenDate(String queryStartDate, String queryEndDate){
		Set<SalesReport> reports = backEndDao.queryOrderBetweenDate(queryStartDate, queryEndDate);
		return reports;
	}
	
}
	
	
//		// 2.後臺管理新增商品
//		Goods goods = new Goods();
//		goods.setGoodsName("黑糖珍珠鮮奶茶");
//		goods.setGoodsPrice(65);
//		goods.setGoodsQuantity(10);
//		goods.setGoodsImageName("BrownSugarPearlMilkTea.jpg");
//		goods.setStatus("1");
//		int goodsID = backendDao.createGoods(goods);
//		if(goodsID > 0){ System.out.println("商品新增上架成功！ 商品編號：" + goodsID); }
//		System.out.println("----------------------------------------");
//		
//		// 3.後臺管理更新商品
//		goods.setGoodsID(new BigDecimal(goodsID));
//		goods.setGoodsPrice(55); // 更改價格
//		boolean updateSuccess = backendDao.updateGoods(goods);
//		if(updateSuccess){ System.out.println("商品更新成功！ 商品編號：" + goodsID); }
//		System.out.println("----------------------------------------");
//		
//		// 4.後臺管理刪除商品
//		boolean deleteSuccess = backendDao.deleteGoods(goods.getGoodsID());
//		if(deleteSuccess){ System.out.println("商品刪除成功！ 商品編號：" + goodsID); }
//		System.out.println("----------------------------------------");
//		
//		// 5.後臺管理顧客訂單查詢
//		String orderDate = sf.format(Calendar.getInstance().getTime());
//		Set<SalesReport> reports = backendDao.queryOrderBetweenDate(orderDate, orderDate);
//		reports.stream().forEach(r -> System.out.println(r));
		
	



