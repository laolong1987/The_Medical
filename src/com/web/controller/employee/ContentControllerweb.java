package com.web.controller.employee;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.StringUtil;
import com.web.entity.Doc_news;
import com.web.entity.Recordlog;
import com.web.service.BonusService;
import com.web.service.ContentService;



@Controller
@RequestMapping("/content")
public class ContentControllerweb {
	//private static final Log log = LogFactory.getLog(HomeController.class);
	@Autowired
	public ContentService contentService;
	@Autowired
	public BonusService bonusService;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listcontentwed")
	public String contentlistcontent(HttpServletRequest request, HttpServletResponse response) {
	    String type=request.getParameter("type");
	//	String type=StringUtil.safeToString(request.getSession().getAttribute("type"), "");

		
		
		
		
		
		Map<String, String> map=new HashMap<String, String>();
		Map emp =(Map)request.getSession().getAttribute("emp");
	  	String brand=(String) emp.get("brand");
	  	
	 	String dealercode=(String) emp.get("dealercode");
	  	String  regionname =bonusService.getRegionnameByDealerCode(dealercode);
		String  regionname2=bonusService.getRegionnameByDealerCodeyb(dealercode);
		map.put("regionname2", regionname2);
	  	map.put("regionname", regionname);
		map.put("brand", brand);
		map.put("type", type);

		
		

		  String totalRows = StringUtil.safeToString(contentService.getdocCount(map).get("totalCount"),"");
	        String page = StringUtil.safeToString(request.getParameter("page"), "");        
	        int intTotalRows = Integer.valueOf(totalRows);
	        if("" == page){
	            page = "1";
	        }
	        int intPage = Integer.valueOf(page);
	        int pageSize = 10;
	        int totalPage = 0;
	        if(intTotalRows%pageSize == 0){
	        	totalPage = intTotalRows/pageSize;
	        }else{
	        	totalPage = intTotalRows/pageSize + 1;
	        }
	        
	        int start = pageSize*(intPage - 1);
	        int maxResult = 10;
		
	       // map.put("empid", empid);
	        map.put("start", String.valueOf(start));
	        map.put("maxResult", String.valueOf(maxResult));
		
		List<Map> list = contentService.listAlldocnews(map);
		request.setAttribute("list", list);
		request.setAttribute("type", type);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("totalRows",totalRows);
		request.setAttribute("listPage", intPage);
//		System.out.println(start);
//		System.out.println(maxResult);
//		System.out.println(intPage);
		return "/jsp/website/news_doc";

	}
	


	@RequestMapping(value = "/showcontent")
	public String showcountent(HttpServletRequest request, HttpServletResponse response) {
      String id=request.getParameter("id");
      Doc_news doc_news= contentService.getcontentInfoById(Integer.parseInt(id));
      
      
  	Map emp =(Map)request.getSession().getAttribute("emp");
  	String ewid=(String) emp.get("ewid");
  	String username=(String) emp.get("username");
   
 	Recordlog recordlog =new Recordlog(); 

	recordlog.setEwid(ewid);
	recordlog.setGgid(Integer.parseInt(id));
	recordlog.setName(doc_news.getName());
	recordlog.setUsername(username);
	recordlog.setSysdate(new Date());
	contentService.saverizhifo(recordlog);
//	Map<String, String> p=new HashMap<String, String>();
//	Recordlog recordlog =new Recordlog(); 
//	r
//	p.put("ewid", ewid);
//	p.put("username", username);
//	p.put("ggid", id);
//	p.put("name", docNews.getName());
//	p.put("sysdate", TimeUtil.getSysDate());
//	          
	
      if(null!=doc_news){
    	  
    	  request.setAttribute("utype", doc_news.getUtype());
    	  request.setAttribute("content", doc_news.getContent());
      }
      
		return "/jsp/website/showcontent";

	}

}
