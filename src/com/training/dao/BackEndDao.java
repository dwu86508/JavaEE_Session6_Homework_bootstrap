package com.training.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;
import com.training.model.Goods;
import com.training.model.MembersInf;
import com.training.vo.GoodsListSearch;
import com.training.vo.SalesReport;
import com.training.vo.ShoppingCartGoods;


public class BackEndDao {
	
	private static BackEndDao backEndDao = new BackEndDao();
	
	private BackEndDao(){ }

	public static BackEndDao getInstance(){
		return backEndDao;
	}
	
	/**
	 * 後臺管理商品列表
	 * @return Set(Goods)
	 */
	public MembersInf queryAccountById(String id){
		MembersInf membersInf = null;		
		// querySQL SQL		
		String querySQL = "SELECT IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME FROM BEVERAGE_MEMBER WHERE IDENTIFICATION_NO = ?";		
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
		    // Step2:Create prepareStatement For SQL
			PreparedStatement stmt = conn.prepareStatement(querySQL)){
			stmt.setString(1, id);
			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					membersInf = new MembersInf();
					membersInf.setMembersID(rs.getString("IDENTIFICATION_NO"));
					membersInf.setMembersPwd(rs.getString("PASSWORD"));
					membersInf.setMembersName(rs.getString("CUSTOMER_NAME"));
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return membersInf;
	}
	
