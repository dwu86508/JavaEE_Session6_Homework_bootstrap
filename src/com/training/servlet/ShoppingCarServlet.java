package com.training.servlet;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.model.Goods;
import com.training.service.ShoppingCarService;
import com.training.vo.ShoppingCartGoods;
import com.training.vo.ShoppingCartGoodsInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig
public class ShoppingCarServlet extends  DispatchAction {	
	
	private ShoppingCarService shoppingCarService = ShoppingCarService.getInstance();
	
	//新增商品至購物車
	public ActionForward addCartGoods(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		Map<BigDecimal,ShoppingCartGoods> shoppingCar = new LinkedHashMap<>(); 		
		HttpSession session = req.getSession();
		JSONObject jsRtn = new JSONObject();
		List<ShoppingCartGoods> shoppingCartGoodsLt = new ArrayList<>();		
		String goodsID = req.getParameter("goodsID");	
		String msg = null;
		String msgFrom = "error";
		//判斷是否有輸入商品數量
		if(req.getParameter("buyQuantity").length() != 0 ) {
			Integer buyQuantity = Integer.parseInt(req.getParameter("buyQuantity"));
			//判斷商品數量是否大於0
			if(buyQuantity>0) {			
				//查詢購買商品資訊
				 ShoppingCartGoods good = shoppingCarService.cartGoodsInf(goodsID);
				 good.setBuyQuantity(buyQuantity);
				//判斷session內是否已存在購物車
				shoppingCar = (Map<BigDecimal, ShoppingCartGoods>) session.getAttribute("shoppingCar");
				shoppingCar = shoppingCarService.addCartGoods(shoppingCar,good);				
				shoppingCartGoodsLt = shoppingCarService.cartGoodsRtn(shoppingCar);
				session.setAttribute("shoppingCar", shoppingCar);
				session.setAttribute("checkout", "no");				
				msgFrom = "addGood";
				jsRtn.put("msg", shoppingCartGoodsLt);
			}else {
				msg = "商品數量需大於0!!";	
			}
		}else {
			msg = "請輸入商品數量!!";	
		}
		if(msgFrom.equals("error")) {
			jsRtn.put("msg", msg);
			
		};		
		jsRtn.put("msgFrom", msgFrom);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		out.println(JSONArray.fromObject(jsRtn));
		out.flush();
		out.close();	
		
//		return mapping.findForward("addCartGoods");
		return null;
	}
	
	//查詢購物車內商品
	 public ActionForward queryCartGoods(ActionMapping mapping, ActionForm form,			 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {			
		Map<BigDecimal,ShoppingCartGoods> shoppingCar = new LinkedHashMap<>();
		HttpSession session = req.getSession();
		JSONObject jsRtn = new JSONObject();
		ShoppingCartGoodsInfo shoppingCartGoodsInfo = new ShoppingCartGoodsInfo();		
		String msgFrom = "error";
		//判斷購物車內是否有商品
		if(session.getAttribute("shoppingCar") == null) {			
			jsRtn.put("msg", "尚未加入商品!!");
		}else {
			//從session中取出購物車資料
			shoppingCar = (Map<BigDecimal, ShoppingCartGoods>) session.getAttribute("shoppingCar");
			shoppingCar = shoppingCarService.cartGoodsShow(shoppingCar);
			//輸出購物車內所有商品
			shoppingCartGoodsInfo = shoppingCarService.cartGoodsShowRtn(shoppingCar);			
			msgFrom = "shoppingCar";
			jsRtn.put("msg", shoppingCartGoodsInfo);
			session.setAttribute("shoppingCar", shoppingCar);
		}
		jsRtn.put("msgFrom", msgFrom);				
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();	
		out.println(JSONObject.fromObject(jsRtn));
		out.flush();
		out.close();    	
		return null;	
	 }
	 
	 
	public ActionForward updateCar(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		Map<BigDecimal,ShoppingCartGoods> shoppingCar = new LinkedHashMap<>();
		HttpSession session = req.getSession();
		JSONObject jsRtn = new JSONObject();
		ShoppingCartGoodsInfo shoppingCartGoodsInfo = new ShoppingCartGoodsInfo();		
		String msgFrom = "error";
		String goodsID = req.getParameter("goodsID");			
		Integer buyQuantity = Integer.parseInt(req.getParameter("buyQuantity"));	
		//查詢購買商品資訊
		ShoppingCartGoods good = shoppingCarService.cartGoodsInf(goodsID);
		good.setBuyQuantity(buyQuantity);
		//判斷session內是否已存在購物車
		shoppingCar = (Map<BigDecimal, ShoppingCartGoods>) session.getAttribute("shoppingCar");
		shoppingCar.put(good.getGoodsID(), good);								
		shoppingCartGoodsInfo = shoppingCarService.cartGoodsShowRtn(shoppingCar);			
		msgFrom = "shoppingCar";
		jsRtn.put("msg", shoppingCartGoodsInfo);
		session.setAttribute("shoppingCar", shoppingCar);		
		jsRtn.put("msgFrom", msgFrom);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();	
		out.println(JSONObject.fromObject(jsRtn));
		out.flush();
		out.close();    	
		return null;
	}
	
	public ActionForward removeGoods(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		Map<BigDecimal,ShoppingCartGoods> shoppingCar = new LinkedHashMap<>();
		HttpSession session = req.getSession();
		JSONObject jsRtn = new JSONObject();
		ShoppingCartGoodsInfo shoppingCartGoodsInfo = new ShoppingCartGoodsInfo();		
		String msgFrom = "error";
		String goodsID = req.getParameter("goodsID");		
		shoppingCar = (Map<BigDecimal, ShoppingCartGoods>) session.getAttribute("shoppingCar");
		shoppingCar.remove(new BigDecimal(goodsID));
		if(shoppingCar.size()==0) {
			shoppingCar.clear();
			session.removeAttribute("shoppingCar");
			jsRtn.put("msg", "尚未加入商品!!");
		}else {
			shoppingCartGoodsInfo = shoppingCarService.cartGoodsShowRtn(shoppingCar);			
			msgFrom = "shoppingCar";
			jsRtn.put("msg", shoppingCartGoodsInfo);
			session.setAttribute("shoppingCar", shoppingCar);			
		}
		jsRtn.put("msgFrom", msgFrom);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();	
		out.println(JSONObject.fromObject(jsRtn));
		out.flush();
		out.close();    	
		return null;
	}
	 
	 //清空購物車
	 public ActionForward clearCartGoods(ActionMapping mapping, ActionForm form,			 
			    HttpServletRequest req, HttpServletResponse response) throws Exception {		 
		HttpSession session = req.getSession();
		JSONObject jsRtn = new JSONObject();		
		session.removeAttribute("shoppingCar");	
		session.removeAttribute("checkout");
		jsRtn.put("msgFrom", "clearShoppingCar");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();	
		out.println(JSONObject.fromObject(jsRtn));
		out.flush();
		out.close();
		return null;
	 }
}
