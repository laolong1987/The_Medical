package com.web.controller.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.StringUtil;
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Org_employee;
import com.web.service.BonusService;
import com.web.service.EmployeeService;

@Controller
@RequestMapping("/bonus")
public class UserBonusController {
	private static final Log log = LogFactory.getLog(UserBonusController.class);

	@Autowired
	public BonusService bonusService;
	@Autowired
	public EmployeeService employeeService;

	/**
	 * 查询奖金列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listbonus")
	public String listbonus(HttpServletRequest request,
			HttpServletResponse response) {
		String resultjsp = "/jsp/website/listbonus";
     
		String type = StringUtil.safeToString(request.getParameter("type"), "");
		String status = StringUtil.safeToString(request.getParameter("status"),
				"");		
		//String adjustment = StringUtil.safeToString(request.getParameter("adjustment"), "");
		
		String batch = StringUtil.safeToString(request.getParameter("batch"),
				"");
//		if(!"".equals(adjustment)){
//			batch = bonusService.getMaxBatchCode().toString();
//		}
		String dealercode = StringUtil.safeToString(request
				.getParameter("dealercode"), "");
		String position = StringUtil.safeToString(request
				.getParameter("position"), "");
		String input_position = StringUtil.safeToString(request
				.getParameter("input_position"), "");
		Org_employee employee = (Org_employee) request.getSession().getAttribute("employee");
		
		String loggedPosition = StringUtil.safeToString(employee.getPosition(), "");
		
		Map emp = (Map) request.getSession().getAttribute("emp");

		Map<String, String> map = new HashMap<String, String>();
		List<Map> bList= bonusService.searchBatchs(new HashMap());
//		if(!"".equals(adjustment)){
//			resultjsp = "/jsp/website/adjustbonus";
//		}else 
		if (!"".equals(dealercode)) {
			map.put("dealercode", dealercode);
			resultjsp = "/jsp/website/listbonuszjl";
		}else {
			// map.put("empid",employeeService.getEmpid(request));
			map.put("ewid", StringUtil.safeToString(emp.get("ewid"), ""));
		}
		map.put("type", type);
		map.put("status", status);
		map.put("batch", batch);
		map.put("loggedPosition", loggedPosition);
		map.put("orderby",StringUtil.safeToString(emp.get("dealercode"), ""));
		if ("其他".equals(position)) {
			map.put("position", input_position);
		}
		else{
			map.put("position", position);
		}
		
		List<Map> list= bonusService.searchBonusdetail(map);
		
		Double zj=0.00;
		String btype = "";
		for(Map m:list){
			zj+=Double.parseDouble(StringUtil.safeToString(m.get("price"), "0.00"));
			String b=StringUtil.safeToString(m.get("batch"), "");
			btype = StringUtil.safeToString(m.get("type"), "");
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		request.setAttribute("list", list);
		request.setAttribute("zj", df.format(zj));
		request.setAttribute("status", status);
		request.setAttribute("type", type);
		request.setAttribute("btype", btype);
		request.setAttribute("batch", batch);
		request.setAttribute("dealercode", dealercode);
		request.setAttribute("position", position);
		request.setAttribute("input_position", input_position);
		request.setAttribute("blist", bList);
		
		return resultjsp;
	}

	/**
	 * 查询奖金排名
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listbonusrank")
	public String listbonusrank(HttpServletRequest request,
			HttpServletResponse response) {

		return "/jsp/website/listbonusrank";
	}

	/**
	 * 查询奖金发放
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listbonusprovide")
	public String listbonusprovide(HttpServletRequest request,
			HttpServletResponse response) {
		String resultjsp = "/jsp/website/listbonusprovide";
		String dealercode = StringUtil.safeToString(request
				.getParameter("dealercode"), "");
		
		System.out.println("dealercode"+dealercode);
		
		request.setAttribute("dealercode", dealercode);

		String input_position = StringUtil.safeToString(request
				.getParameter("input_position"), "");
		String position = StringUtil.safeToString(request
				.getParameter("position"), "");

		String id=StringUtil.safeToString(request.getParameter("id"), "");
		String type = StringUtil.safeToString(request.getParameter("type"), "");
		String status = StringUtil.safeToString(request.getParameter("status"),
				"");
		String batch = StringUtil.safeToString(request.getParameter("batch"),
				"");
		Map<String, String> map = new HashMap<String, String>();
		Map emp = (Map) request.getSession().getAttribute("emp");

		if (!"".equals(dealercode)) {
			resultjsp = "/jsp/website/listbonusprovidezjl";
		} else {
			// map.put("empid",employeeService.getEmpid(request));
			map.put("ewid", StringUtil.safeToString(emp.get("ewid"), ""));
		}
		Org_employee annemp = (Org_employee) request.getSession().getAttribute("employee");
		String loggedPosition = annemp.getPosition();
		map.put("loggedPosition", loggedPosition);
		map.put("type", type);
		map.put("status", status);
		map.put("batch", batch);
		map.put("dealercode", dealercode);
		map.put("orderby",StringUtil.safeToString(emp.get("dealercode"), ""));
		if ("其他".equals(position)) {
			map.put("position", input_position);
		}
		else{
			map.put("position", position);
		}
		
		if(!"".equals(id)){
			Bo_bonusdetail bonusdetail=	bonusService.getBonusdetailById(Integer.parseInt(id));
			status=StringUtil.safeToString(bonusdetail.getStatus(), "");
			type=StringUtil.safeToString(bonusdetail.getType(), "");
			batch=StringUtil.safeToString(bonusdetail.getBatch(), "");
			
//			Org_employee employee= employeeService.getEmployeeByEwid(bonusdetail.getEwid());
			
			if("总经理".equals(bonusdetail.getPosition()) || "销售经理".equals(bonusdetail.getPosition()) ||
					"销售顾问".equals(bonusdetail.getPosition()) || "延保操作员".equals(bonusdetail.getPosition())){
				position=bonusdetail.getPosition();
			}else{
				position="其他";
				input_position=bonusdetail.getPosition();
			}
			
			map.put("type", type);
			map.put("status", status);
			map.put("batch", batch);
			map.put("dealercode", bonusdetail.getDealercode());
			map.put("position", bonusdetail.getPosition());
		}
		
//		map.put("id", id);
		List<Map> list= bonusService.searchBonusdetail(map);

		Double zj1=0.00;
		Double tzj1=0.00;
		Double rzj1=0.00;

		Double zj2=0.00;
		Double tzj2=0.00;
		Double rzj2=0.00;

		Double zj3=0.00;
		Double tzj3=0.00;
		Double rzj3=0.00;
		
		List<Map> list1=new ArrayList<Map>();
		List<Map> list2=new ArrayList<Map>();
		for(Map m:list){
			String s=StringUtil.safeToString(m.get("status"), "0.00");
			if("0".equals(s)){
				zj2+=Double.parseDouble(StringUtil.safeToString(m.get("price"), "0.00"));
				tzj2+=Double.parseDouble(StringUtil.safeToString(m.get("taxprice"), "0.00"));
				rzj2+=Double.parseDouble(StringUtil.safeToString(m.get("realizeprice"), "0.00"));
				list2.add(m);
			}else if("1".equals(s)){
				zj1+=Double.parseDouble(StringUtil.safeToString(m.get("price"), "0.00"));
				tzj1+=Double.parseDouble(StringUtil.safeToString(m.get("taxprice"), "0.00"));
				rzj1+=Double.parseDouble(StringUtil.safeToString(m.get("realizeprice"), "0.00"));
				list1.add(m);
			}
			zj3+=Double.parseDouble(StringUtil.safeToString(m.get("price"), "0.00"));
			tzj3+=Double.parseDouble(StringUtil.safeToString(m.get("taxprice"), "0.00"));
			rzj3+=Double.parseDouble(StringUtil.safeToString(m.get("realizeprice"), "0.00"));
			
			
		}
		DecimalFormat  df   = new DecimalFormat("######0.00");   

		
		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);

		request.setAttribute("zj1", df.format(zj1));
		request.setAttribute("zj2", df.format(zj2));
		request.setAttribute("zj3", df.format(zj3));
		request.setAttribute("tzj1", df.format(tzj1));
		request.setAttribute("tzj2", df.format(tzj2));
		request.setAttribute("tzj3", df.format(tzj3));
		request.setAttribute("rzj1", df.format(rzj1));
		request.setAttribute("rzj2", df.format(rzj2));
		request.setAttribute("rzj3", df.format(rzj3));
		
		request.setAttribute("status", status);
		request.setAttribute("type", type);
		request.setAttribute("batch", batch);
		request.setAttribute("dealercode", dealercode);

		request.setAttribute("position", position);
		request.setAttribute("input_position", input_position);
		List<Map> bList= bonusService.searchBatchs(new HashMap());
		request.setAttribute("blist", bList);
		return resultjsp;
	}

	/**
	 * 查询销量明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listsales")
	public String listsales(HttpServletRequest request,
			HttpServletResponse response) {

		return "/jsp/website/listsales";
	}
	/**
	 * 总经理调整金额
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/adjustbonus")
	public void adjustBonus(HttpServletRequest request,
			HttpServletResponse response){		
		
		String ids = StringUtil.safeToString(request.getParameter("ids"),"");
		String ewid = StringUtil.safeToString(request.getParameter("chewid"), "");
		String idcard = StringUtil.safeToString(request.getParameter("chidcard"), "");
		String name = StringUtil.safeToString(request.getParameter("chname"), "");
		String dossid = StringUtil.safeToString(request.getParameter("chdossid"),"");
		String realPosition = StringUtil.safeToString(request.getParameter("realposition"),"");
		Org_employee employee = (Org_employee) request.getSession().getAttribute("employee");
		String dealerCode = StringUtil.safeToString(employee.getDealercode(), "");
		
		Map para = new HashMap();		
		String msg = "";
		String error = "";
		Org_employee emp = null;
		para.put("ids", ids);
		para.put("dealercode", dealerCode);
		if(!"".equals(ewid)){
			emp = employeeService.getEmployeeByEwid(ewid);
			if(emp != null){
				para.put("ewid", emp.getEwid());
				msg = bonusService.changeBonus(para);
			}else{
				error = "您所输入的ewid不存在，请重新输入！";				
			}
			
		}else if(!"".equals(dossid)){
			emp = employeeService.getEmployeeByDossid(dossid);
			if(emp != null){
				para.put("ewid", emp.getEwid());
				msg = bonusService.changeBonus(para);
			}else{
				error = "您所输入的DOSS ID 不存在，请重新输入！";
				request.getSession().setAttribute("msg", msg);
			}
			
		}else{
			para.put("name", name);
			para.put("idcard", idcard);
			para.put("position", realPosition);
			emp = employeeService.checkUserIsexit(para);
			if(emp != null){
				para.put("ewid", emp.getEwid());
				msg = bonusService.changeBonus(para);
			}else{
				error = "您要转的对象不存在，请您确认输入的信息是否正确！";
				//request.getSession().setAttribute("msg", msg);
			}
		}
		
		//根据已有 criteria检索数据并返回数据
		try {
			PrintWriter out = response.getWriter();
			JSONArray json = new JSONArray();
			//json.addAll(0,stars);
			if("奖金调整成功！".equals(msg)){
			    json.add(msg);
			}else{
				json.add(error);
			}
			//json.add(1, msg);
			out.println(json);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Confirm completed adjustment bonus list
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/confirm")
	public void confirmAdjust(HttpServletRequest request,
			HttpServletResponse response){
		String dealercode = request.getParameter("dealercode");
		String batch = request.getParameter("batch");
		String ids = request.getParameter("changed_id");
		Map map = new HashMap();
		map.put("dealercode", dealercode);
		map.put("batch", batch);
		map.put("ids", ids);
	    String msg = bonusService.confirmBonus(map);
//	    //根据已有 criteria检索数据并返回数据
		try {
			PrintWriter out = response.getWriter();
			JSONArray json = new JSONArray();
			//json.addAll(0,stars);
			json.add(msg);
			//json.add(1, msg);
			out.println(json);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Cancel adjustment confirmation 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/cancel")
	public void cancelConfirm(HttpServletRequest request,
			HttpServletResponse response){
		String dealercode = request.getParameter("dealercode");
		String batch = request.getParameter("batch");
		String ids = request.getParameter("changed_id");
		Map map = new HashMap();
		map.put("dealercode", dealercode);
		map.put("batch", batch);
		map.put("ids", ids);
	    String msg = bonusService.cancelConfirmBonus(map);
//	    //根据已有 criteria检索数据并返回数据
		try {
			PrintWriter out = response.getWriter();
			JSONArray json = new JSONArray();
			//json.addAll(0,stars);
			json.add(msg);
			//json.add(1, msg);
			out.println(json);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Get employee object
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getemp")
	public void getEmployee(HttpServletRequest request, 
			HttpServletResponse response){
		String ewid = StringUtil.safeToString(request.getParameter("ewid"), "");
		String dossid = StringUtil.safeToString(request.getParameter("dossid"), "");
		Org_employee emp = (Org_employee) request.getSession().getAttribute("employee");
		String dealercode = emp.getDealercode();
		Map map = new HashMap();
		map.put("dealercode", dealercode);
		Org_employee employee = null;
		String msg = "";
		if(!"".equals(ewid)){
			map.put("ewid", ewid);
			employee = employeeService.checkUserIsexit(map);
			if(employee == null){
				msg = "1";
			}
		}else if(!"".equals(dossid)){
			map.put("doss_id", dossid);
			employee = employeeService.checkUserIsexit(map);
			if(employee == null){
				msg = "2";
			}
		}else{
			msg = "3";
		}

		try {
			PrintWriter out = response.getWriter();
			JSONArray json = new JSONArray();
			
			if(employee != null){
				json.add(employee);
			}else{
				json.add(msg);
			}			
			//json.add(1, msg);
			out.println(json);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Get potential employee list
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getpotentialemp")
	public void getpotentialemp(HttpServletRequest request,
			HttpServletResponse response){
		//Get parameters
		String empname = StringUtil.safeToString(request.getParameter("empname"), "");
		String empidcard = StringUtil.safeToString(request.getParameter("empidcard"), "");
		
		//Get dealercode
		Org_employee emp = (Org_employee) request.getSession().getAttribute("employee");
		String dealercode = emp.getDealercode();
		
		//Encapsulation parameters
		Map map = new HashMap();
		map.put("dealercode", dealercode);
		map.put("name", empname);
		map.put("idcard", empidcard);
		
		//Set search result.
		String msg = "";
		
		//Get employee list
		List<Org_employee> emplist = null;
		if(!"".equals(empname)){			
			emplist = employeeService.searchEmployee(map);
			if(emplist.size() <= 0){
				msg = "1";
			}
		}else if(!"".equals(empidcard)){
			emplist = employeeService.searchEmployee(map);
			if(emplist.size() <= 0){
				msg = "2";
			}
		}else{
			msg = "3";
		}
		
		try {
			PrintWriter out = response.getWriter();
			JSONArray json = new JSONArray();
			
			if(emplist.size() > 0){
				json.add(emplist);
			}else{
				json.add(msg);
			}			
			//json.add(1, msg);
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * list adjusted bonus list
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/listadjustbonus")
	public String listAdjustBonus(HttpServletRequest request,
			HttpServletResponse response){
		
		String type = StringUtil.safeToString(request.getParameter("type"), "");
			
		//String adjustment = StringUtil.safeToString(request.getParameter("adjustment"), "");
		
		String batch = request.getParameter("adjustBatch");
		//if(!"".equals(adjustment)){
		if(batch == null){
			batch = bonusService.getMaxBatchCode().toString();
		}
		//}
		
		Org_employee emp = (Org_employee) request.getSession().getAttribute("employee");
		String dealercode = StringUtil.safeToString(emp.getDealercode(), "");

		String loggedPosition = StringUtil.safeToString(emp.getPosition(), "");
		
		String position = StringUtil.safeToString(request
				.getParameter("adjusPosition"), "");

		String ewid = StringUtil.safeToString(request
				.getParameter("ewid"), "");
		
		String dossid = StringUtil.safeToString(request
				.getParameter("adjustdossid"), "");
		
		String idcard = StringUtil.safeToString(request
				.getParameter("adjustidcard"), "");
		
		Map<String, String> map = new HashMap<String, String>();
		List<Map> bList= bonusService.searchBatchs(new HashMap());
		

		map.put("type", type);
		map.put("idcard", idcard);
		map.put("dossid", dossid);
		map.put("dealercode", dealercode);
		//map.put("realposition", realposition);
		map.put("loggedPosition", loggedPosition);
		map.put("position", position);
		map.put("ewid", ewid);
		map.put("batch", batch);
		map.put("adjust", "adjust");
		
		List<Map> list= bonusService.searchBonusdetail(map);
		
		Double zj=0.00;
		String btype = "";
		for(Map m:list){
			zj+=Double.parseDouble(StringUtil.safeToString(m.get("price"), "0.00"));
			String b=StringUtil.safeToString(m.get("batch"), "");
			btype = StringUtil.safeToString(m.get("type"), "");
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		request.setAttribute("list", list);
		request.setAttribute("listSize", list.size());
		request.setAttribute("zj", df.format(zj));
//		request.setAttribute("status", status);
		request.setAttribute("type", type);
		request.setAttribute("btype", btype);
		request.setAttribute("batch", batch);
		request.setAttribute("dealercode", dealercode);
		request.setAttribute("position", position);
		request.setAttribute("dossid", dossid);
		request.setAttribute("idcard", idcard);
		request.setAttribute("ewid", ewid);
		//request.setAttribute("input_position", input_position);
		request.setAttribute("blist", bList);
		String msg = (String) request.getSession().getAttribute("msg");
		request.setAttribute("msg",msg);
		return "/jsp/website/adjustbonus";
		
	}
	
	
}