	public boolean memberSingup(String ID,String PWD,String name){
		boolean singupYn = false;		
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			// Insert SQL
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO BEVERAGE_MEMBER ( IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME) ");
			sb.append("VALUES ( ?, ?, ?)");
			String insertSQL = sb.toString();
			// Step2:Create prepareStatement For SQL
			try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)){
				// Step3:將"資料欄位編號"、"資料值"作為引數傳入				
				pstmt.setString(1, ID);
				pstmt.setString(2, PWD);				
				pstmt.setString(3, name);
				// Step4:Execute SQL
				int singupCount = 0;
				singupCount = pstmt.executeUpdate();
				singupYn = (singupCount > 0) ? true : false;
							
				// Step5:Transaction commit(交易提交)
				conn.commit();
			} catch (SQLException e) {
				// 若發生錯誤則資料 rollback(回滾)
				conn.rollback();
				throw e;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return singupYn;
	}
	
	public Set<Goods> queryGoods(GoodsListSearch goodsListSearch) {
		Set<Goods> goods = new LinkedHashSet<>(); 
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS FROM ");
		sb.append("(SELECT ROWNUM ROW_NUM, GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS FROM BEVERAGE_GOODS B WHERE GOODS_ID=GOODS_ID  ");
		if(goodsListSearch!=null) {
			if(goodsListSearch.getGoodsID()!=null) {
				sb.append(" AND B.GOODS_ID=" + goodsListSearch.getGoodsID());
			}
			if(goodsListSearch.getGoodsName()!=null  ) {
				sb.append(" AND LOWER(B.GOODS_NAME) like '%" + goodsListSearch.getGoodsName().toLowerCase() +"%'" );
			}
			if(goodsListSearch.getLowestPrice()!= 0) {
				sb.append(" AND B.PRICE >= " + goodsListSearch.getLowestPrice());
			}
			if(goodsListSearch.getHighestPrice()!= 0) {
				sb.append(" AND B.PRICE <= " + goodsListSearch.getHighestPrice());
			}
			if(goodsListSearch.getGoodsQuantity()!=0) {
				sb.append(" AND QUANTITY < " + goodsListSearch.getGoodsQuantity());
			}
			if(goodsListSearch.getStatus()!=null ) {
				if(!goodsListSearch.getStatus().equals("2")) {
					sb.append(" AND STATUS = " + goodsListSearch.getStatus());
				}
			}
			if(goodsListSearch.getPriceOrderby()!=null ) {
				if(!goodsListSearch.getPriceOrderby().equals("0")) {
					sb.append(" ORDER BY B.PRICE " + goodsListSearch.getPriceOrderby());
				}
			}
			sb.append(" ) WHERE ROW_NUM BETWEEN ? AND  ? ");
		}else {
			sb.append(" )");
		}
				
		String querySQL = sb.toString();
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			PreparedStatement pstmt = conn.prepareStatement(querySQL);
			if(goodsListSearch!=null) {
				pstmt.setInt(1, goodsListSearch.getPage()*15-14);
				pstmt.setInt(2, goodsListSearch.getPage()*15);
			}
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
	
	public GoodsListSearch queryGoodsAmount(GoodsListSearch goodsListSearch) {
		Set<Goods> goods = new LinkedHashSet<>(); 
		StringBuilder sb = new StringBuilder();		
		sb.append("SELECT COUNT(*) GOODS_AMOUNT FROM BEVERAGE_GOODS B WHERE GOODS_ID=GOODS_ID  ");
		if(goodsListSearch!=null) {
			if(goodsListSearch.getGoodsID()!=null) {
				sb.append(" AND B.GOODS_ID=" + goodsListSearch.getGoodsID());
			}
			if(goodsListSearch.getGoodsName()!=null) {
				sb.append(" AND LOWER(B.GOODS_NAME) like '%" + goodsListSearch.getGoodsName().toLowerCase() +"%'" );
			}
			if(goodsListSearch.getLowestPrice()!= 0) {
				sb.append(" AND B.PRICE >= " + goodsListSearch.getLowestPrice());
			}
			if(goodsListSearch.getHighestPrice()!= 0) {
				sb.append(" AND B.PRICE <= " + goodsListSearch.getHighestPrice());
			}
			if(goodsListSearch.getGoodsQuantity()!=0) {
				sb.append(" AND QUANTITY < " + goodsListSearch.getGoodsQuantity());
			}
			if(goodsListSearch.getStatus()!=null) {
				if(!goodsListSearch.getStatus().equals("2") && goodsListSearch.getStatus()!=null) {
					sb.append(" AND STATUS = " + goodsListSearch.getStatus());
				}
			}
			if(goodsListSearch.getPriceOrderby()!=null) {
				if(!goodsListSearch.getPriceOrderby().equals("0")) {
					sb.append(" ORDER BY B.PRICE " + goodsListSearch.getPriceOrderby());
				}
			}
		}
		
		String querySQL = sb.toString();
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			PreparedStatement pstmt = conn.prepareStatement(querySQL);			
			try (ResultSet rs = pstmt.executeQuery()){				
				while (rs.next()) {
					goodsListSearch.setListTotal(rs.getInt("GOODS_AMOUNT"));
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
		return goodsListSearch;
	}
	
	public Goods queryGoodsById(String goodsIDs){
		 Goods good = new Goods();
		String goodsSearchSql = ("SELECT GOODS_ID,GOODS_NAME,PRICE,QUANTITY,STATUS FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ");			
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
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setStatus(rs.getString("STATUS"));	
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
	
	public int goodsQuantity(Goods goods) {
		int goodsQuantity  = 0;
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			// Insert SQL
			String insertSQL = "SELECT QUANTITY FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ";
			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			pstmt.setBigDecimal(1, goods.getGoodsID());
			try (ResultSet rs = pstmt.executeQuery()){				
					while (rs.next()) {
						goodsQuantity = rs.getInt("QUANTITY");
					}			
				conn.commit();
			} catch (SQLException e) {
				// 若發生錯誤則資料 rollback(回滾)
				conn.rollback();
				throw e;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
			return goodsQuantity;		
	}
	

	public boolean updateGoods(Goods goods) {
		int updateCount = 0;
		boolean updateSuccess = false;
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			// Update SQL
			String updateSQL = "UPDATE BEVERAGE_GOODS SET PRICE = ?, QUANTITY = ?, STATUS = ? WHERE GOODS_ID = ?";
			// Step2:Create prepareStatement For SQL
			try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)){
				// Step3:將"資料欄位編號"、"資料值"作為引數傳入
				pstmt.setInt(1, goods.getGoodsPrice());
				pstmt.setInt(2, goods.getGoodsQuantity());
				pstmt.setString(3, goods.getStatus());
				pstmt.setBigDecimal(4, goods.getGoodsID());
				// Step4:Execute SQL			
				updateCount = pstmt.executeUpdate();
				updateSuccess = (updateCount > 0) ? true : false;
				// Step5:交易提交
				conn.commit();	
			} catch (SQLException e) {
				// 發生 Exception 交易資料 roll back
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return updateSuccess;
	}	

	public int addGoods(Goods goods){
		int goodsID = 0;		
		String[] cols = { "GOODS_ID" };
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			// Insert SQL
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS) ");
			sb.append("VALUES (BEVERAGE_GOODS_SEQ.NEXTVAL, ?, ?, ?, ?, ?)");
			String insertSQL = sb.toString();
			// Step2:Create prepareStatement For SQL
			try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, cols)){
				// Step3:將"資料欄位編號"、"資料值"作為引數傳入				
				pstmt.setString(1, goods.getGoodsName());
				pstmt.setInt(2, goods.getGoodsPrice());				
				pstmt.setInt(3, goods.getGoodsQuantity());
				pstmt.setString(4, goods.getGoodsImageName());
				pstmt.setString(5, goods.getStatus());				
				// Step4:Execute SQL
				pstmt.executeUpdate();				
				// 取對應的自增主鍵值
				ResultSet rsKeys = pstmt.getGeneratedKeys();
				rsKeys.next();
				goodsID = rsKeys.getInt(1);				
				// Step5:Transaction commit(交易提交)
				conn.commit();
			} catch (SQLException e) {
				// 若發生錯誤則資料 rollback(回滾)
				conn.rollback();
				throw e;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goodsID;
	}

	public Set<SalesReport> queryOrderBetweenDate(String queryStartDate, String queryEndDate) {
		Set<SalesReport> reports = new LinkedHashSet<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT O.ORDER_ID,O.ORDER_DATE,O.GOODS_BUY_PRICE,O.BUY_QUANTITY,M.CUSTOMER_NAME,G.GOODS_NAME FROM BEVERAGE_ORDER O  ");
		sb.append("LEFT JOIN BEVERAGE_GOODS G ON O.GOODS_ID=G.GOODS_ID ");
		sb.append("LEFT JOIN BEVERAGE_MEMBER M ON O.CUSTOMER_ID=M.IDENTIFICATION_NO ");
		sb.append("WHERE O.ORDER_DATE BETWEEN TO_DATE(?,'YYYY-mm-DD HH24:MI:SS') AND TO_DATE(?,'YYYY-mm-DD HH24:MI:SS') ");
		sb.append("ORDER BY O.ORDER_ID");
		String orderSearchSql = sb.toString();		
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()){
			// 設置交易不自動提交
			conn.setAutoCommit(false);			
			PreparedStatement pstmt = conn.prepareStatement(orderSearchSql);
			pstmt.setString(1, queryStartDate+" 00:00:00");
			pstmt.setString(2, queryEndDate+" 23:59:59");
			
			try (ResultSet rs = pstmt.executeQuery()){				
				while (rs.next()) {
					SalesReport salesReport = new SalesReport();
					salesReport.setOrderID(rs.getInt("ORDER_ID"));
					salesReport.setCustomerName(rs.getString("CUSTOMER_NAME"));
					salesReport.setOrderDate(rs.getString("ORDER_DATE"));
					salesReport.setGoodsName(rs.getString("GOODS_NAME"));
					salesReport.setGoodsBuyPrice(rs.getInt("GOODS_BUY_PRICE"));					
					salesReport.setBuyQuantity(rs.getInt("BUY_QUANTITY"));
					salesReport.setBuyAmount(rs.getInt("BUY_QUANTITY")*rs.getInt("GOODS_BUY_PRICE"));	
					reports.add(salesReport);
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
		return reports;
	}	
	
}
