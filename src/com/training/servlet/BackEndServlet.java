package com.training.servlet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;


import com.training.formbean.GoodsForm;
import com.training.formbean.GoodsListSearchForm;
import com.training.model.Goods;
import com.training.model.MembersInf;
import com.training.service.BackEndService;
import com.training.service.ShoppingCarService;
import com.training.vo.GoodsListSearch;
import com.training.vo.SalesReport;
import com.training.vo.ShoppingCartGoods;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig
public class BackEndServlet extends  DispatchAction {	
	
	private BackEndService backEndService = BackEndService.getInstance();
	
	
	public ActionForward queryGoods(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		GoodsListSearch goodsListSearch = new GoodsListSearch();		
		goodsListSearch = backEndService.queryGoodsSearch(goodsListSearch);		
		req.setAttribute("goodsListSearch", goodsListSearch);		
		return mapping.findForward("queryGoods");		
	}
	
	public ActionForward queryGoodsSearch(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		GoodsListSearch goodsListSearch = new GoodsListSearch();
		BigDecimalConverter bd = new BigDecimalConverter(null);
		ConvertUtils.register(bd, java.math.BigDecimal.class);
		GoodsListSearchForm goodsListSearchForm = (GoodsListSearchForm) form;		
		BeanUtils.copyProperties(goodsListSearch,goodsListSearchForm);		
		goodsListSearch = backEndService.queryGoodsSearch(goodsListSearch);		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		out.println(JSONArray.fromObject(goodsListSearch));
		out.flush();
		out.close();	
    	return null;	
	}	

	public ActionForward updateGoodsView(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		Set<Goods> goodsList = backEndService.queryGoods();		
		req.setAttribute("goodsLists", goodsList);		
		return mapping.findForward("updateGoodsView");
	}
	
	
	public ActionForward getupdateGoods(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse resp) throws Exception {		
		String goodsID = req.getParameter("goodsID");
		Goods good = backEndService.queryGoodsById(goodsID);		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(JSONObject.fromObject(good));
		out.flush();
		out.close();
		return null;
	}
	
	
	public ActionForward updateGoods(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse resp) throws Exception {		
		GoodsListSearchForm goodsListSearchForm = (GoodsListSearchForm) form;
		Goods goods = new Goods();
		BeanUtils.copyProperties(goods,goodsListSearchForm);
		boolean updateinf = backEndService.updateGoods(goods);
		String message = updateinf ? "商品維護作業成功！" : "商品維護作業失敗！";
		int quantity = updateinf ? goods.getGoodsQuantity() : goodsListSearchForm.getGoodsQuantity();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		JSONObject jsRtn = new JSONObject();
		jsRtn.put("quantity", quantity);
		jsRtn.put("msg", message);
		out.println(JSONObject.fromObject(jsRtn));
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward addGoodsView(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		return mapping.findForward("addGoodsView");
	}
	
	public ActionForward addGoods(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse resp) throws Exception {
			HttpSession session = req.getSession();
			GoodsListSearchForm goodsListSearchForm = (GoodsListSearchForm) form;
			Goods goods = new Goods();
			BeanUtils.copyProperties(goods,goodsListSearchForm);			
			String serverGoodsImgPath = req.getSession().getServletContext().getRealPath("/DrinksImage");
		    FormFile goodsImage = goodsListSearchForm.getGoodsImage();			    
		    String fileName = goodsImage.getFileName();				
		    Path serverImgPath = Paths.get(serverGoodsImgPath).resolve(fileName);			    
		    try (InputStream fileContent = goodsImage.getInputStream();){
		        Files.copy(fileContent, serverImgPath, StandardCopyOption.REPLACE_EXISTING);
		    }
		    goods.setGoodsImageName(fileName);
			int goodsID = backEndService.addGoods(goods);
			String message = goodsID > 0 ? "商品新增上架成功！ 商品編號：" + goodsID : "商品新增失敗" ;			
			session.setAttribute("createMsg", message);					
			return mapping.findForward("addGoods"); 
	}
	
	public ActionForward querySalesReportView(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse response) throws Exception {
		return mapping.findForward("querySalesReportView");
	}
	
	public ActionForward querySalesReport(ActionMapping mapping, ActionForm form, 
		    HttpServletRequest req, HttpServletResponse resp) throws Exception {		
		String queryStartDate = req.getParameter("queryStartDate");
		String queryEndDate = req.getParameter("queryEndDate");
		Set<SalesReport> reports = backEndService.queryOrderBetweenDate(queryStartDate, queryEndDate);
		req.setAttribute("reports", reports);	
		// Redirect to view
		return mapping.findForward("querySalesReportView");
	}

}
