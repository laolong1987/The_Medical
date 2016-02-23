package com.web.controller.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.StringUtil;
import com.web.entity.ClientQuestion;
import com.web.service.ClientQuestionService;

@Controller
@RequestMapping("/saveclientquestion")
public class SaveClientQuestionController {
	
	@Autowired
	public ClientQuestionService clientquestionservice;
	
	//保存用户提问的信息
	@RequestMapping(value = "/saveQuestions")
	public String saveQuestions(HttpServletRequest request,HttpServletResponse response,ClientQuestion question){
		boolean isSubmmited = isFormSubbmited(request);
		if(isSubmmited){
		    request.setAttribute("msg", "您的问题已提交");
		}else{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(new Date());
		String empid = StringUtil.safeToString(request.getSession().getAttribute("id"), "");
		//java.util.Date nowdate = new java.sql.Date(date.getTime());
		Date date2 = null;
		try {
			date2 = sdf.parse(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//question.setCreatetime(new Date());
		//String countet= question.getCountent();
		String title = StringUtil.safeToString(request.getParameter("title"), "");
		
		String countent = StringUtil.safeToString(request.getParameter("content"),"");
		//int type = question.getType();
		//question.getUpdatetime();
		question.setCreatetime(date2);
		question.setCountent(countent);
		question.setEmpid(Integer.valueOf(empid));
		//question.setId(0);
		//question.setStatus(status);
		question.setTitle(title);
		question.setStatus(0);
		question.setUpdatetime(date2);
		
		
		//question.setType(type);
		//question.setUpdatetime(new Date());
		
		//String username = (String)request.getSession().getAttribute("username");
		//request.getAttribute(username);
		
		clientquestionservice.saveQuetions(question);
		request.setAttribute("msg", "您的问题提交成功");
		}
		//question.setEmpid();
		return "redirect:/clientquestion/home";
	}
	
	/**
     * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
     * @param request
     * @return
     *      true 用户重复提交了表单 
     *      false 用户没有重复提交表单
     */
    public boolean isFormSubbmited(HttpServletRequest request){
        //获取客户端的Token
        String client_Token = request.getParameter("client_token");
        
        HttpSession session = request.getSession();
        //获取服务器端的session
        String session_Token = (String) session.getAttribute("session_token");
        
        //如果用户提交的表单中没有Token，则用户重复提交了表单
        if(client_Token == null){
            return true;
        }
        
        //如果当前用户的Session中没有Token
        if(session_Token == null){
            return true;
        }
        
        
        //如果表单中的Token和用户Session中的Token不一致，则用户重复提交
        if(!client_Token.equals(session_Token)){
            return true;
        }
        
        session.removeAttribute("session_token");
        return false;
        
    }

}
