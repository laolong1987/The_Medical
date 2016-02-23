package com.web.controller.employee;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.utils.BeanUtils;
import com.utils.DateUtil;
import com.utils.StringUtil;
import com.utils.TimeUtil;
import com.web.entity.Apply_role;
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Modify_name;
import com.web.entity.Org_employee;
import com.web.entity.Secretquestion;
import com.web.service.ApproveService;
import com.web.service.BonusService;
import com.web.service.ContentService;
import com.web.service.EmployeeService;



@Controller
@RequestMapping("/index")
public class IndexController {
	private static final Log log = LogFactory.getLog(IndexController.class);

	@Autowired
	public EmployeeService employeeService;
	@Autowired
	public ContentService contentService;
	
	@Autowired
	public ApproveService approveService;
	
	@Autowired
	public BonusService bonusService;
	
	@RequestMapping(value = "/home")
	public String login(HttpServletRequest request, HttpServletResponse response) {

		String vid = StringUtil.safeToString(request.getParameter("vid"), "");
//		Viwepagerdeta viwepagerdeta =new  Viwepagerdeta();
////		
////		if(!"".equals(vid) && null!=vid){
////			viwepagerdeta=contentService.getimgInfoById(Integer.parseInt(vid));
////		
////		} 
		Map<String, String> map1=new HashMap<String, String>();
		Map emp =(Map)request.getSession().getAttribute("emp");
	  	String brand=(String) emp.get("brand");
	  	String dealercode=(String) emp.get("dealercode");
	  	
	String  regionname=bonusService.getRegionnameByDealerCode(dealercode);
	String  regionname2=bonusService.getRegionnameByDealerCodeyb(dealercode);
	
	  	map1.put("regionname", regionname);
		map1.put("brand", brand);
		
		
		
		//map.put("vid", vid);
		List<Map> showlist = contentService.listshowimg(map1);
	
		request.setAttribute("showlist", showlist);
		
		
		String status=request.getParameter("status");
	
		Map<String, String> map=new HashMap<String, String>();
	//	  String type2=request.getParameter("type");
//		Map emp =(Map)request.getSession().getAttribute("emp");
//	  	String brand=(String) emp.get("brand");
		map.put("status", status);
	//	System.out.println("status -----"+status);
	//	map.put("type", type2);
		map.put("brand", brand);
	 	map.put("regionname", regionname);
		map.put("regionname2", regionname2);
		List<Map> list = contentService.listshuowdocnews(map);
	

		List<Map> list1=new ArrayList<Map>();
		List<Map> list2=new ArrayList<Map>();
		for (Map m : list) {
			String type=StringUtil.safeToString(m.get("type"), "");
			if("1".equals(type)){
				list1.add(m);
			}else if("2".equals(type)){
				list2.add(m);
			}
		}
		
		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);

		request.setAttribute("status", status);
	



