package com.training.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.training.dao.BackEndDao;
import com.training.model.MembersInf;

public class LoginAction extends DispatchAction {
	
	private BackEndDao backEndDao = BackEndDao.getInstance();
	
	/**
	 * info:這是負責"登入"的action method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward login(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 登入請求
    	ActionForward actFwd = null;
    	HttpSession session = request.getSession();
    	String inputID = request.getParameter("id");
        String inputPwd = request.getParameter("pwd");
        // Step2:依使用者所輸入的帳戶名稱取得 Member
        MembersInf membersInf = backEndDao.queryAccountById(inputID);
        String loginMsg = null;
    	if(membersInf != null) {
    		// Step3:取得帳戶後進行帳號、密碼比對
    		String id = membersInf.getMembersID();    		
    		String pwd = membersInf.getMembersPwd();
    		if(id.equals(inputID) && pwd.equals(inputPwd)) {
    			loginMsg = membersInf.getMembersName() + " 先生/小姐您好!";
    			// 將account存入session scope 以供LoginCheckFilter之後使用!
    			session.setAttribute("membersInf", membersInf);    					
    			actFwd = mapping.findForward("success");        			
    		} else {
                // Step4:帳號、密碼錯誤,轉向到 "/BankLogin.html" 要求重新登入.
    			loginMsg="帳號或密碼錯誤";
    			actFwd = mapping.findForward("fail");
    		}
    	} else {
            // Step5:無此帳戶名稱,轉向到 "/BankLogin.html" 要求重新登入.
    		loginMsg="無此帳戶名稱,請重新輸入!";        		
    		actFwd = mapping.findForward("fail");
    	}
    	request.setAttribute("loginMsg", loginMsg);
    	return actFwd;
    }
    
    
    
    /**
     * info:這是負責"登出"的action method
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward logout(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 登出請求
    	HttpSession session = request.getSession();
		session.removeAttribute("account");		
		session.removeAttribute("modifyGoodID");
		session.removeAttribute("checkout");
		session.removeAttribute("goodsPages");
		session.removeAttribute("shoppingCar");
		request.setAttribute("loginMsg", "謝謝您的光臨!");
    	return mapping.findForward("fail");
    }
    
    
    public ActionForward singup(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ActionForward actFwd = null;
    	String inputID = request.getParameter("id");        
        MembersInf membersInf = backEndDao.queryAccountById(inputID);
        String singupMsg = null;
    	if(membersInf == null) {
	    	String inputPwd = request.getParameter("pwd");	  
	        String inputName = request.getParameter("name");
	        if(inputPwd.equals(request.getParameter("pwdCheck"))) {
	        	boolean singupYn = backEndDao.memberSingup(inputID, inputPwd, inputName);
	        	if(singupYn) {
	        		HttpSession session = request.getSession(); 
	        		membersInf = new MembersInf();
	        		membersInf.setMembersID(inputID);
	        		membersInf.setMembersPwd(inputPwd);
	        		membersInf.setMembersName(inputPwd);
	        		session.setAttribute("membersInf", membersInf);
		        	System.out.println("註冊成功");		        	
		        	actFwd = mapping.findForward("singupSuccess");
	        	}else {
	        		singupMsg = "註冊失敗，請從新註冊!!";		        	
		        	actFwd = mapping.findForward("singupFail");
	        	}
	        }else {
	        	singupMsg="密碼與確認密碼不一致!!!";	        	
	        	actFwd = mapping.findForward("singupFail");
	        }
    	}else {
    		singupMsg="帳號已存在!!!";
        	actFwd = mapping.findForward("singupFail");
    	}
    	request.setAttribute("singupMsg", singupMsg);
    	return actFwd; 	      
    }
}
