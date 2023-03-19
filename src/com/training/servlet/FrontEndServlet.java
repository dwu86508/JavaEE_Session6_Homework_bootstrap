package com.training.servlet;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.model.Goods;
import com.training.model.MembersInf;
import com.training.service.BackEndService;
import com.training.service.FrontEndService;
import com.training.vo.BuyOrderRtn;
import com.training.vo.GoodsPage;
import com.training.vo.ShoppingCartGoods;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class FrontEndServlet extends DispatchAction {	
	
	private FrontEndService frontEndService = FrontEndService.getInstance();	
	
	public ActionForward buyGoodsView(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{		
    	return mapping.findForward("buyGoodsView");
    }
	
	public ActionForward shoppingCarView(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{		
    	return mapping.findForward("shoppingCarView");
    }
	
	public ActionForward searchGoods(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();			
		System.out.println(request.getParameter("searchKeyword"));
		System.out.println(request.getParameter("pageNo"));
		GoodsPage goodsPage = frontEndService.searchGoodsPage(request.getParameter("searchKeyword"),request.getParameter("pageNo"));		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		out.println(JSONArray.fromObject(goodsPage));
		out.flush();
		out.close();	
    	return null;
    }
	
	public ActionForward buyGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception { 	
		BuyOrderRtn buyOrderRtn = new BuyOrderRtn();
		List<ShoppingCartGoods> buyGoodsRtn = new ArrayList<>(); 
		HttpSession session = req.getSession();	
		JSONObject jsRtn = new JSONObject();
		String msg = null;
		String msgFrom = "error";
		if(session.getAttribute("shoppingCar") == null) {			
			msg = "購物車為空，請先添加商品!!";			
		}else {			
			String buyAmount = req.getParameter("inputMoney");
			if(buyAmount.length()!=0) {				
				MembersInf membersInf = (MembersInf) session.getAttribute("membersInf");
				buyOrderRtn.setCustomerID(membersInf.getMembersID());
				buyOrderRtn.setInputMoney(Integer.parseInt(buyAmount));
				buyOrderRtn.setGoodsOrdersRtn((Map<BigDecimal, ShoppingCartGoods>) session.getAttribute("shoppingCar"));
				buyOrderRtn = frontEndService.buyGoods(buyOrderRtn);
				Map<BigDecimal, ShoppingCartGoods> goodsOrdersRtn = buyOrderRtn.getGoodsOrdersRtn();
				goodsOrdersRtn.forEach((k,v) -> buyGoodsRtn.add(v));
				buyOrderRtn.setGoodsOrdersRtn(null);
				buyOrderRtn.setBuyGoodsRtn(buyGoodsRtn);
				if(buyOrderRtn.isBuyYn()) {
					session.removeAttribute("shoppingCar");
					session.removeAttribute("checkout");					
				}
				msgFrom = "checkout";
				jsRtn.put("msg", buyOrderRtn);
			}else {
				msg = "請輸入金額!!";					
			}			
		}
		if(msgFrom.equals("error")) {
			jsRtn.put("msg", msg);			
		};		
		jsRtn.put("msgFrom", msgFrom);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();		
		out.println(JSONArray.fromObject(jsRtn));
		out.flush();
		out.close();	
    	return null;
    }
	
}
