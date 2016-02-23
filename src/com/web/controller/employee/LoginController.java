package com.web.controller.employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.common.SingletonClient;
import com.utils.BeanUtils;
import com.utils.DateUtil;
import com.utils.MD5Util;
import com.utils.SettingUtil;
import com.utils.StringUtil;
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Modify_name;
import com.web.entity.Msgvalidate;
import com.web.entity.Org_employee;
import com.web.entity.Secretquestion;
import com.web.service.BonusService;
import com.web.service.EmployeeService;

import org.apache.commons.fileupload.FileItemFactory;
@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Log log = LogFactory.getLog(LoginController.class);
	
	@Autowired
	public EmployeeService employeeService;
	@Autowired
	public BonusService bonusService;
	
	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/go")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		
		String name = StringUtil.safeToString(request.getParameter("username"),"");
		String pwd = StringUtil.safeToString(request.getParameter("password"),"");
		Map map = new HashMap<String, String>();
		map.put("username", name);
		map.put("pwd", pwd);
		log.info("登录-------------------");
		Org_employee employee = employeeService.checkLogin(map);
		if (null !=employee && employee.getIsactived()==1) {//Isactived=1表示激活，Isactived=2表示锁定，Isactived=0表示未激活
//			if(employee.getIspass()==2){
//				request.setAttribute("error", "该账号正在审核中，请耐心等待");
//				return "/jsp/website/login";
//			}else if(employee.getIspass()==0){
//				request.setAttribute("error", "该账号审核未通过，请联系管理员");
//				return "/jsp/website/login";
//			}else{
				log.info("当前登录用户" + name);
				request.getSession().setAttribute("username", name);
				request.getSession().setAttribute("id", employee.getId());
				request.getSession().setAttribute("position", employee.getPosition());
				request.getSession().setAttribute("employee", employee);
				Map emp= BeanUtils.BeanToMap(employee);
				emp.put("region", bonusService.getRegionnameByDealerCode(employee.getDealercode()));
				request.getSession().setAttribute("emp", emp);
				return "redirect:/index/home";
//			}
				
		}else if(null !=employee && employee.getIsactived()==0){
			if(employee.getDoss_id()==null || "".equals(employee.getDoss_id())){
				request.setAttribute("error", "账号信息不完整，请转注册页面完成信息填写！");
				return "/jsp/website/login";
			}else{
				request.setAttribute("error", "账号未激活，请激活账号！");
				return "/jsp/website/login";
			}
			
		}else if(null !=employee && employee.getIsactived()==2){
			request.setAttribute("error", "账号已锁定，不可登陆，请联系管理员");
			return "/jsp/website/login";
		}else {
			// 跳转到登陆页面 并且提示错误
			request.setAttribute("error", "用户名或密码错误，请重新输入！");
			return "/jsp/website/login";
		}
	}

	
	@RequestMapping(value = "/showlogin")
	public String showlogin(HttpServletRequest request,
			HttpServletResponse response) {
		String ewid = StringUtil.safeToString(request
				.getParameter("ewid"), "");
		request.setAttribute("EWID", ewid);	
		
		String[] phone =new String[1];
		phone[0]="13795242060";
		try {//
			SingletonClient.getClient().sendSMS(phone, "1111", "",5);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(ewid);
		return "/jsp/website/login";
	}
	
	
	/**
	 * 第二种登录方式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String login2(HttpServletRequest request, HttpServletResponse response) {
		String name = StringUtil.safeToString(request.getParameter("username"),
				"");
		String pwd = StringUtil.safeToString(request.getParameter("password"),
				"");
//		String info = StringUtil
//				.safeToString(request.getParameter("error"), "");
//		String wether_open = SettingUtil.getSetting("open_login");
		// System.out.println(name);
		// System.out.println(psw);
		Map map = new HashMap<String, String>();
		
			map.put("username", name);
			map.put("pwd", pwd);
		log.info("登录-------------------");

		if(!"".equals(name) && !"".equals(pwd)){
			Org_employee employee = employeeService.checkLogin(map);
			if (null !=employee) {
				// 跳转到list列表页面
				log.info("当前登录用户" + name);
				//Org_employee employee = employeeService.getEmployeeByUsername(name);
				request.getSession().setAttribute("username", name);
				request.getSession().setAttribute("id", employee.getId());
				//System.out.println(employeeService.getEmpUsername(request));
					
				Map emp= BeanUtils.BeanToMap(employee);
				request.getSession().setAttribute("emp", emp);
				return "redirect:/index/home";
			} else {
				// 跳转到登陆页面 并且提示错误
				request.setAttribute("error", "用户名或密码错误，请重新输入！");
				return "/jsp/website/login2";
			}
		}else{
			return "/jsp/website/login2";
		}
		
	}
	
	/**
	 * 验证用户名（昵称）是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkusername")
	public void checkusername(HttpServletRequest request, HttpServletResponse response) {
		String username = StringUtil.safeToString(request.getParameter("username"),
				"");
		log.info("验证用户名-------------------");
			String result="0";// 1=true 0=false
			if(!username.isEmpty()){
				boolean isexits = employeeService.checkUsernameIsexit(username);
				if (isexits) {
					result="1";
				}
			}
							
			try {
				PrintWriter out = response.getWriter();
				out.println(result);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/regist")
	public String checkuserIsexit(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String idcard = request.getParameter("cardnum");
		String dealercode = request.getParameter("code");
		dealercode=dealercode.toUpperCase();
		String brand = StringUtil.safeToString(request.getSession().getAttribute("brand"), "");
		Org_employee employee=null;
		String tip ="";
		Map map = new HashMap<String, String>();
		if(name!=null && name!="" && idcard!=null && idcard!="" && dealercode!=null && dealercode!=""){
			map.put("name", name);
			map.put("idcard", idcard);
			map.put("dealercode", dealercode);
			employee = employeeService.checkUserIsexit(map);
			log.info("验证用户是否存在/激活-------------------");
			if (null !=employee && employee.getIsactived()==0) {//Isactived=1表示激活，Isactived=2表示锁定，Isactived=0表示未激活
				// 跳转到list列表页面
				if(employee.getDoss_id()==null ||"".equals(employee.getDoss_id())){
					String num="";
					for (int i = 0; i <= 5; i++) {
				    	int rd = (int) (Math.random() * 10);
				        num+=String.valueOf(rd);
				    }
					String text="尊敬的用户，您的验证码为：" +num+ "，有效时间为15分钟，使用后即作废。";
					String[] phone =new String[1];
					phone[0]=employee.getPhone();
					Msgvalidate mvd=new Msgvalidate();
					mvd.setEwid(employee.getEwid());
					mvd.setMsgcode(num);
					mvd.setCreatetime(new Date());
					employeeService.saveVertifyCode(mvd);
					int vid=mvd.getId();
					try {//
						SingletonClient.getClient().sendSMS(phone, text, "",5);
						tip="校验码已发送到你的手机，15分钟内输入有效，请勿泄漏 ";
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
					request.setAttribute("employee", employee);
					request.setAttribute("tip", tip);
					request.setAttribute("action", "regist");
					request.setAttribute("mvid", mvd.getId());
					return "/jsp/website/sendmsg";
				}else{
					log.info("当前登录用户" + name);
					tip="该帐号未激活，立即<a href=http://" + request.getServerName()+ ":"+ request.getServerPort() + request.getContextPath()+"/login/zhanghu?brand="+brand +"&event=activate>激活</a>";
					request.setAttribute("tip", tip);
					return "/jsp/website/regist";
				}
			}else if(null !=employee && employee.getIsactived()==1){
				if(employee.getIsregist()==2){
					tip="该账号已被注册，正在审核中，请耐心等待";
					request.setAttribute("tip", tip);
					return "/jsp/website/regist";
				}else if(employee.getIsregist()==3){
					tip="该账号已被注册，审核未通过，请联系管理员";
					request.setAttribute("tip", tip);
					return "/jsp/website/regist";
				}else{
					tip="该帐号已激活，立即<a href=http://" + request.getServerName()+ ":"+ request.getServerPort() + request.getContextPath()+">登陆</a>";
					request.setAttribute("tip", tip);
					return "/jsp/website/regist";
				}
			}else if(null !=employee && employee.getIsactived()==2){
				tip="该帐号已锁定，不可注册，请与管理员联系";
				request.setAttribute("tip", tip);
				return "/jsp/website/regist";
			}
			else {
				// 跳转
				Org_employee tempEmp = new Org_employee();
				tempEmp.setName(name);
				tempEmp.setIdcard(idcard);
				tempEmp.setDealercode(dealercode);
				tempEmp.setBrand(brand);
				tempEmp.setRegion(bonusService.getRegionnameByDealerCode(dealercode));
				tempEmp.setDealer(bonusService.getdelearByDealerCode(dealercode));
				request.getSession().setAttribute("tempEmp", tempEmp);
				return "/jsp/website/registnext";
			}
		}else{
			tip="验证信息不可为空，请重试！";
			request.setAttribute("tip", tip);
			return "/jsp/website/regist";
		}
	}
	
	
	/**
	 * 用户注册下一步
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/saveregister")
	public void register(HttpServletRequest request,HttpServletResponse response) {
		String phone = StringUtil.safeToString(request.getParameter("phone"),"");
		String position = StringUtil.safeToString(request.getParameter("position"),"");
		String email = StringUtil.safeToString(request.getParameter("email"),"");
		String secretQuestion = StringUtil.safeToString(request.getParameter("secretQuestion"),"");
		String secretAnwser = StringUtil.safeToString(request.getParameter("answer"),"");
		String bankname = StringUtil.safeToString(request.getParameter("bankname"),"");
		String openbank = StringUtil.safeToString(request.getParameter("bank"),"");
		String account = StringUtil.safeToString(request.getParameter("banknum"),"");
		String username = StringUtil.safeToString(request.getParameter("username"),"");
		String pwd1 = StringUtil.safeToString(request.getParameter("pwd"),"");
		String pwd2 = StringUtil.safeToString(request.getParameter("pwdRepeat"),"");
		String EWID=StringUtil.safeToString(request.getParameter("ewid"),"");
		String birthday = StringUtil.safeToString(request.getParameter("birthday"),"");
		String msg = "";
		String dealercode="";
		Org_employee employee=new Org_employee();
		if(EWID=="" || EWID==null){
			employee = (Org_employee) request.getSession().getAttribute("tempEmp");
			dealercode=employee.getDealercode();
			employee.setIsregist(1);//isregist=是否为注册用户;0=否；1=是
			EWID = employeeService.getEWID(dealercode);
		}else{
			employee=employeeService.getEmployeeByEwid(EWID);
		}
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
		long size=multipartFile.getSize()/1024;
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 获取用户名 **/
		// /**使用UUID生成文件名称**/
		// String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
		// String logImageName = multipartFile.getOriginalFilename();
		String logImageName = EWID + suffix;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = logoRealPathDir + File.separator + logImageName;
		System.out.println(fileName);
		File file = new File(fileName);
		try {
			if(size<2000){
				multipartFile.transferTo(file);
			}
		} catch (IllegalStateException e) {
			msg="0";
			e.printStackTrace();
		} catch (IOException e) {
			msg="0";
			e.printStackTrace();
		}
		/** 保存图片路径到数据库 **/
		String img_Path = logoPathDir + "/" + logImageName;
		
		
		employee.setPhone(phone);
		if(position.equals("other")){
			position=request.getParameter("other");
		}
		employee.setPosition(position);
		employee.setMail(email);
		employee.setBankname(bankname);
		employee.setOpenbank(openbank);
		employee.setAccountbank(account);   
	    employee.setUsername(username);
	    employee.setEwid(EWID);
	    if(!"".equals(birthday)){
			employee.setBirthday(DateUtil.String2Date(birthday, "yyyy-MM-dd"));
		}
//	    employee.setCard_img(img_Path);
	    employee.setIdentityimg(img_Path);
	    boolean flag=false;
	    try{
	    	if(size>2000){
	    		msg="2";
	    	}
	    	else if(pwd1.equals(pwd2)){
		    	 employee.setPwd(pwd1);
		    	 employee.setCreatetime(new Date());
		    	 employee.setIsactived(1);
		    	 employee.setIspass(1);//ispass==是否为通过审核;0=否；1=是，2=进行中
		    	 employeeService.saveEmployee(employee);
		    	 if(!"".equals(secretQuestion) && !"".equals(secretAnwser)){
		    		 Secretquestion secretQ_A = new Secretquestion();
		    		 secretQ_A.setSecretquestion(secretQuestion);
		    		 secretQ_A.setSecretanwser(secretAnwser);
		    		 secretQ_A.setEwid(EWID);
		 		    secretQ_A.setUsername(username);
		 		    secretQ_A.setCreatetime(new Date());
		    		 employeeService.saveSecretQandA(secretQ_A);
		    	 }
		    	 msg="1";
		    	 flag=true;
		    	 request.setAttribute("EWID", EWID);
		    }
	    }catch(Exception e){
	    	flag=false;
	    	msg="0";
	    }
	    try {
	    	Map rmap=new HashMap();
			rmap.put("data", msg);
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
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/procotol")
	public String procotol(HttpServletRequest request,HttpServletResponse response) {
		String event=request.getParameter("event");
		String type = request.getParameter("type");
		request.setAttribute("event", event);
		request.getSession().setAttribute("type", type);
		//request.getSession().setAttribute("event", event);
		return "/jsp/website/procotol";
	}
	

	/**
	 * 地址栏传参判断跳转页面（激活、忘记密码、获取账户、奖金更新、注册）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/zhanghu")
	public String zhanghu(HttpServletRequest request,
			HttpServletResponse response) {
		String event=request.getParameter("event");
		String brand=request.getParameter("brand");
		request.getSession().setAttribute("event",event);
		//String type = request.getParameter("type");
		if(brand!="" || brand!=null){
			request.getSession().setAttribute("brand", brand);
		}
		if(event.equals("activate")){
			return "/jsp/website/activate";
		}else if(event.equals("password")){
			return "/jsp/website/forgetpwd";
		}else if(event.equals("getaccount")){
			return "/jsp/website/getaccount";
		}else if(event.equals("regist")){
			return "/jsp/website/regist";
		}else if(event.equals("exchbonus")){
			return "/jsp/website/getaccount";
		}else{
			return  "/jsp/website/login";
		}
	}
	
	/**
	 * 激活验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/verifyActivate")
	public String verifyActivate(HttpServletRequest request,
			HttpServletResponse response) {
		String name=request.getParameter("name");
		String idcard=request.getParameter("cardnum");
		String dossid=request.getParameter("dossid");
		
		String brand=StringUtil.safeToString(request.getSession().getAttribute("brand"), "");
		Map<String,String> map =new HashMap();
		map.put("name", name);
		map.put("idcard", idcard);
		map.put("doss_id", dossid);
		map.put("brand", brand);
		Org_employee employee = employeeService.checkLogin(map);
		if (null !=employee && employee.getIsactived()==0) {//Isactived=1表示激活，Isactived=2表示锁定，Isactived=0表示未激活
			// 跳转到list列表页面
			if(employee.getDoss_id().isEmpty()){
				request.setAttribute("error", "账号信息不完整，请转注册页面完成信息填写");
				return "/jsp/website/activate";
			}else{
				log.info("当前登录用户" + name);
				String num="";
				for (int i = 0; i <= 5; i++) {
			    	int rd = (int) (Math.random() * 10);
			        num+=String.valueOf(rd);
			    }
				String text="尊敬的用户，您的验证码为：" +num+ "，有效时间为15分钟，使用后即作废。";
				String[] phone =new String[1];
				phone[0]=employee.getPhone();
				Msgvalidate mvd=new Msgvalidate();
				mvd.setEwid(employee.getEwid());
				mvd.setMsgcode(num);
				mvd.setCreatetime(new Date());
				employeeService.saveVertifyCode(mvd);
				int vid=mvd.getId();
				try {//
					SingletonClient.getClient().sendSMS(phone, text, "",5);
					String error="校验码已发送到你的手机，15分钟内输入有效，请勿泄漏 ";
					request.setAttribute("error", error);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("employee", employee);
				
				request.setAttribute("mvid", mvd.getId());
//				return "/jsp/website/activatenext";
				request.setAttribute("action", "activate");
				return "/jsp/website/sendmsg";
			}
			
		}else if(null !=employee && employee.getIsactived()==1){
			String tip="该帐号已激活，立即<a href=http://" + request.getServerName()+ ":"+ request.getServerPort() + request.getContextPath()+">登陆</a>";
			request.setAttribute("error", tip);
			return "/jsp/website/activate";
		}else if(null !=employee && employee.getIsactived()==2){
			request.setAttribute("error", "账号已锁定，请联系管理员");
			return "/jsp/website/activate";
		}else {
			// 跳转到登陆页面 并且提示错误
			request.setAttribute("error", "当前用户不存在！");
			return "/jsp/website/activate";
		}
	}
	
	/**
	 * 保存激活信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveActivateInfo")
	public void saveActivateInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String phone = StringUtil.safeToString(request.getParameter("phone"),"");
		String email = StringUtil.safeToString(request.getParameter("email"),"");
		String secretQuestion = StringUtil.safeToString(request.getParameter("secretQuestion"),"");
		String secretAnwser = StringUtil.safeToString(request.getParameter("answer"),"");
		String bankname = StringUtil.safeToString(request.getParameter("bankname"),"");
		String openbank = StringUtil.safeToString(request.getParameter("bank"),"");
		String account = StringUtil.safeToString(request.getParameter("banknum"),"");
		String birthday = StringUtil.safeToString(request.getParameter("birthday"),"");
		String username = StringUtil.safeToString(request.getParameter("username"),"");
		String ewid = StringUtil.safeToString(request.getParameter("ewid"),"");
		String pwd = StringUtil.safeToString(request.getParameter("pwd"),"");
		String msg = "";
		
		Org_employee employee = employeeService.getEmployeeByEwid(ewid);
		Secretquestion secretQ_A = new Secretquestion();
		employee.setPhone(phone);
		employee.setMail(email);
		employee.setBankname(bankname);
		employee.setPwd(pwd);
		employee.setOpenbank(openbank);
		employee.setAccountbank(account); 
		if(birthday!="" && birthday!=null){
			employee.setBirthday(DateUtil.String2Date(birthday, "yyyy-MM-dd"));
		}
		employee.setUsername(username);
	    employee.setIsactived(1);
	    
	    if(secretQuestion !="" && secretQuestion!=null &&secretAnwser !="" && secretAnwser!=null){
	    	secretQ_A.setEwid(employee.getEwid());
	    	secretQ_A.setSecretquestion(secretQuestion);
		    secretQ_A.setSecretanwser(secretAnwser);
		    secretQ_A.setCreatetime(new Date());
		    employeeService.saveSecretQandA(secretQ_A);
	    }
	    try{
	    	employee.setUpdatetime(new Date());
	    	employeeService.saveEmployee(employee);
	    	Map map=new HashMap();
	    	map.put("name", employee.getName());
	    	map.put("dealercode", employee.getDealercode());
	    	map.put("idcard", employee.getIdcard());
	    	List<Org_employee> empList=employeeService.searchEmployee(map);
	    	//通过姓名、身份证、经销商代码判断是否已存在，存在则锁定，并将奖金转为当前激活账号的ewid
	    	if(empList.size()>1){
	    		for(Org_employee emp : empList){
	    			if(!emp.getEwid().equals(ewid) && emp.getIsactived()!=2){
	    				emp.setIsactived(2);
	    				emp.setUpdatetime(new Date());
	    				employeeService.saveEmployee(emp);
	    				
	    				List<Bo_bonusdetail> bonusList=bonusService.getBonusbyEWID(emp.getEwid());
	    				for(Bo_bonusdetail bonus : bonusList ){
	    					bonus.setEwid(ewid);
	    					bonusService.saveOrUpdateBonus(bonus);
	    				}
	    			}
	    		}
	    	}
		    msg="激活成功";
	    }catch(Exception e){
	    	e.printStackTrace();
	    	msg="激活失败，请重试";
	    }
    	try {
    		PrintWriter out = response.getWriter();
    		out.println(msg);
    		out.flush();
    		out.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * 密保验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/resetpwd")
	public String resetPwd(HttpServletRequest request,
			HttpServletResponse response) {
		String ewid=request.getParameter("username");
		String secretQuestion = StringUtil.safeToString(request.getParameter("secretQuestion"),"");
		String secretAnwser = StringUtil.safeToString(request.getParameter("answer"),"");
		String brand =StringUtil.safeToString(request.getSession().getAttribute("brand"), "");
		Map<String,String> map =new HashMap();
		Map<String,String> p =new HashMap();
		map.put("username", ewid);
		Org_employee employee = employeeService.serchEmployee(map);
		if(employee!=null){
			p.put("username", employee.getEwid());
			p.put("question", secretQuestion);
			p.put("answer", secretAnwser);
		}else{
			request.setAttribute("error", "当前用户不存在！");
			return "/jsp/website/forgetpwd";
		}
		
		boolean checkQandA=employeeService.checkSecretQandA(p);
		
		if(employee==null || !brand.equals(employee.getBrand())){
			// 跳转到登陆页面 并且提示错误
			request.setAttribute("error", "当前用户不存在！");
			return "/jsp/website/forgetpwd";
		}else if (null !=employee && employee.getIsactived()!=2 && checkQandA) {//Isactived=1表示激活，Isactived=2表示锁定，Isactived=0表示未激活
			// 跳转到resetPwd页面
			request.getSession().setAttribute("id", employee.getId());
			return "/jsp/website/resetpwd";
		}else if(null !=employee && employee.getIsactived()==2){
			request.setAttribute("error", "账号已锁定，不可操作");
			return "/jsp/website/forgetpwd";
		} else{
			request.setAttribute("error", "密保验证信息错误，请重试！");
			return "/jsp/website/forgetpwd";
		}
	}
	
	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveresetpwd")
	public void saveresetpwd(HttpServletRequest request,HttpServletResponse response) {
		String pwd1 = StringUtil.safeToString(request.getParameter("pwd"),"");
		String pwd2 = StringUtil.safeToString(request.getParameter("pwdRepeat"),"");
		String msg = "";
		Org_employee employee = employeeService.getEmployeeById(Integer.parseInt(employeeService.getEmpid(request)));
		if(pwd1.equals(pwd2) && ""!=pwd1 &&null!=pwd1){
			employee.setPwd(pwd1);
			employeeService.saveEmployee(employee);
			msg="密码重置成功";
		}else{
			msg="密码重置失败，请重试";
		}
    	try {
    		PrintWriter out = response.getWriter();
    		out.println(msg);
    		out.flush();
    		out.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * 获取账户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getaccount")
	public String getaccount(HttpServletRequest request,HttpServletResponse response) {
		String name=request.getParameter("name");
		String idcard=request.getParameter("cardnum");
		String dossid=StringUtil.safeToString(request.getParameter("doss_id"), "");
		String dealercode = request.getParameter("code");
		String brand = StringUtil.safeToString(request.getSession().getAttribute("brand"), "");
		String tip="";
		Map<String,String> map =new HashMap();
		Org_employee employee=null;
		if(!"".equals(dossid)){
			map.put("doss_id", dossid);
		}
		if( !"".equals(name) && name!=null && !"".equals(idcard) &&idcard!=null && 
				 !"".equals(dealercode) && dealercode!=null){
			map.put("name", name);
			map.put("idcard", idcard);
			map.put("brand", brand);
			map.put("dealercode", dealercode);
			employee = employeeService.checkLogin(map);
			if(employee!=null){
				if(employee.getDoss_id()!=null && !"".equals(employee.getDoss_id()) && !"未匹配".equals(employee.getDoss_id()) ){
					if("".equals(dossid) ){
						tip="DOSSID存在，不可为空";
						request.setAttribute("tip", tip);
						return "/jsp/website/getaccount";
					}
				}
			}
		}
		
		if (null !=employee && employee.getIsactived()==0) {//Isactived=1表示激活，Isactived=2表示锁定，Isactived=0表示未激活
			// 跳转到list列表页面
			log.info("当前登录用户" + name);
			if("".equals(dossid)){
				tip="该帐号未注册，立即<a href=http://" + request.getServerName()+ ":"+ request.getServerPort() + request.getContextPath()+"/login/zhanghu?brand="+brand +"&event=regist>注册</a>";

			}else{
				tip="该帐号未激活，立即<a href=http://" + request.getServerName()+ ":"+ request.getServerPort() + request.getContextPath()+"/login/zhanghu?brand="+brand +"&event=activate>激活</a>";

			}
			request.setAttribute("employee", employee);
			request.setAttribute("tip", tip);
			return "/jsp/website/getaccount";
		}else if(null !=employee && employee.getIsactived()!=0 ){
			String num="";
			for (int i = 0; i <= 5; i++) {
		    	int rd = (int) (Math.random() * 10);
		        num+=String.valueOf(rd);
		    }
			String text="尊敬的用户，您的验证码为：" +num+ "，有效时间为15分钟，使用后即作废。";
			String[] phone =new String[1];
			phone[0]=employee.getPhone();
			Msgvalidate mvd=new Msgvalidate();
			mvd.setEwid(employee.getEwid());
			mvd.setMsgcode(num);
			mvd.setCreatetime(new Date());
			employeeService.saveVertifyCode(mvd);
			int vid=mvd.getId();
			try {//
				SingletonClient.getClient().sendSMS(phone, text, "",5);
				tip="校验码已发送到你的手机，15分钟内输入有效，请勿泄漏 ";
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			request.setAttribute("employee", employee);
			request.setAttribute("tip", tip);
			request.setAttribute("mvid", mvd.getId());
			return "/jsp/website/sendmsg";
		}else {
			// 跳转到登陆页面 并且提示错误
			tip="当前用户不存在！";
			request.setAttribute("tip", tip);
			return "/jsp/website/getaccount";
		}
	}
	
	@RequestMapping(value = "sentmsg")
	public String msgvalidate(HttpServletRequest request,HttpServletResponse response) {
		String phone = StringUtil.safeToString(request.getParameter("phone"),"");
		String ewid = StringUtil.safeToString(request.getParameter("ewid"),"");
		String[] p=new String[1];
		p[0]=phone;
		String tip = "";
		String num="";
		for (int i = 0; i <= 5; i++) {
	    	int rd = (int) (Math.random() * 10);
	        num+=String.valueOf(rd);
	    }
		String text="尊敬的用户，您的验证码为："+ num+"，有效时间为15分钟，使用后即作废。";
		try {
			SingletonClient.getClient().sendSMS(p, text, "",5);
			tip="校验码已发送到你的手机，15分钟内输入有效，请勿泄漏 ";
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Msgvalidate mvd=new Msgvalidate();
		mvd.setEwid(ewid);
		mvd.setMsgcode(num);
		mvd.setCreatetime(new Date());
		employeeService.saveVertifyCode(mvd);
		request.setAttribute("mvid", mvd.getId());
		request.setAttribute("tip", tip);
    	try {
    		PrintWriter out = response.getWriter();
    		out.println(tip);
    		out.flush();
    		out.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return  "/jsp/website/sendmsg";
	}
	
	@RequestMapping(value="validatemsg")
	public String validatemsg(HttpServletRequest request,HttpServletResponse respond){
		String ewid=request.getParameter("ewid");
		String mvid=request.getParameter("mvid");
		String action=request.getParameter("action");
		String verifycode=request.getParameter("verifycode");
		Calendar can = Calendar.getInstance();
		can.add(Calendar.MINUTE, -15);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(can.getTime());
		Org_employee employee =employeeService.getEmployeeByEwid(ewid);
		String tip="";
		Map map=new HashMap();
		map.put("id", mvid);
		map.put("time", time);
		Msgvalidate validateInfo = employeeService.getVertifyCode(map);
		if(verifycode.equals(validateInfo.getMsgcode())){
			request.setAttribute("employee", employee);
			if(action.equals("activate")){
				return "/jsp/website/activatenext";
			}else if(action.equals("regist")){
				return "/jsp/website/registnext";
			}else{
				tip="立即<a href=http://" + request.getServerName()+ ":"+ request.getServerPort() + request.getContextPath()+">登陆</a>";
				request.setAttribute("tip", tip);
				return "/jsp/website/showuidpwd";
			}
		}else{
			tip = "您输入的验证码错误请重试";
			if(action.equals("regist")){
				request.setAttribute("action", "regist");
			}else if(action.equals("activate")){
				request.setAttribute("action", "activate");
			}
			request.setAttribute("employee", employee);
			request.setAttribute("tip", tip);
			request.setAttribute("mvid", mvid);
			return "/jsp/website/sendmsg";
		}
	}
	@RequestMapping(value="/validatebomsg")
	public String validateBomsg(HttpServletRequest request,HttpServletResponse respond){
		String ewid=request.getParameter("ewid");
		String mvid=request.getParameter("mvid");
		//String action=request.getParameter("action");
		String verifycode=request.getParameter("verifycode");
		Calendar can = Calendar.getInstance();
		can.add(Calendar.MINUTE, -15);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(can.getTime());
		Org_employee employee =employeeService.getEmployeeByEwid(ewid);
		String tip="";
		Map map=new HashMap();
		map.put("id", mvid);
		map.put("time", time);
		Msgvalidate validateInfo = employeeService.getVertifyCode(map);
		if(!"".equals(verifycode)){
			if(verifycode.equals(validateInfo.getMsgcode())){
				request.setAttribute("employee", employee);
				return "/jsp/website/exchbonus";
			}else{
				tip = "您输入的验证码错误请重试";
				request.setAttribute("employee", employee);
				request.setAttribute("tip", tip);
				request.setAttribute("mvid", mvid);
				return "/jsp/website/sendmsg";
			}
		}else{
			return "/jsp/website/sendmsg";
		}
	}
	
	@RequestMapping(value = "/tojyh")
	public String tojyh(HttpServletRequest request, HttpServletResponse response) {	
		Map emp =(Map)request.getSession().getAttribute("emp");
		if(null==emp){
			return "/jsp/website/login";
		}else{
		String dossid = (String) emp.get("doss_id");
		String time = StringUtil.safeToString(System.currentTimeMillis(), "");
		String sign = MD5Util.mmd5(dossid + time);
		String redirect_uri = StringUtil.safeToString(request.getParameter("redirect_uri"), "http://sgmewelite.com/");
		StringBuffer p = new StringBuffer();
		p.append("doss_id=").append(dossid).append("&timestamp=").append(time)
				.append("&sign=").append(sign).append("&redirect_uri=").append(
						redirect_uri);
//		sendPost("http://114.80.203.124:8080/chevrolet/loginFromYB.do", p
//				.toString());
		String jyhurl2= SettingUtil.getSetting("jyhurl2");
		
		log.info("sid1--------" +request.getSession().getId());
		return "redirect:"+jyhurl2+"?"+p;
		}
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/innerlogin")
	public String innerlogin(HttpServletRequest request, HttpServletResponse response) {	
		String doss_id =StringUtil.safeToString(request.getParameter("doss_id"), "");
		String timestamp=StringUtil.safeToString(request.getParameter("timestamp"), "");
		String sign=StringUtil.safeToString(request.getParameter("sign"), "");
		String redirect_uri=StringUtil.safeToString(request.getParameter("redirect_uri"), "");
		request.setAttribute("redirect_uri",redirect_uri);
		String s = MD5Util.mmd5(doss_id + timestamp);
		if(s.equals(sign)){
			//根据dossid 获取对象
			Org_employee employee= employeeService.getEmployeeByDossidChevy(doss_id);
			if(null!=employee){
				log.info("innerlogin当前登录用户" + employee.getName());
				log.info("sid2--------"+request.getSession().getId());
				
				request.getSession().setAttribute("username", employee.getName());
				request.getSession().setAttribute("id", employee.getId());
				Map emp= BeanUtils.BeanToMap(employee);
				request.getSession().setAttribute("emp", emp);
				return "redirect:/index/home";
			}else {
				//return "redirect:"+redirect_uri;
				return "/jsp/website/innerlogin";
			}
		}else{
			//return "redirect:"+redirect_uri;
			return "/jsp/website/innerlogin";
		}

	}
	/**
	 * 提交修改申请信息
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/rewardchange")
	public void submitApply(HttpServletRequest request,HttpServletResponse response) {
		
		String oldewid = StringUtil.safeToString(request.getParameter("oldewid"), "");
		String newewid = StringUtil.safeToString(request.getParameter("newewid"), "");
		String msg="0";
		Calendar cal = Calendar.getInstance();
		Date apply_date = cal.getTime();
		//Org_employee oldemp = employeeService.getEmployeeByEwid(oldewid);
		Org_employee newemp = employeeService.getEmployeeByEwid(newewid);
		int isactived = newemp.getIsactived();
        if(newemp==null){
			msg="2";
			//request.setAttribute("tip", "新的ewid 不存在，请您再次确认，谢谢！");
		}else{
		    if(isactived == 2){
		        msg = "4";
		        //request.setAttribute("tip", "新的ewid已锁定，请您联系系统管理员，谢谢！");
		    }else{
    			String before_name = request.getParameter("orgname");
    			String before_idcard = request.getParameter("orgidcard");
    			String before_dossId = request.getParameter("orgdossid");
    			String before_dealercode = request.getParameter("dealercode");;
    			String before_position = request.getParameter("orgposition");
    			String after_name = newemp.getName();
    			String after_idcard = newemp.getIdcard();
    			String after_dossId = newemp.getDoss_id();
    			String after_dealercode = newemp.getDealercode();
    			String after_phone = newemp.getPhone();
    			String after_position = newemp.getPosition();
    			Modify_name applyInfo = new Modify_name();
    			applyInfo.setEwid(oldewid);
    			applyInfo.setAfter_ewId(newewid);
    			applyInfo.setDealercode(before_dealercode);
    			applyInfo.setBefore_name(before_name);
    			applyInfo.setAfter_name(after_name);
    			applyInfo.setBefore_idcard(before_idcard);
    			applyInfo.setAfter_idcard(after_idcard);
    			applyInfo.setBefore_dossid(before_dossId);
    			applyInfo.setAfter_dossid(after_dossId);
    			applyInfo.setBefore_position(before_position);
    			applyInfo.setAfter_position(after_position);
    			applyInfo.setApply_date(apply_date);
    			applyInfo.setInfo_type(1);
    			applyInfo.setExBonusPhone(after_phone);
    			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    			/** 构建图片保存的目录 **/
    			String logoPathDir = "/images/website/proveimg";
    			/** 得到图片保存目录的真实路径 **/
    			String logoRealPathDir = request.getSession().getServletContext()
    					.getRealPath(logoPathDir);
    			/** 根据真实路径创建目录 **/
    			File logoSaveFile = new File(logoRealPathDir);
    			if (!logoSaveFile.exists())
    				logoSaveFile.mkdirs();
    			/** 页面控件的文件流 **/
    			MultipartFile multipartFile = multipartRequest.getFile("prove_img");
    			/** 获取文件的后缀 **/
    			long size=multipartFile.getSize()/1024;
    			
    			
    			String suffix = multipartFile.getOriginalFilename().substring(
    					multipartFile.getOriginalFilename().lastIndexOf("."));
    			/** 获取用户名 **/
    			// /**使用UUID生成文件名称**/
    			// String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
    			// String logImageName = multipartFile.getOriginalFilename();
    			String logImageName = oldewid +"_rewardchange" +suffix;
    			/** 拼成完整的文件保存路径加文件 **/
    			String fileName = logoRealPathDir + File.separator + logImageName;
    			System.out.println(fileName);
    			File file = new File(fileName);
    			try {
    				if(size<2000){
    					multipartFile.transferTo(file);
    					String img_Path = logoPathDir + "/" + logImageName;
    					applyInfo.setProve_img(img_Path);
    					applyInfo.setRemark("该用户申请将账户：" +oldewid +"的奖金转到"+newewid+"账户下");
    					employeeService.saveApplyInfo(applyInfo);
    					msg = "1";
    				}else{
    					msg="3";//图片太大不能上传
    				}
    			} catch (IllegalStateException e) {
    				msg="3";
    				e.printStackTrace();
    			} catch (IOException e) {
    				msg="0";
    				e.printStackTrace();
    			}
    		}
		}
		try {
			Map rmap=new HashMap();
			rmap.put("data", msg);
			JSONObject resultJSON = JSONObject.fromObject(rmap);
			PrintWriter out = response.getWriter();
	    	out.print(resultJSON);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	@RequestMapping (value="/verifyBonus")
	public String verifyExchBonus(HttpServletRequest request,
			HttpServletResponse response) {
		String name=request.getParameter("name");
		String idcard=request.getParameter("cardnum");
		String ewid=request.getParameter("orgewid");		
		String brand=StringUtil.safeToString(request.getSession().getAttribute("brand"), "");
		Map<String,String> map =new HashMap();
		map.put("name", name);
		map.put("idcard", idcard);
		map.put("ewid", ewid);
		map.put("brand", brand);	
		Org_employee employee = employeeService.checkLogin(map);
		//int isactive = employee.getIsactived();
		String url = "";								
		if (null != employee) {
		    List<Bo_bonusdetail> bonuslist = bonusService.getBonusbyEWID(ewid);
		    if(bonuslist.size() > 0){
					log.info("当前登录用户" + name);
					String num="";
					for (int i = 0; i <= 5; i++) {
				    	int rd = (int) (Math.random() * 10);
				        num+=String.valueOf(rd);
				    }
					String text="尊敬的用户，您的验证码为：" +num+ "，有效时间为15分钟，使用后即作废。";
					String[] phone =new String[1];
					phone[0]=employee.getPhone();
					//phone[0]="13020225459";
					Msgvalidate mvd=new Msgvalidate();
					mvd.setEwid(employee.getEwid());
					mvd.setMsgcode(num);
					mvd.setCreatetime(new Date());
					employeeService.saveVertifyCode(mvd);
					int vid=mvd.getId();
					try {//
						SingletonClient.getClient().sendSMS(phone, text, "",5);
						String tip="校验码已发送到你的手机，15分钟内输入有效，请勿泄漏 ";
						request.setAttribute("tip", tip);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("employee", employee);
					request.setAttribute("mvid", vid);
					//request.setAttribute("action", "activate");
					url = "/jsp/website/sendmsg";
				
		    }else{
	            request.setAttribute("tip","您账号" + ewid +"下面目前没有奖金信息，请您确认原EWID："+ewid+"信息是否有误");
	            url = "/jsp/website/getaccount";
	        }
	    }else{
			// 跳转到登陆页面 并且提示错误
			request.setAttribute("tip", "当前用户不存在，请您确认您填写的信息是否正确！");
			url = "/jsp/website/getaccount";
		}		
		return url;
	}	
}
