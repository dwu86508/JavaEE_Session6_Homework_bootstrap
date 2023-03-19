package com.training.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.training.model.Goods;
import com.training.vo.ShoppingCartGoods;

public class ShoppingCarDao {
	
	private static ShoppingCarDao shoppingCarDao = new ShoppingCarDao();
	
	private ShoppingCarDao(){ }

	public static ShoppingCarDao getInstance(){
		return shoppingCarDao;
	}
	
	public ShoppingCartGoods queryBuyGoods(String goodsIDs){
		 ShoppingCartGoods good = new ShoppingCartGoods();
		String goodsSearchSql = ("SELECT GOODS_ID,GOODS_NAME,PRICE,IMAGE_NAME FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ");			
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			PreparedStatement pstmt = conn.prepareStatement(goodsSearchSql);	
			pstmt.setString(1,goodsIDs);					
			try (ResultSet rs = pstmt.executeQuery()){				
				while (rs.next()) {					
					good.setGoodsID(rs.getBigDecimal("GOODS_ID"));
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
				}
				conn.commit();	
			} catch (SQLException e) {
				// 發生 Exception 交易資料 roll back
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return good;
	}

}
