package com.web.controller.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.StringUtil;
import com.utils.TokenProccessor;
import com.web.entity.ClientQuestion;
import com.web.service.ClientQuestionService;

//查询用户提出的所有问题和保存用户的提问信息
@Controller
@RequestMapping("/clientquestion")
public class ClientQuestionController {
	
	@Autowired
	public ClientQuestionService clientquestionservice;
	
	//查询用户提问的信息
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home")
	public String listAllQuestions(HttpServletRequest request,HttpServletResponse response,ClientQuestion question){
        Map<String,String> p = new HashMap<String, String>();
        Map emp =(Map)request.getSession().getAttribute("emp");
        String empid = StringUtil.safeToString(emp.get("id"), "");
        p.put("empid", empid);
        String totalRows = StringUtil.safeToString(clientquestionservice.getTotalRows(p).get("totalCount"),"");
        String page = StringUtil.safeToString(request.getParameter("page"), "");        
        int intTotalRows = Integer.valueOf(totalRows);
        if("" == page){
            page = "1";
        }
        int intPage = Integer.valueOf(page);
        int pageSize = 3;
        int totalPage = 0;
        if(intTotalRows%pageSize == 0){
        	totalPage = intTotalRows/pageSize;
        }else{
        	totalPage = intTotalRows/pageSize + 1;
        }
        
        int start = pageSize*(intPage - 1);
        int maxResult = 3;
        p.put("empid", empid);
        p.put("start", String.valueOf(start));
        p.put("maxResult", String.valueOf(maxResult));
		List<Map> list = clientquestionservice.listAllQuestions(p);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("totalRows",totalRows);
		request.setAttribute("listPage", intPage);
		request.setAttribute("list", list);
		String token = TokenProccessor.getInstance().makeToken();
		request.getSession().setAttribute("session_token", token);
		return "/jsp/website/onlineservice";
	}
	

	@RequestMapping(value = "/listqa")
	public String listqa(HttpServletRequest request,HttpServletResponse response,ClientQuestion question){
		return "/jsp/website/listqa";
	}
	@RequestMapping(value = "/showHotline")
	public String showHotline(HttpServletRequest request, HttpServletResponse response) {
		
		return "/jsp/website/showHotline";

	}
//	@RequestMapping(value = "/detail")
//	public void getQuestionDetail(HttpServletRequest request, HttpServletResponse response){
//		try {
//			String id = StringUtil.safeToString(request.getParameter("cid"), "");
//	System.out.println(id);
//			int qid = Integer.valueOf(id);
//			List<Map> list = clientquestionservice.getDetailCQ(qid);
//			PrintWriter out = response.getWriter();
//			JSONArray json = new JSONArray();
//			json.addAll(list);
//			out.println(json);
//			out.flush();
//			out.close();
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@RequestMapping(value="/remove")
	public String deleteQuestion(HttpServletRequest request, HttpServletResponse response){
		clientquestionservice.removeQCByID(request.getParameter("id"));
		return "redirect:/clientquestion/home";
	}
	
	
}
