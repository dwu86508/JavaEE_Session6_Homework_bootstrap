package com.training.dao;
import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.training.model.Goods;
import com.training.vo.ShoppingCartGoods;



public class FrontEndDao {
	
	private static FrontEndDao frontEndDao = new FrontEndDao();
	
	private FrontEndDao(){ }

	public static FrontEndDao getInstance(){
		return frontEndDao;
	}
	
	//商品查詢
	public Set<Goods> searchGoods(String searchKeyword,int startRowNo, int endRowNo) {
		Set<Goods> goods = new LinkedHashSet<>();		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS FROM ");
		sb.append("(SELECT ROWNUM ROW_NUM, GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS FROM BEVERAGE_GOODS B where LOWER(B.GOODS_NAME) like ? AND STATUS=1) ");
		sb.append("WHERE ROW_NUM BETWEEN ? AND  ? ");
		String goodsSearchSql = sb.toString();
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			PreparedStatement pstmt = conn.prepareStatement(goodsSearchSql);
			pstmt.setString(1, "%"+searchKeyword+"%");
			pstmt.setInt(2, startRowNo);
			pstmt.setInt(3, endRowNo);
			try (ResultSet rs = pstmt.executeQuery()){				
				while (rs.next()) {
					Goods good = new Goods();
					good.setGoodsID(rs.getBigDecimal("GOODS_ID"));
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));
					goods.add(good);
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
		return goods;
	}
	
	public int goodsAmount(String searchKeyword) {
				
		
		
		String goodsSearchSql = "SELECT COUNT(*) GOODS_AMOUNT FROM BEVERAGE_GOODS B where LOWER(B.GOODS_NAME) like ? AND STATUS=1 ";
		int goodsAmount=0;

		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			PreparedStatement pstmt = conn.prepareStatement(goodsSearchSql);
			pstmt.setString(1, "%"+searchKeyword+"%");
			try (ResultSet rs = pstmt.executeQuery()){				
				while (rs.next()) {
					goodsAmount = rs.getInt("GOODS_AMOUNT");
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
		return goodsAmount;
	}

	//顧客購買商品資料查詢
	public Map<BigDecimal, Goods> queryBuyGoods(Set<BigDecimal> goodsIDs){
		// key:商品編號、value:商品				
		Map<BigDecimal, Goods> goods = new LinkedHashMap<>();		
		Function<BigDecimal, String> mapper = id -> "?";
		String id = goodsIDs.stream().map(mapper).collect(Collectors.joining(","));
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT GOODS_ID,GOODS_NAME,PRICE,QUANTITY FROM BEVERAGE_GOODS WHERE GOODS_ID IN ");
		sb.append("("+id+")");
			String goodsSearchSql =sb.toString();
			try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
				// 設置交易不自動提交
				conn.setAutoCommit(false);			
				PreparedStatement pstmt = conn.prepareStatement(goodsSearchSql);				
				int parameterIndex = 0;
				for(BigDecimal goodsID : goodsIDs){
					pstmt.setBigDecimal(++parameterIndex, goodsID);
				}	
				try (ResultSet rs = pstmt.executeQuery()){				
					while (rs.next()) {
						Goods good = new Goods();
						good.setGoodsID(rs.getBigDecimal("GOODS_ID"));
						good.setGoodsName(rs.getString("GOODS_NAME"));
						good.setGoodsPrice(rs.getInt("PRICE"));
						good.setGoodsQuantity(rs.getInt("QUANTITY"));
						goods.put(rs.getBigDecimal("GOODS_ID"), good);
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
		
		return goods;
	}
	//建立訂單
	public boolean batchCreateGoodsOrder(String customerID, Map<BigDecimal, ShoppingCartGoods> goodsOrders){
		boolean insertSuccess = false;
		try(Connection conn = DBConnectionFactory.getOracleDBConnection()) {			
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			try (Statement stmt = conn.createStatement()) {	
				// addBatch 批次執行SQL指令					
				goodsOrders.forEach((key,goods) -> {
					StringBuilder sb = new StringBuilder();
					sb.append("INSERT INTO BEVERAGE_ORDER (ORDER_ID,ORDER_DATE,CUSTOMER_ID,GOODS_ID,GOODS_BUY_PRICE,BUY_QUANTITY) ");
					sb.append("values (BEVERAGE_ORDER_SEQ.NEXTVAL, SYSDATE, '" );
					sb.append(customerID+"',");
					sb.append(goods.getGoodsID()+",");
					sb.append(goods.getGoodsPrice()+",");
					sb.append(goods.getBuyQuantity()+")");
					String sql =sb.toString();
					try {
						stmt.addBatch(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				});				
				// 在執行 executeBatch()時SQL語法若有錯誤的情況下，會拋出 BatchUpdateException例外
				int[] insertCounts = stmt.executeBatch();
				// PS:Oracle 對於 PreparedStatement 批次更新所回傳的異動筆數沒有支援,但資料仍會被異動!
				for(int count : insertCounts){					
					if(count != 1){
						insertSuccess = false;
						conn.rollback();
						break;
					}
					insertSuccess = true;
				}				
			} catch(BatchUpdateException b) {
				// 交易取消資料 rollback(必須設置setAutoCommit false)
				conn.rollback();				
			}			
			// Transaction commit(交易提交)
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertSuccess;
	}	
	//更新庫存
	public boolean batchUpdateGoodsQuantity(Set<Goods> goods){
		boolean updateSuccess = false;
		try(Connection conn = DBConnectionFactory.getOracleDBConnection()) {			
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			try (Statement stmt = conn.createStatement()) {				
				// addBatch 批次執行SQL指令
				for(Goods good : goods) {
					StringBuilder sb = new StringBuilder();
					sb.append("UPDATE BEVERAGE_GOODS SET QUANTITY= ");
					sb.append(good.getGoodsQuantity());
					sb.append("where GOODS_ID = ");
					sb.append(good.getGoodsID());
					String sql = sb.toString();			
					stmt.addBatch(sql);	
				}	
				// 在執行 executeBatch()時SQL語法若有錯誤的情況下，會拋出 BatchUpdateException例外
				int[] insertCounts = stmt.executeBatch();
				// PS:Oracle 對於 PreparedStatement 批次更新所回傳的異動筆數沒有支援,但資料仍會被異動!
				for(int count : insertCounts){					
					if(count != 1){
						updateSuccess = false;
						conn.rollback();
						break;
					}
					updateSuccess = true;
				}				
			} catch(BatchUpdateException b) {
				// 交易取消資料 rollback(必須設置setAutoCommit false)
				conn.rollback();				
			}			
			// Transaction commit(交易提交)
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateSuccess;
	}
}