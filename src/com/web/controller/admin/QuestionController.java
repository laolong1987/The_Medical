package com.web.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utils.DateUtil;
import com.utils.JxlUtil;
import com.utils.LigerUtils;
import com.utils.StringUtil;
import com.utils.TimeUtil;

import com.web.service.ClientQuestionService;
import com.web.entity.ClientQuestion;
import com.web.entity.Replyclient;


@Controller
@RequestMapping("/admin/client")
public class QuestionController {
	private static final Log log = LogFactory.getLog(QuestionController.class);


	@Autowired
	public ClientQuestionService clientquestionservice;
	
	/**
	 * 查询奖金列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/clientqing")
	public String listbonus(HttpServletRequest request, HttpServletResponse response) {
		
		return "/jsp/proprietor/listclient";
	}

	/**
	 * 奖金列表控件调用的方法
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchclient", method = RequestMethod.POST)
	public void searchbonus(HttpServletRequest request,
			HttpServletResponse response) {
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");
		
		try {
			String title= StringUtil.safeToString(request.getParameter("title"),"");
			String type = StringUtil.safeToString(request.getParameter("type"),"");
			String state = StringUtil.safeToString(request.getParameter("state"),"");
			String empname = StringUtil.safeToString(request.getParameter("empname"), "");
			Map<String, 
			String> p = new HashMap<String, String>();
            p.put("title", title);
            p.put("type", type);
            p.put("state", state);
            p.put("empname", empname);
			p.put("firstrs", String.valueOf(Integer.valueOf(page)
					* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			List<Map> emp = clientquestionservice.searchclient(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, clientquestionservice
					.countclient(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/sevaclient")
	public void sevaclient(HttpServletRequest request,
			HttpServletResponse response) {
		String id = StringUtil.safeToString(request.getParameter("id"), "");
		String rid = StringUtil.safeToString(request.getParameter("rid"), "");
		String title = StringUtil.safeToString(request.getParameter("stitle"), "");
		String status =StringUtil.safeToString(request.getParameter("status"), "");
		String type =StringUtil.safeToString(request.getParameter("stype"), "");
		String countent =StringUtil.safeToString(request.getParameter("countent"), "");
		String repcountent =StringUtil.safeToString(request.getParameter("repcountent"), "");
		
		ClientQuestion clientQuestion=new ClientQuestion();
		
		if(!"".equals(id) && null != id){
			clientQuestion=	clientquestionservice.getclientInfoById(Integer.parseInt(id));			
		}
		clientQuestion.setStatus(1);
		clientquestionservice.saveQuetions(clientQuestion);
		Replyclient replyclient=new Replyclient();
		
		if(!"".equals(rid) && !("null".equals(rid)) && null!= rid ){
			replyclient=clientquestionservice.getclientInfoByhfId(Integer.parseInt(rid));			
		}

		replyclient.setRepcountent(repcountent);
		replyclient.setHfid(Integer.parseInt(id));	
		if("0".equals(status)){
			replyclient.setCreatetime(new Date());
			replyclient.setUpdatetime(new Date());
		}else{
			replyclient.setUpdatetime(new Date());
		}		
		clientquestionservice.saveHclient(replyclient);
		try {
			PrintWriter out = response.getWriter();
			out.println("保存成功");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	@RequestMapping (value = "/exportQuestions")
	public void exportQuestions(HttpServletRequest request,
			HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("/")
		+ "template" + "/";
		String f1 = path + "questions_list.xls";

		String title = StringUtil.safeToString(request
				.getParameter("title"), "");
		String type = StringUtil.safeToString(request
				.getParameter("type"), "");		
//		String status = new String (request
//				.getParameter("input_position").getBytes("iso-8859-1"), "UTF-8") ; 
		String status = StringUtil.safeToString(request.
				getParameter("state"),
				""); 	
		String empname = StringUtil.safeToString(request.
				getParameter("empname"),
		""); 
		String d = DateUtil.getcurrentDatetime("yyyyMMdd");
		String f2 = path + d + "_Questions.xls";
		Map para = new HashMap();
		para.put("title", title);
		para.put("status", status);	
		para.put("type", type);
		para.put("empname", empname);
		JxlUtil.writeExcel(f1, f2, clientquestionservice.exportQuestions(para), 0, 1);
		String fileName = "questions_list_" + d + ".xls";
		// 当前文件路径 
		String nowPath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "template" + "/" + d + "_Questions.xls";
		response.setContentType("application/vnd.ms-excel");
		File file = new File(nowPath);
		
		// 清空response
		response.reset();
		
		// 设置response的Header
		
		
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gbk"), "iso-8859-1")); // 转码之后下载的文件不会出现中文乱码
			response.addHeader("Content-Length", "" + file.length());
			// 以流的形式下载文件
			InputStream fis = new BufferedInputStream(new FileInputStream(
					nowPath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
		
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
//	/**
//	 *删除
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/removebonus", method = RequestMethod.POST)
//	public void removebonus(HttpServletRequest request,
//			HttpServletResponse response) {
//		String ids=request.getParameter("ids");
//
//		String[] idss=ids.split(",");
//		for(int i=0;i<idss.length;i++){
//			bonusService.delBonusById(idss[i]);
//		}
//	}
}
