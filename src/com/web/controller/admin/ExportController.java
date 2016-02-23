package com.web.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.utils.DateUtil;
import com.utils.JxlUtil;
import com.utils.LigerUtils;
import com.utils.SettingUtil;
import com.utils.StringUtil;

import com.web.entity.Admin;
import com.web.entity.Org_employee;
import com.web.entity.UserLockInfo;
import com.web.service.AdminService;
import com.web.service.EmployeeService;
import com.web.service.ExportService;




@Controller
@RequestMapping("admin/export")
public class ExportController {
	private static final Log log = LogFactory.getLog(ExportController.class);
	
	@Autowired
	public ExportService exportService;
	
	@Autowired
	public EmployeeService empService;
	/**
	 * 显示所有人员列表
	 * 
	 */
	@RequestMapping(value = "/listemployee")
	public String showEmployee(HttpServletRequest request,
			HttpServletResponse response) {
		return "/jsp/proprietor/listemployee";
	}
	
	@RequestMapping (value = "/searchemp")
	public void searchEMP(HttpServletRequest request,
			HttpServletResponse response){
		String brands = request.getParameter("brands");		
		String dealercode = StringUtil.safeToString(request
				.getParameter("dealercode"), "");
		String position = StringUtil.safeToString(request
				.getParameter("position"), "");
		String idCard = StringUtil.safeToString(request.getParameter("idCard"),
				"");
		String dossId = StringUtil.safeToString(request.getParameter("dossId"),
		"");
		String isActived = StringUtil.safeToString(request.getParameter("isactived"), "");
		String empname = StringUtil.safeToString(request.getParameter("empname"), "");
		String region = StringUtil.safeToString(request.getParameter("region"), "");
		
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");
		String start_date = StringUtil.safeToString(request
				.getParameter("start_date"), "");
		String end_date = StringUtil.safeToString(request
				.getParameter("end_date"), "");

		
		try {
		
			Map<String, String> p = new HashMap<String, String>();
		    p.put("brands", brands);
			p.put("dealercode", dealercode);
			p.put("isActived", isActived);
			p.put("position", position);
			p.put("idCard", idCard);
			p.put("dossId", dossId);
			p.put("empname", empname);
			p.put("region", region);
			p.put("begintime", start_date);
			p.put("endtime", end_date);
			p.put("firstrs", String.valueOf(Integer.valueOf(page)* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			List<Map> emp = exportService.getAllEmpoyees(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, exportService
					.searchPeopleInfoCount(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/exportdata")
	public void exportData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("导出数据");
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "template" + "/";
		String f1 = path + "template.xls";
		
		//String brands = StringUtil.safeToString(request.getParameter("brands"), "");
		String brands[] = request.getParameterValues("brand");
		String brand="";
		if( !(null == brands)){
		for(String obj:brands){
			if(brand.length()>0){
				brand+=",";
			}
			brand+= "'" + obj + "'";
		}
		}
		String dealercode = StringUtil.safeToString(request
				.getParameter("dealercode"), "");
		String position = StringUtil.safeToString(request
				.getParameter("position"), "");		
		String inputPosition = new String (request
				.getParameter("input_position").getBytes("iso-8859-1"), "UTF-8") ; 
		String idCard = StringUtil.safeToString(request.getParameter("idCard"),
				""); 
		String dossId = StringUtil.safeToString(request.getParameter("dossId"),
		"");
		String isActived = StringUtil.safeToString(request.getParameter("isactived"), "");
		String region = StringUtil.safeToString(request.getParameter("region"), "");
		String d = DateUtil.getcurrentDatetime("yyyyMMdd");
		String f2 = path + d + ".xls";
		Map para = new HashMap();
		para.put("brand", brand);
		para.put("dealercode", dealercode);	
		if("0".equals(position)){
			para.put("position", "总经理");
		}
		if("1".equals(position)){
			para.put("position", "销售经理");
		}
		if("2".equals(position)){
			para.put("position", "销售顾问");
		}
		if("3".equals(position)){
			para.put("position", "延保操作员");
		}
		if("4".equals(position)){
			para.put("position", inputPosition);
		}
		para.put("idCard", idCard);		
		para.put("dossId", dossId);
		para.put("isActived", isActived);
		para.put("region", region);
		para.put("input_position", inputPosition);
		
//		// 如果登陆的不是管理员 查询条件固定经销商
//		String username = StringUtil.safeToString(request.getSession()
//				.getAttribute("username"), "");
//		if (!"admin".equals(username)) {
//			para.put("dealercode", username);
//		}
		JxlUtil.writeExcel(f1, f2, exportService.searchshsgm(para), 0, 1);
		String fileName = "employees_list_"+d + ".xls";
		// 当前文件路径 
		String nowPath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "template" + "/" + d + ".xls";
		response.setContentType("application/vnd.ms-excel");
		File file = new File(nowPath);

		// 清空response
		response.reset();

		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("gbk"), "iso-8859-1")); // 转码之后下载的文件不会出现中文乱码
		response.addHeader("Content-Length", "" + file.length());

		try {
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
	
	@RequestMapping (value="showlockusers")
	public String listLockingUsers(HttpServletRequest request,
			HttpServletResponse response){		
		return "/jsp/proprietor/lockusers";
	}
	@RequestMapping (value="getlockingusers")
	public void getLockingUsers(HttpServletRequest request,
			HttpServletResponse response){
		try {
			String dossId = StringUtil.safeToString(request.getParameter("dossid"),
			"");
			String isActived = StringUtil.safeToString(request.getParameter("isactived"), "0,1");
			String name = StringUtil.safeToString(request.getParameter("name"), "");
			String page = StringUtil.safeToString(request.getParameter("page"), "");
			String pagesize = StringUtil.safeToString(request
					.getParameter("pagesize"), "");
			Map p = new HashMap();
			p.put("name", name);
			p.put("doss_id", dossId);
			p.put("isactived",isActived);
			p.put("page", page);
			p.put("pagesize",pagesize);
			List<Map> emp = exportService.getLockingUsers(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, exportService
					.getLockingUsersCount(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping (value="lockuser")
	public String lockUsers(HttpServletRequest request,
			HttpServletResponse response){
		String json = StringUtil.safeToString(request.getParameter("json"), "");
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<Map> list = (List) JSONArray.toCollection(jsonArray, Map.class);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lockReason = "";
        String dimission_time = null;
        int lockType = 0;
        String dis_status = "";
        String service_status = "";
        UserLockInfo ulInfo = null;
		for(Map map:list){
			String ewid = StringUtil.safeToString(map.get("ewid"), "");
			dis_status = StringUtil.safeToString(map.get("dis_status"),"");
            dimission_time = StringUtil.safeToString(map.get("dimission_time"),"");
            service_status = StringUtil.safeToString(map.get("inservice"),"");
			Org_employee emp = empService.getEmployeeByEwid(ewid);
			emp.setIsactived(2);
			emp.setUpdatetime(date);
			empService.saveEmployee(emp);
			ulInfo = exportService.getUserLockInfoByEwid(ewid);
			if(ulInfo != null){
			    ulInfo.setLockDate(sdf.format(date));
                if("0".equals(dis_status)){
                    lockReason = "此人所在的店为待上线，暂时锁定该人员信息";
                }else if("2".equals(dis_status)){
                    lockReason = "此人所在的店已停业，锁定该人员信息";
                }else if("3".equals(dis_status)){
                    lockReason = "此人所在的店已下线，锁定该人员信息";
                }
                if(!"".equals(dimission_time) && !"".equals(service_status) ){
                    lockReason = "此人的就业状态为:"+service_status+"，锁定该人员信息";
                    //Lock emp login account
                    lockType = 1;
                }
                ulInfo.setReason(lockReason);
                ulInfo.setLockType(lockType);
                exportService.saveUserLockInfo(ulInfo);
			}else{
    			UserLockInfo ulLockInfo = new UserLockInfo();
    			ulLockInfo.setEwid(ewid);
    			ulLockInfo.setLockDate(sdf.format(date));
    			if("0".equals(dis_status)){
                    lockReason = "此人所在的店为待上线，暂时锁定该人员信息";
                }else if("2".equals(dis_status)){
                    lockReason = "此人所在的店已停业，锁定该人员信息";
                }else if("3".equals(dis_status)){
                    lockReason = "此人所在的店已下线，锁定该人员信息";
                }
                if(!"".equals(dimission_time) && !"".equals(service_status) ){
                    lockReason = "此人的就业状态为:"+service_status+"，锁定该人员信息";
                    //Lock emp login account
                    lockType = 1;
                }
    			ulLockInfo.setReason(lockReason);
    			ulLockInfo.setLockType(lockType);
    			exportService.saveUserLockInfo(ulLockInfo);   			
			}			
		}
		
		return "redirect:showlockusers";
	}
	@RequestMapping(value="/unlockusers")
	public void unLockUsers(HttpServletRequest request,
            HttpServletResponse response){
	    String json = StringUtil.safeToString(request.getParameter("json"), "");
	    String unLockReason = StringUtil.safeToString(request.getParameter("value"), "");
        JSONArray jsonArray = JSONArray.fromObject(json);
        List<Map> list = (List) JSONArray.toCollection(jsonArray, Map.class);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dimission_time = null;
        int lockType = 0;
        String dis_status = "";
        String service_status = "";
        String tip = "";
        String ewid = "";
        for(Map map:list){
            ewid = StringUtil.safeToString(map.get("ewid"), "");
            dis_status = StringUtil.safeToString(map.get("dis_status"),"");
            dimission_time = StringUtil.safeToString(map.get("dimission_time"),"");
            service_status = StringUtil.safeToString(map.get("inservice"),"");
            Org_employee emp = empService.getEmployeeByEwid(ewid);
            UserLockInfo ulInfo = exportService.getUserLockInfoByEwid(ewid);
            if(!"草稿".equals(service_status) && !"注销申请中".equals(service_status)&&
                    !"冻结".equals(service_status) && !"离职".equals(service_status)){
                //lockReason = "此人账号因为经销商上线由锁定转为激活";
                emp.setIsactived(1);
                emp.setUpdatetime(date);
                UserLockInfo ulLockInfo = new UserLockInfo();
                ulLockInfo.setEwid(ewid);
                ulLockInfo.setUnlockDate(sdf.format(date));
                if(ulInfo != null){
                    ulLockInfo.setLockDate(ulInfo.getLockDate());
                }                
                ulLockInfo.setReason(unLockReason);
                ulLockInfo.setLockType(3);
                exportService.saveUserLockInfo(ulLockInfo);
                
                empService.saveEmployee(emp);
            } else{
                tip="0";
            }                      
        }
        try {
            Map rmap=new HashMap();
            rmap.put("data", tip);
            rmap.put("ewid", ewid);
            rmap.put("service_status", service_status);
            JSONObject resultJSON = JSONObject.fromObject(rmap);
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
