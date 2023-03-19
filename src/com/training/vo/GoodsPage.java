package com.training.vo;

import java.util.Set;

import com.training.model.Goods;

public class GoodsPage {
	
	private int page;
	
	private int lastPage;
	
	private int pagePage;
	
	private String searchKeyWord;
	
	private Set<Goods> goodsList;

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

	public String getSearchKeyWord() {
		return searchKeyWord;
	}

	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}

	public Set<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(Set<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	@Override
	public String toString() {
		return "GoodsPage [page=" + page + ", lastPage=" + lastPage + ", pagePage=" + pagePage + ", searchKeyWord="
				+ searchKeyWord + ", goodsList=" + goodsList + "]";
	}

	
	

}