		return "/jsp/website/index";
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 *  @return
	 */
	@RequestMapping(value = "/changepwd")
	public void change_pwd(HttpServletRequest request,
			HttpServletResponse response) {
		String id = employeeService.getEmpid(request);
		String pwd1 = StringUtil.safeToString(
				request.getParameter("pwd"), "");
		String pwd2 = StringUtil.safeToString(
				request.getParameter("pwdRepeat"), "");
		String oldpwd = StringUtil.safeToString(
				request.getParameter("oldpwd"), "");
		
		String msg = "修改成功";
		try {
			if (pwd1.equals(pwd2)) {
				Org_employee employee = employeeService
						.getEmployeeById(Integer.parseInt(id));
				
				if (null != employee) {
					if(oldpwd.equals(employee.getPwd())){
						employee.setPwd(pwd1);
						employeeService.saveEmployee(employee);
					}else{
						msg = "旧密码输入错误，请重试";
					}
				} else {
					msg = "用户不存在";
				}

			} else {
				msg = "两次密码输入不一致，请重新输入";
			}
		} catch (Exception e) {
			msg = "修改失败，请重试";
		}

		try {
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示修改密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/show_changepwd")
	public String show_changepwd(HttpServletRequest request,
			HttpServletResponse response) {

		String username = StringUtil.safeToString(request.getSession()
				.getAttribute("username"), "");
		request.setAttribute("username", username);

		return "/jsp/website/change_pwd";
	}

	/**
	 * 退出清空session
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String removeuser(HttpServletRequest request,
			HttpServletResponse response) {

		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("id");
		request.getSession().removeAttribute("emp");
		request.getSession().invalidate();
		log.info("清除用户");
		return "redirect:/";

	}

	/**
	 * 修改employee的基本信息，跳转到modify_emp_baseinfo.jsp页面
	 * 
	 */
	@RequestMapping(value = "/show_modify_emp")
	public String updateEmployee(HttpServletRequest request,
			HttpServletResponse response) {
		// String id_ = request.getParameter("id");
		// System.out.println(id_);
		//用来返回保存信息
		String info = StringUtil.safeToString(request.getParameter("info"),"");
		String modify = StringUtil.safeToString(request.getParameter("modify"),"");
		String id = employeeService.getEmpid(request);
		Org_employee employee = employeeService.getEmployeeById(Integer.parseInt(id));
		Secretquestion secret=employeeService.getSecretQandAByEwid(employee.getEwid());
		String birthday="";
		if(!"".equals(employee.getBirthday()) && employee.getBirthday()!=null){
			birthday=employee.getBirthday().toString();
			birthday=birthday.substring(0, 10);
		}
		employee.setRegion(bonusService.getRegionnameByDealerCode(employee.getDealercode()));
		request.setAttribute("employee", employee);
		if(null!=secret){
			request.setAttribute("s1", secret.getSecretquestion());
			request.setAttribute("s2", secret.getSecretanwser());
		}else{
			request.setAttribute("s1", "0");
			request.setAttribute("s2", "");
		}
		
		List<Bo_bonusdetail>  bonus=bonusService.getBonusbyEWID(employee.getEwid());
		boolean flag=true;
		if(bonus.size()>0){
			for(Bo_bonusdetail bo: bonus){
				if(bo.getStatus()==1){
					//判断是否有奖金发放成功，发放成功，前台姓名为只读，不允许修改姓名
					flag=false;
					break;
				}
			}
		}
		
		request.setAttribute("flag", flag);
		request.setAttribute("birthday", birthday);
		// request.setAttribute("ewid",
		// setEWid(String.valueOf(shsgmNew.getId())));
		request.setAttribute("sysdate", TimeUtil.getSysDate());
		
		if(!"".equals(modify) && modify.equals("0")){
			request.setAttribute("errorMsg", "当前修改的姓名、身份证号已存在，不可修改");
		}
		if(!"".equals(info)){
			if("1".equals(info)){
				request.setAttribute("info", "保存成功");
			}else if("2".equals(info)){
				request.setAttribute("info", "图片文件大小不能超过2M");
			}else if("3".equals(info)){
				request.setAttribute("info", "图片格式必须为JPG格式");
			}
		}
		
		return "/jsp/website/modify_emp_baseinfo";
	}

	/**
	 * 更新employee基本信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modify_emp_baseinfo")
	public String modify_emp_baseinfo(HttpServletRequest request,
			HttpServletResponse response) {
		String id = employeeService.getEmpid(request);
		String result="1"; // 1=成功 2=文件太大 3=文件格式不对
		String modify="1";//重定向参数，标记是否允许修改姓名
//		String idcard = StringUtil.safeToString(request.getParameter("idCard"),
//				"");
		String phoneNum = StringUtil.safeToString(
				request.getParameter("phone"), "");
		String bankName = StringUtil.safeToString(request
				.getParameter("bankName"), "");
		String openBank = StringUtil.safeToString(request
				.getParameter("openBank"), "");
		String accountBank = StringUtil.safeToString(request
				.getParameter("banknum"), "");
		String mail = StringUtil.safeToString(request
				.getParameter("email"), "");
		String birthday = StringUtil.safeToString(request
				.getParameter("birthday"), "");
		
		String secretQuestion = StringUtil.safeToString(request
				.getParameter("secretQuestion"), "");
		String answer = StringUtil.safeToString(request
				.getParameter("answer"), "");
		
		String tip="";
		Org_employee employee = employeeService.getEmployeeById(Integer
				.parseInt(id));
		if (null != employee) {
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			/** 构建图片保存的目录 **/
			String logoPathDir = "/images/website/idcard";
			/** 得到图片保存目录的真实路径 **/
			String logoRealPathDir = request.getSession().getServletContext()
					.getRealPath(logoPathDir);
			/** 根据真实路径创建目录 **/
			File logoSaveFile = new File(logoRealPathDir);
			if (!logoSaveFile.exists())
				logoSaveFile.mkdirs();
			/** 页面控件的文件流 **/
			MultipartFile multipartFile = multipartRequest.getFile("cardphoto");
			if(null!=multipartFile.getOriginalFilename() && !"".equals(multipartFile.getOriginalFilename()) ){
				long size=multipartFile.getSize()/1024;
				/** 获取文件的后缀 **/
				String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				
				if(!".jpg".equals(suffix)){
					return "redirect:show_modify_emp?info=3";
				}
				
				/** 获取用户名 **/
				// /**使用UUID生成文件名称**/
				// String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
				// String logImageName = multipartFile.getOriginalFilename();
				String logImageName = employee.getEwid() + suffix;
				/** 拼成完整的文件保存路径加文件 **/
				String fileName = logoRealPathDir + File.separator + logImageName;
				File file = new File(fileName);
				try {
					if(size<2000){
						multipartFile.transferTo(file);
					}else{
						return "redirect:show_modify_emp?info=2";
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				/** 保存图片路径到数据库 **/
				String img_Path = logoPathDir + "/" + logImageName;
				employee.setIdentityimg(img_Path);
			}
			
			
			
			// shsgmNew.setPosition(position);
			if("".equals(employee.getDoss_id()) || employee.getDoss_id()==null){
				List<Bo_bonusdetail>  bonus=bonusService.getBonusbyEWID(employee.getEwid());
				boolean flag=true;
				if(bonus.size()>0){
					for(Bo_bonusdetail bo: bonus){
						if(bo.getStatus()==1){
							//判断是否有奖金发放成功，发放成功，前台姓名为只读，不允许修改姓名
							flag=false;
							break;
						}
					}
				}
				if(flag){
					String name = StringUtil.safeToString(request.getParameter("name"), "");
					String position = StringUtil.safeToString(request.getParameter("position"), "");
					String other = StringUtil.safeToString(request.getParameter("other"), "");
					String cardnum  = StringUtil.safeToString(request.getParameter("cardnum"), "");
					if("other".equals(position)){
						position=other;
					}
					Map<String,String> p=new HashMap();
					p.put("name", name);
					p.put("idcard", cardnum);
					Org_employee emp=employeeService.serchEmployee(p);
					
					if(emp !=null && !emp.getEwid().equals(employee.getEwid())){
						modify="0";//修改的姓名、身份证在数据库中存在，不允许修改
					}else{
						employee.setName(name);
						employee.setPosition(position);		
						employee.setIdcard(cardnum);
					}
					
				}
				
			}
			if(!"0".equals(secretQuestion)){
				Secretquestion secret=employeeService.getSecretQandAByEwid(employee.getEwid());
				if(secret ==null){
					Secretquestion secrectquestion=new Secretquestion();
					secrectquestion.setEwid(employee.getEwid());
					secrectquestion.setSecretquestion(secretQuestion);
					secrectquestion.setSecretanwser(answer);
					secrectquestion.setCreatetime(new Date());
					employeeService.saveSecretQandA(secrectquestion);
				}else{
					secret.setSecretquestion(secretQuestion);
					secret.setSecretanwser(answer);
					secret.setUpdatetime(new Date());
					employeeService.saveSecretQandA(secret);
				}
			}else{
				tip="密保信息为空，未保存";
			}
			
			employee.setMail(mail);
			if(!"".equals(birthday)){
				employee.setBirthday(DateUtil.String2Date(birthday, "yyyy-MM-dd"));
			}			
			employee.setPhone(phoneNum);
			employee.setBankname(bankName);
			employee.setOpenbank(openBank);
			employee.setAccountbank(accountBank);
			employee.setUpdatetime(new Date());
			employeeService.saveEmployee(employee);
			
			request.getSession().setAttribute("username", employee.getName());
			request.getSession().setAttribute("id", employee.getId());
			Map emp= BeanUtils.BeanToMap(employee);
			emp.put("region", bonusService.getRegionnameByDealerCode(employee.getDealercode()));
			request.getSession().setAttribute("emp", emp);
			
		}
		return "redirect:show_modify_emp?info=1&&modify=" +modify;
	}

	@RequestMapping(value = "/showapply")
	public String applymodifyinfo(HttpServletRequest request,
			HttpServletResponse response) {
		// String id_ = request.getParameter("id");
		// System.out.println(id_);
		String id = employeeService.getEmpid(request);
		Org_employee employee = employeeService.getEmployeeById(Integer
				.parseInt(id));
		request.setAttribute("employee", employee);
		// request.setAttribute("ewid",
		// setEWid(String.valueOf(shsgmNew.getId())));
		request.setAttribute("sysdate", TimeUtil.getSysDate());
		return "/jsp/website/applymodifyinfo";
	}

	/**
	 * 提交修改申请信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/submitapply")
	public void submitApply(HttpServletRequest request,
			HttpServletResponse response) {
		String id = employeeService.getEmpid(request);
		
		String after_name = StringUtil.safeToString(request
				.getParameter("name"), "");
		String after_idcard = StringUtil.safeToString(request
				.getParameter("cardnum"), "");
		String after_position = StringUtil.safeToString(request
				.getParameter("position"), "");
		String input_position = StringUtil.safeToString(request
				.getParameter("input_position"), "");
		String after_dossId = StringUtil.safeToString(request
				.getParameter("dossid"), "");
//		String after_EWID = StringUtil.safeToString(request
//				.getParameter("ewid"), "");
		
		after_dossId=StringUtil.convertStringUpper(after_dossId);
		
		String tip = "0";
		
		Org_employee employee = employeeService.getEmployeeById(Integer
				.parseInt(id));		        
		String before_name = employee.getName();
		String before_idcard = employee.getIdcard();
		String before_dossId = employee.getDoss_id();
		String before_position = employee.getPosition();
		String dealercode = employee.getDealercode();
		
		if("其他".equals(after_position)){
			after_position=input_position;
		}
		
		String EWID = employee.getEwid();
		boolean both_equals=before_name.equals(after_name) && before_idcard.equals(after_idcard) 
			 && after_dossId.equals(before_dossId) && after_position.equals(before_position);

		Modify_name applyInfo = new Modify_name();
		applyInfo.setEwid(EWID);
		applyInfo.setDealercode(dealercode);
		applyInfo.setBefore_name(before_name);
		applyInfo.setBefore_idcard(before_idcard);
		applyInfo.setBefore_position(before_position);
		applyInfo.setBefore_dossid(before_dossId);
		applyInfo.setInfo_type(0);
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		/** 构建图片保存的目录 **/
//		String logoPathDir = "/images/website/proveimg";
//		/** 得到图片保存目录的真实路径 **/
//		String logoRealPathDir = request.getSession().getServletContext()
//				.getRealPath(logoPathDir);
//		/** 根据真实路径创建目录 **/
//		File logoSaveFile = new File(logoRealPathDir);
//		if (!logoSaveFile.exists())
//			logoSaveFile.mkdirs();
//		/** 页面控件的文件流 **/
//		MultipartFile multipartFile = multipartRequest.getFile("cardphoto");
//		/** 获取文件的后缀 **/
//		long size=multipartFile.getSize()/1024;
//		
//		
//		String suffix = multipartFile.getOriginalFilename().substring(
//				multipartFile.getOriginalFilename().lastIndexOf("."));
//		/** 获取用户名 **/
//		// /**使用UUID生成文件名称**/
//		// String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
//		// String logImageName = multipartFile.getOriginalFilename();
//		String logImageName = EWID + suffix;
//		/** 拼成完整的文件保存路径加文件 **/
//		String fileName = logoRealPathDir + File.separator + logImageName;
//		System.out.println(fileName);
//		File file = new File(fileName);
//		try {
//			if(size<2000){
//				multipartFile.transferTo(file);
//			}
//		} catch (IllegalStateException e) {
//			tip="0";
//			e.printStackTrace();
//		} catch (IOException e) {
//			tip="0";
//			e.printStackTrace();
//		}
//		/** 保存图片路径到数据库 **/
//		String img_Path = logoPathDir + "/" + logImageName;
		
		
		if(both_equals){
			tip="4";
		}
//		else if(size>2000){
//			tip="3";//图片太大不能上传
//		}
		else{
			Map map = new HashMap<String, String>();
			if (!after_name.equals(before_name) && !"".equals(after_name)) {
				map.put("name", after_name);
				applyInfo.setAfter_name(after_name);
			} else {
//				applyInfo.setAfter_name("未申请修改");
//				applyInfo.setAfter_name(before_name);
			}
			if (!after_idcard.equals(before_idcard) && !"".equals(after_idcard)) {
				map.put("idcard", after_idcard);
				applyInfo.setAfter_idcard(after_idcard);
			} else {
//				applyInfo.setAfter_idcard("未申请修改");
//				applyInfo.setAfter_idcard(before_idcard);
			}

			if (!after_position.equals(before_position) && !"".equals(after_position)) {
				applyInfo.setAfter_position(after_position);
			} else {
//				applyInfo.setAfter_position("未申请修改");
			}

			if (!"".equals(after_dossId) && !after_dossId.equals(before_dossId)) {
				map.put("doss_id", after_dossId);
				applyInfo.setAfter_dossid(after_dossId);
			} else if(after_dossId.equals(before_dossId)){
//				applyInfo.setAfter_dossid(after_dossId);
				applyInfo.setAfter_dossid("未申请修改");
			}else if("".equals(before_dossId)){
				applyInfo.setAfter_dossid("");
			}
			
//			if (!after_EWID.equals(EWID) && ! "".equals(after_EWID)) {
//				Org_employee emp= employeeService.getEmployeeByEwid(after_EWID);
//				if(emp != null){
//					applyInfo.setAfter_ewId(after_EWID);
//					
//				}else{
//					tip="2";
//				}
//			} else {
////				applyInfo.setAfter_dossid(after_dossId);
////				applyInfo.setAfter_dossid("未申请修改");
//			}
			applyInfo.setProve_img(employee.getIdentityimg());
			applyInfo.setApply_date(new Date());

			Org_employee isexituser=null;
			if(map.containsKey("idcard") || map.containsKey("doss_id")){
				isexituser = employeeService.serchEmployee(map);
			}
			if(map.size()==0){
				if(!tip.equals("2")){
					employeeService.saveApplyInfo(applyInfo);
					tip="1";
				}
			}else if(map.containsKey("doss_id") && !map.containsKey("idcard")){//申请修改dossid,未申请修改身份证
				if (isexituser != null && !id.equals(isexituser.getId())) {//当前申请者相同的身份证在系统中存在
					int isactived = isexituser.getIsactived();
					String state = "";
					if (isactived == 0) {
						state = "未激活";
					} else if (isactived == 1) {
						state = "已激活";
					} else {
						state = "已锁定";
					}
					applyInfo.setRemark("当前申请修改信息与用户："  + isexituser.getEwid()
							+ "相同，用户" + isexituser.getEwid() + "的DOSSID为：" + isexituser.getDoss_id() 
							+ "且账号：" + state);
					employeeService.saveApplyInfo(applyInfo);
					tip = "1";
				}else{
					applyInfo.setRemark("该用户申请修改dossid,且当前修改的dossid在系统中不存在");
					employeeService.saveApplyInfo(applyInfo);
					tip = "1";
				}
			}else if (isexituser != null && !id.equals(isexituser.getId())) {//当前申请者相同的身份证在系统中存在
				int isactived = isexituser.getIsactived();
				String state = "";
				if (isactived == 0) {
					state = "未激活";
				} else if (isactived == 1) {
					state = "已激活";
				} else {
					state = "已锁定";
				}
				if ("".equals(after_dossId)|| (!isexituser.getDoss_id().equals(after_dossId) && !isexituser
								.getDoss_id().isEmpty())) {//如果填写的dossid与"存在的用户"的dossid不同提示重新填写
					tip = "DOSSID与当前申请修改的姓名不匹配，请重新填写";
				} else {
					if (isexituser.getDoss_id().isEmpty()) {
						applyInfo.setRemark("当前申请修改信息与用户：" + isexituser.getEwid()
								+ "相同，用户" + isexituser.getEwid() + "无DOSSID"+ "账号：" + state);
					} else {
						applyInfo.setRemark("当前申请修改信息与用户："  + isexituser.getEwid()
								+ "相同，用户" + isexituser.getEwid() + "的DOSSID为：" + isexituser.getDoss_id() 
								+ "账号：" + state);
					}
					employeeService.saveApplyInfo(applyInfo);
					tip = "1";
				}
			} else if(!map.containsKey("idcard") && !map.containsKey("doss_id")) {
				applyInfo.setRemark("该用户只申请变更姓名");
				employeeService.saveApplyInfo(applyInfo);
				tip = "1";
			}else{
				applyInfo.setRemark("该用户只申请变更身份证等信息，且当前申请修改的身份证在系统中不存在");
				employeeService.saveApplyInfo(applyInfo);
				tip = "1";
			}
			Map p = new HashMap();       
	        if("".equals(after_name) && "".equals(after_idcard) && "".equals(after_position)){
	            p.put("doss_id", after_dossId);
	            p.put("dealercode", employee.getDealercode());
	            Org_employee newEmp = employeeService.getEMPByNIDP(p); 
	            if(newEmp != null){
	                if(newEmp.getIsactived() == 2){
	                    //request.setAttribute("tip", "您申请的新的DOSS ID 对应的账号已锁定，请您联系管理员，谢谢！");
	                    tip = "5";
	                }
	            }
	        }else if(!"".equals(after_dossId) && after_dossId.equals(employee.getDoss_id())){            
	            p.put("name", after_name);
	            p.put("idcard", after_idcard);
	            p.put("position", after_position);
	            p.put("dealercode", employee.getDealercode());
	            Org_employee newEmp = employeeService.getEMPByNIDP(p);
	            if(newEmp != null){
	                if(newEmp.getIsactived() == 1 || newEmp.getIsactived() == 0){
	                    tip = "6";
	                }
	            }
	        }
		}
		
		try {
			Map rmap=new HashMap();
			rmap.put("data", tip);
			JSONObject resultJSON = JSONObject.fromObject(rmap);
			PrintWriter out = response.getWriter();
	    	out.print(resultJSON);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示上传用户头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/show_uploadimg")
	public String show_uploadimg(HttpServletRequest request,
			HttpServletResponse response) {

		String username = StringUtil.safeToString(request.getSession()
				.getAttribute("username"), "");
		request.setAttribute("username", username);

		return "/jsp/website/uploadimg";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadimg")
	public void upload(HttpServletRequest request,
			HttpServletResponse response) {

		String result="1";//1=成功  0="失败" 2="文件太大" 3=格式不对
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/** 构建图片保存的目录 **/
		String logoPathDir = "/images/empimg";
		/** 得到图片保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		long size=multipartFile.getSize()/1024;
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		
		if(".jpg".equals(suffix)){
		/** 获取用户名 **/
		String username = StringUtil.safeToString(request.getSession()
				.getAttribute("username"), "");
		// /**使用UUID生成文件名称**/
		// String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
		// String logImageName = multipartFile.getOriginalFilename();
		String logImageName = username + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = logoRealPathDir + File.separator + logImageName;
//		System.out.println(fileName);
		File file = new File(fileName);
		try {
			if(size<2000){
				multipartFile.transferTo(file);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 保存图片路径到数据库 **/
		String img_Path = logoPathDir + "/" + logImageName;
		Org_employee employee = employeeService.getEmployeeByUsername(username);
		if (null != employee) {
			// shsgmNew.setPosition(position);
			if(size<2000){
				employee.setHead_img(img_Path);
				employeeService.saveEmployee(employee);
				employee = employeeService.getEmployeeByUsername(username);
				Map emp = BeanUtils.BeanToMap(employee);
				request.getSession().setAttribute("emp", emp);
				result=img_Path;
			}else{
				result="2";
			}
		}
	}else{
		result="3";
	}
		try {
			Map rmap=new HashMap();
			rmap.put("result", result);
			JSONObject resultJSON = JSONObject.fromObject(rmap);
			PrintWriter out = response.getWriter();
			out.println(resultJSON);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/applyrole")
	public void applyrole(HttpServletRequest request,
			HttpServletResponse response) {

		String result="1";//1=成功  0="失败" 2="文件太大" 3=格式不对
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/** 构建图片保存的目录 **/
		String logoPathDir = "/images/proveimg";
		/** 得到图片保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("provephoto");
		long size=multipartFile.getSize()/1024;
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		
		if(".jpg".equals(suffix)){
		/** 获取用户名 **/
		String id = StringUtil.safeToString(request.getSession()
				.getAttribute("id"), "");
		
		Org_employee employee=employeeService.getEmployeeById(Integer.parseInt(id));
		Map<String,String> map =new HashMap<String,String>();
		map.put("position", "总经理");
		map.put("dealercode", employee.getDealercode());
		Org_employee emp=employeeService.serchEmployee(map);//查找该店是否有总经理
		// /**使用UUID生成文件名称**/
		// String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
		// String logImageName = multipartFile.getOriginalFilename();
		String logImageName ="applyRole_" + employee.getEwid() + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = logoRealPathDir + File.separator + logImageName;
//		System.out.println(fileName);
		File file = new File(fileName);
		try {
			if(size<2000){
				multipartFile.transferTo(file);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 保存图片路径到数据库 **/
		String img_Path = logoPathDir + "/" + logImageName;
		if (null != employee) {
			// shsgmNew.setPosition(position);
			if(size<2000){
				Apply_role apply=new Apply_role();
				apply.setName(employee.getName());
				apply.setDealercode(employee.getDealercode());
				apply.setDealer(employee.getDealer());
				apply.setPosition(employee.getPosition());
				apply.setEwid(employee.getEwid());
				apply.setIsapprove("2");
				apply.setProve_img(img_Path);
				
				if(emp!=null && !"2".equals(emp.getIsactived())){
					apply.setRemark("该经销商已存在总经理,但总经理账号被锁定");
				}else if(emp!=null){
					apply.setRemark("该经销商已存在总经理");
				} 
				else{
					apply.setRemark("该经销商无总经理");
				}
				apply.setCreatetime(new Date());
				apply.setUpdatetime(new Date());
				approveService.saveApplyRole(apply);
				
				employee.setRole("2");
				employeeService.saveEmployee(employee);
				result="1";
			}else{
				result="2";
			}
		}
	}else{
		result="3";
	}
		try {
			Map rmap=new HashMap();
			rmap.put("result", result);
			JSONObject resultJSON = JSONObject.fromObject(rmap);
			PrintWriter out = response.getWriter();
			out.println(resultJSON);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@RequestMapping( value = "/toleft")
	public String toleft(HttpServletRequest request,
			HttpServletResponse response) {
		
		//重新读取left的内容

		return "/jsp/website/left";

	}
	
	public static void main(String[] args) {
		String[] color = { "div_plan_green", "div_plan_red",
				"div_plan_blue", "div_plan_yellow" };

		for (int i = 1; i < 10; i++) {
			int d = (i % 4);
			String c = color[d];
			System.out.println(d+"-------------"+c);
		}
	}
}
