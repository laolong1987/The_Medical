package com.web.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.ietf.jgss.Oid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.SingletonClient;
import com.utils.LigerUtils;
import com.utils.StringUtil;
import com.web.entity.Apply_role;
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Modify_name;
import com.web.entity.Org_employee;
import com.web.entity.UserLockInfo;
import com.web.service.ApproveService;
import com.web.service.BonusService;
import com.web.service.EmployeeService;
import com.web.service.ExportService;


@Controller
@RequestMapping(value="admin/approve")
public class ApproveController {
	
	@Autowired
	public ApproveService approveService;
	@Autowired
	public EmployeeService  empService;
	@Autowired
	public BonusService bonusService;
	
	@Autowired
	public ExportService exportService;

	@RequestMapping(value = "/approvemf")
    public String approveMF(HttpServletRequest request,
    		HttpServletResponse response){	
    	return "/jsp/proprietor/approvemodf";
    }
	
	@RequestMapping(value = "/searchappvlt")	
	public void searchApprovalist(HttpServletRequest request, 
			HttpServletResponse response){
		try {			
			String page = StringUtil.safeToString(request.getParameter("page"), "");
			String pagesize = StringUtil.safeToString(request
					.getParameter("pagesize"), "");
			String isApproved = StringUtil.safeToString(request.getParameter("isapproved"),"0");
			request.setAttribute("isapproved", isApproved);
			String dossid = StringUtil.safeToString(request.getParameter("dossid"),"");
			String orgname = StringUtil.safeToString(request.getParameter("orgname"),"");
			
			String start_date = StringUtil.safeToString(request
					.getParameter("start_date"), "");
			String end_date = StringUtil.safeToString(request
					.getParameter("end_date"), "");
			
			Map<String,String> map = new HashMap<String,String>();	
			map.put("page", page);
			map.put("pagesize", pagesize);
			map.put("isApproved", isApproved);
			map.put("dossid", dossid);
			map.put("orgname", orgname);
			map.put("begintime", start_date);
			map.put("endtime", end_date);
			List<Map> approval = approveService.getAllApprovalist(map);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(approval, approveService
					.searchApprovalCount(map)));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @RequestMapping(value="/approvelists")
	public String approveEmp(HttpServletRequest request, 
			HttpServletResponse response){
    	String json = StringUtil.safeToString(request.getParameter("json"), "");
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<Map> list = (List) JSONArray.toCollection(jsonArray, Map.class);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Org_employee oldEMP = null;
		for(Map map:list){
			
			String ewid=StringUtil.safeToString(map.get("ewId"),"");
			String afterEwid = StringUtil.safeToString(map.get("after_ewId"), "");
									
			String orgPosition = StringUtil.safeToString(map.get("before_position"),"");			
		    String afterPosition = StringUtil.safeToString(map.get("after_position"),"");
		    
			
			String orgDossid = StringUtil.safeToString(map.get("before_dossId"),"");			
		    String dossid = StringUtil.safeToString(map.get("after_dossId"),"");
		    
		    String orgName = StringUtil.safeToString(map.get("before_name"),"");
		    String afterName = StringUtil.safeToString(map.get("after_name"),"");
		    
		    String orgIdCard = StringUtil.safeToString(map.get("before_idcard"),"");
		    String afterIdCard = StringUtil.safeToString(map.get("after_idcard"),"");
		    String strInfoType = StringUtil.safeToString(map.get("info_type"),"0");
		    
		    int info_type = Integer.parseInt(strInfoType);
		    
		    //根据ewid获取org_employee对象
		    oldEMP = empService.getEmployeeByEwid(ewid);
		    Org_employee orgemp = null;		    
			Org_employee newEMP = null;
			String phone = "";		    		        
		    if(info_type == 1){//人员奖金转移到新的EWID上
			    if(!"".equals(afterEwid) && !ewid.equals(afterEwid)){
			    	String exbonusPhone = StringUtil.safeToString(map.get("exch_bonus_phone"),"");
			    	List<Bo_bonusdetail> bonuslist = bonusService.getBonusbyEWID(ewid);
			    	if(bonuslist.size() > 0){
				    	approveService.updateEMP(oldEMP);
				    	for(Bo_bonusdetail bonus:bonuslist){
				    		 bonus.setEwid(afterEwid);
				    		 bonus.setUpdatetime(date);
				    		 bonusService.saveOrUpdateBonus(bonus);
				    	}
				    	oldEMP.setUpdatetime(date);
				    	oldEMP.setIsactived(2);
				    	approveService.updateEMP(oldEMP);
				    	phone = exbonusPhone;
			    	}else{
			    	    request.setAttribute("message", "账号" + ewid + "没有奖金信息") ;
			    	}
			    } 
		    }else if(info_type == 0){		    	
		    	//人员信息变更
		    	if("未申请修改".equals(dossid)){//DOSS ID未变更的
			    	if(!"".equals(afterName)){
				    	oldEMP.setName(afterName);	
				    }		    			  
				    if(!"".equals(afterIdCard)){
				    	oldEMP.setIdcard(afterIdCard);
				    }
				    if(!"".equals(afterPosition)){
				    	oldEMP.setPosition(afterPosition);
				    }
				    phone = oldEMP.getPhone();
				    oldEMP.setUpdatetime(date);
				    approveService.updateEMP(oldEMP);
			    	
			    }else if(!"".equals(dossid)){//DOSS ID 变更，并且DOSS ID 不为空
			    	orgemp = empService.getEmployeeByDossid(dossid);
			    	//如果系统中已存在该人员信息
			        if(orgemp != null){				    	
					    if(!"".equals(afterName)){
					    	orgemp.setName(afterName);	
					    }
					    if(!"".equals(afterIdCard)){
					    	orgemp.setIdcard(afterIdCard);
					    }
					    if(!"".equals(afterPosition)){
					    	orgemp.setPosition(afterPosition);
					    }
					    orgemp.setIsregist(3);	
					    phone = orgemp.getPhone();
					    orgemp.setUpdatetime(date);
					    oldEMP.setIsactived(2);
					    oldEMP.setUpdatetime(date);
					    approveService.updateEMP(oldEMP);
				    }else{
				    	//新加入一条记录
				    	newEMP = new Org_employee();
					    newEMP.setBrand(oldEMP.getBrand());
					    newEMP.setBirthday(oldEMP.getBirthday());
					    newEMP.setRegion(oldEMP.getRegion());
					    newEMP.setDealercode(oldEMP.getDealercode());
					    newEMP.setDealer(oldEMP.getDealer());
					    String newewid =empService.getEWID(StringUtil.safeToString(oldEMP.getDealercode(), ""));
					    newEMP.setEwid(newewid);
					    //if(dossid != ""){
					    newEMP.setDoss_id(dossid);
					    //}
					    
					    // && !"null".equals(afterName)
					    if(!"".equals(afterName)){
					    	newEMP.setName(afterName);	
					    }else{
					    	newEMP.setName(orgName);
					    }
					    
					    //设置性别
					    newEMP.setSex(oldEMP.getSex());
					    //设置学历
					    newEMP.setDegree(oldEMP.getDegree());
					    // && !"null".equals(afterPosition)
					    if(!"".equals(afterPosition)){
					    	newEMP.setPosition(afterPosition);
					    }else{
					    	newEMP.setPosition(orgPosition);
					    }
					    newEMP.setDescription(oldEMP.getDescription());
					    newEMP.setInservice(oldEMP.getInservice());
					    
					    // && !"null".equals(afterIdCard)
					    if(!"".equals(afterIdCard)){
					    	newEMP.setIdcard(afterIdCard);
					    }else{
					    	newEMP.setIdcard(orgIdCard);
					    }
					    
					    newEMP.setPhone(oldEMP.getPhone());
					    newEMP.setMail(oldEMP.getMail());
					    newEMP.setBankname(oldEMP.getBankname());
					    newEMP.setOpenbank(oldEMP.getOpenbank());
					    newEMP.setAccountbank(oldEMP.getAccountbank());
						  //设置是否有驾照
						newEMP.setLicense(oldEMP.getLicense());
						//设置领照时间					
						newEMP.setGet_license_time(oldEMP.getGet_license_time());
						//设置首次服务品牌
						newEMP.setFirst_service_brand(oldEMP.getFirst_service_brand());
						//设置是否受过品牌培训
						newEMP.setIstrained(oldEMP.getIstrained());
						//设置岗位申请时间
						newEMP.setCpat(oldEMP.getCpat());
						//设置创建时间
						newEMP.setCreatetime(date);
						//设置入职时间
						newEMP.setCaet(oldEMP.getCaet());
						//设置通过岗位培训时间
						newEMP.setPass_train_time(oldEMP.getPass_train_time());
						//设置离职时间
						newEMP.setDimission_time(oldEMP.getDimission_time());
						//设置LMS导入时间
						newEMP.setLms_date(oldEMP.getLms_date());
						newEMP.setIdentityimg(oldEMP.getIdentityimg()); 
						newEMP.setAddress(oldEMP.getAddress());
						newEMP.setHead_img(oldEMP.getHead_img());
					    newEMP.setIsactived(1);
						newEMP.setUsername(oldEMP.getUsername());
						newEMP.setPwd(oldEMP.getPwd());
					    newEMP.setIsregist(3);
					    newEMP.setUpdatetime(date);
					    //newEMP.setIspass(1);
					    phone = newEMP.getPhone();
					    oldEMP.setIsactived(2);
					    oldEMP.setUpdatetime(date);
					    approveService.updateEMP(oldEMP);
					    
					    //保存锁定用户的信息记录
					    UserLockInfo  userLock = new UserLockInfo();
					    userLock.setEwid(oldEMP.getEwid());
					    userLock.setLockDate(sdf.format(date));
					    userLock.setReason("此人由于信息修改产生了新的账号:"+newEMP.getEwid() +"而锁定该账号");
					    userLock.setLockType(2);
					    exportService.saveUserLockInfo(userLock);
					    
					    List<Bo_bonusdetail> bonuslist = bonusService.getBonusbyEWID(ewid);
				    	if(bonuslist.size() > 0){
					    	for(Bo_bonusdetail bonus:bonuslist){
					    		 bonus.setEwid(newewid);
					    		 bonus.setUpdatetime(date);
					    		 bonusService.saveOrUpdateBonus(bonus);
					    	}					    	
				    	}
				    }
			    }else if("".equals(dossid)) {//如果人员信息不存在或是有DOSS ID 变成无 DOSS ID 的人员		    
				    //新加入一条记录
			    	newEMP = new Org_employee();
				    newEMP.setBrand(oldEMP.getBrand());
				    newEMP.setBirthday(oldEMP.getBirthday());
				    newEMP.setRegion(oldEMP.getRegion());
				    newEMP.setDealercode(oldEMP.getDealercode());
				    newEMP.setDealer(oldEMP.getDealer());
				    String newewid =empService.getEWID(StringUtil.safeToString(oldEMP.getDealercode(), ""));
				    newEMP.setEwid(newewid);
				    //if(dossid != ""){
				    newEMP.setDoss_id(dossid);
				    //}
				    
				    // && !"null".equals(afterName)
				    if(!"".equals(afterName)){
				    	newEMP.setName(afterName);	
				    }else{
				    	newEMP.setName(orgName);
				    }
				    
				    //设置性别
				    newEMP.setSex(oldEMP.getSex());
				    //设置学历
				    newEMP.setDegree(oldEMP.getDegree());
				    // && !"null".equals(afterPosition)
				    if(!"".equals(afterPosition)){
				    	newEMP.setPosition(afterPosition);
				    }else{
				    	newEMP.setPosition(orgPosition);
				    }
				    newEMP.setDescription(oldEMP.getDescription());
				    newEMP.setInservice(oldEMP.getInservice());
				    
				    // && !"null".equals(afterIdCard)
				    if(!"".equals(afterIdCard)){
				    	newEMP.setIdcard(afterIdCard);
				    }else{
				    	newEMP.setIdcard(orgIdCard);
				    }
				    
				    newEMP.setPhone(oldEMP.getPhone());
				    newEMP.setMail(oldEMP.getMail());
				    newEMP.setBankname(oldEMP.getBankname());
				    newEMP.setOpenbank(oldEMP.getOpenbank());
				    newEMP.setAccountbank(oldEMP.getAccountbank());
					  //设置是否有驾照
					newEMP.setLicense(oldEMP.getLicense());
					//设置领照时间					
					newEMP.setGet_license_time(oldEMP.getGet_license_time());
					//设置首次服务品牌
					newEMP.setFirst_service_brand(oldEMP.getFirst_service_brand());
					//设置是否受过品牌培训
					newEMP.setIstrained(oldEMP.getIstrained());
					//设置岗位申请时间
					newEMP.setCpat(oldEMP.getCpat());
					//设置创建时间
					newEMP.setCreatetime(date);
					//设置入职时间
					newEMP.setCaet(oldEMP.getCaet());
					//设置通过岗位培训时间
					newEMP.setPass_train_time(oldEMP.getPass_train_time());
					//设置离职时间
					newEMP.setDimission_time(oldEMP.getDimission_time());
					//设置LMS导入时间
					newEMP.setLms_date(oldEMP.getLms_date());
					newEMP.setIdentityimg(oldEMP.getIdentityimg()); 
					newEMP.setAddress(oldEMP.getAddress());
					newEMP.setHead_img(oldEMP.getHead_img());
				    newEMP.setIsactived(1);
					newEMP.setUsername(oldEMP.getUsername());
					newEMP.setPwd(oldEMP.getPwd());
				    newEMP.setIsregist(3);
				    newEMP.setUpdatetime(date);
				    //newEMP.setIspass(1);
				    phone = newEMP.getPhone();
				    oldEMP.setIsactived(2);
				    oldEMP.setUpdatetime(date);
				    approveService.updateEMP(oldEMP);
				    
				  //保存锁定用户的信息记录
                    UserLockInfo  userLock = new UserLockInfo();
                    userLock.setEwid(oldEMP.getEwid());
                    userLock.setLockDate(sdf.format(date));
                    userLock.setReason("此人由于信息修改产生了新的账号:"+newEMP.getEwid() +"而锁定该账号");
                    userLock.setLockType(2);
                    exportService.saveUserLockInfo(userLock);
                    
				    List<Bo_bonusdetail> bonuslist = bonusService.getBonusbyEWID(ewid);
			    	if(bonuslist.size() > 0){
				    	for(Bo_bonusdetail bonus:bonuslist){
				    		 bonus.setEwid(newewid);
				    		 bonus.setUpdatetime(date);
				    		 bonusService.saveOrUpdateBonus(bonus);
				    	}					    	
			    	}
			    }
		    }
		    
		    //更新修改信息状态
		    String id = StringUtil.safeToString(map.get("id"), "");
		    Modify_name modf = approveService.getMDFByID(id);
		    modf.setIsApprove(1);
		    modf.setApprovedate(date);
		    //approveService.approveEmp(oldEMP,newEMP,orgemp, modf);
		    if(newEMP != null){
		    	approveService.updateEMP(newEMP);
		    }
		    if(orgemp != null){
		    	approveService.updateEMP(orgemp);
		    }
		    approveService.modifyMDF(modf);
		    
		    
			try {
				String msg = "";
			    if(newEMP != null){
			    	msg =  "您的信息变更审批已通过，请您用的新账号"+newEMP.getEwid() +"登录系统";
			    	//SingletonClient.getClient().sendSMS(new String[] { newEMP.getPhone() },msg, "",5);// 带扩展码
			    }else if(orgemp != null){
			    	msg =  "您的信息变更审批已通过，请您用的新账号"+orgemp.getEwid() +"登录系统";
			    	//SingletonClient.getClient().sendSMS(new String[] { orgemp.getPhone() },msg, "",5);// 带扩展码
			    }else if(afterEwid != null && afterEwid != "" && !("null").equals(afterEwid)){
			    	msg =  "您的奖金变更审批已通过，请您用的新账号"+afterEwid +"登录系统查询奖金信息";
			    	//SingletonClient.getClient().sendSMS(new String[] { "13020225459" },msg, "",5);// 带扩展码
			    }else{
			    	msg =  "您的信息变更审批已通过，请您用账号"+oldEMP.getEwid()+"登录系统查看您的信息";
			    	// 带扩展码
			    }
			    if(!"".equals(msg)){
			    	SingletonClient.getClient().sendSMS(new String[] { phone },msg, "",5);
			    }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return "redirect:approvemf";
	}
    @RequestMapping (value="/reject")
    public String reject(HttpServletRequest request, 
			HttpServletResponse response){
    	String json = StringUtil.safeToString(request.getParameter("json"), "");
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<Map> list = (List) JSONArray.toCollection(jsonArray, Map.class);
		for(Map map:list){
			String id = StringUtil.safeToString(map.get("id"), "");
			String ewid = StringUtil.safeToString(map.get("after_ewId"), "");
			Modify_name modf = approveService.getMDFByID(id);
			Org_employee emp = empService.getEmployeeByEwid(ewid);
			String phone = emp.getPhone();
			modf.setIsApprove(2);
			approveService.rejectMDF(modf);
			try {
				SingletonClient.getClient().sendSMS(new String[] { phone }, "您的信息变更审批未通过，如需要，请您重新提交申请，谢谢！", "",5);// 带扩展码
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return "redirect:approvemf";
    }
    
    
    /**
	 * 显示所有人员列表
	 * 
	 */
	@RequestMapping(value = "/list_regist_emp")
	public String list_regist_emp(HttpServletRequest request,
			HttpServletResponse response) {
		return "/jsp/proprietor/list_regist_emp";
	}
	
    /**
	 * 显示所有通过注册流程的人员列表
	 * 
	 */
	
	@RequestMapping(value = "/search_regist_emp", method = RequestMethod.POST)
	public void search_regist_emp(HttpServletRequest request,
			HttpServletResponse response) {
		String ispass = StringUtil.safeToString(request.getParameter("ispass"), "");
		String ewid = StringUtil.safeToString(request
				.getParameter("ewid"), "");
		String doss_id = StringUtil.safeToString(request
				.getParameter("doss_id"), "");
		String idcard = StringUtil.safeToString(request.getParameter("idcard"),"");
		
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");
		

		try {

			Map<String, String> p = new HashMap<String, String>();
			p.put("ispass", ispass);
			p.put("ewid", ewid);
			p.put("dealercode", doss_id);
			p.put("idcard", idcard);
			p.put("firstrs", String.valueOf(Integer.valueOf(page)
					* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			List<Map> registemp=empService.search_registEmp(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(registemp, empService.RegistCount(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/updateispass", method = RequestMethod.POST)
	public void updatestate(HttpServletRequest request,
			HttpServletResponse response) {
		String json = StringUtil.safeToString(request.getParameter("json"), "");
		String ispass = StringUtil.safeToString(request.getParameter("ispass"), "");

		JSONArray jsonArray = JSONArray.fromObject(json);
		List<Map> list = (List) JSONArray.toCollection(jsonArray, Map.class);
		for (Map map : list) {
			String id = StringUtil.safeToString(map.get("id"), "");
			Org_employee employee = empService.getEmployeeById(Integer.parseInt(id));
			employee.setIspass(Integer.parseInt(ispass));
			empService.saveEmployee(employee);
			String text="";
			if(ispass.equals("1")){
				text="尊敬的用户，您的延保账号已通过审核，用户名：" +employee.getEwid()+ "请前往延保系统登陆";
			}else{
				String reason = StringUtil.safeToString(request.getParameter("reason"), "");
				text="尊敬的用户，您的延保账号未通过审核，原因：" +reason;
			}
			 
			String[] phone =new String[1];
			phone[0]=employee.getPhone();
			try {//
				SingletonClient.getClient().sendSMS(phone, text, "",5);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	@RequestMapping(value="applyrolelist")
	public String applyinfo(HttpServletRequest request,HttpServletResponse response){
		return "/jsp/proprietor/applyrolelist";
	}
	
	@RequestMapping(value="searchapplyinfo",method=RequestMethod.POST)
	public void searchapplyinfo(HttpServletRequest request,HttpServletResponse response){
			String isapproved = StringUtil.safeToString(request.getParameter("isapproved"), "");
			String page = StringUtil.safeToString(request.getParameter("page"), "");
			String pagesize = StringUtil.safeToString(request
					.getParameter("pagesize"), "");
			try {
				Map<String, String> p = new HashMap<String, String>();
				p.put("isApproved", isapproved);
				p.put("firstrs", String.valueOf(Integer.valueOf(page)
						* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
				p.put("maxrs", pagesize);
				List<Map> applyRoleList=approveService.getApplyRoleList(p);
				PrintWriter out = response.getWriter();
				out.println(LigerUtils.getJsonMap(applyRoleList, approveService.getApplyRoleListCount(p)));
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value = "/dealapply", method = RequestMethod.POST)
	public void dealapply(HttpServletRequest request,
			HttpServletResponse response) {
		String json = StringUtil.safeToString(request.getParameter("json"), "");
		String ispass = StringUtil.safeToString(request.getParameter("ispass"), "");

		JSONArray jsonArray = JSONArray.fromObject(json);
		List<Map> list = (List) JSONArray.toCollection(jsonArray, Map.class);
		for (Map map : list) {
			String id = StringUtil.safeToString(map.get("id"), "");
			String ewId = StringUtil.safeToString(map.get("ewId"), "");
			Apply_role apply = approveService.getApplyRoleById(id);
			apply.setIsapprove(ispass);
			approveService.saveApplyRole(apply);
			String text="";
			Org_employee employee = empService.getEmployeeByEwid(ewId);
			Map<String,String> p =new HashMap<String,String>();
			p.put("position", "总经理");
			p.put("dealercode", employee.getDealercode());
			Org_employee emp=empService.serchEmployee(p);//查找该店是否有总经理
			if(ispass.equals("1")){
				employee.setRole("1");
				empService.saveEmployee(employee);
				if(emp!=null){
					emp.setRole("0");
				}
				text="尊敬的用户，您申请总经理权限已通过审核， 请前往延保系统登陆";
			}else{
				employee.setRole("0");
				empService.saveEmployee(employee);
				text="尊敬的用户，您申请总经理权限未通过审核，如有问题，请联系管理员" ;
			}
			
			
			
			String[] phone =new String[1];
			phone[0]=employee.getPhone();
			try {//
				SingletonClient.getClient().sendSMS(phone, text, "",5);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
}
