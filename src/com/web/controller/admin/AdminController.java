package com.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.utils.SettingUtil;
import com.utils.StringUtil;

import com.web.entity.Admin;
import com.web.service.AdminService;




@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Log log = LogFactory.getLog(AdminController.class);
	
	@Autowired
	public AdminService adminService;

//	@RequestMapping(value = "/login")
//	public String login_test(HttpServletRequest request, HttpServletResponse response) {
//		String name = StringUtil.safeToString(request.getParameter("name"),
//				"");
//		String pwd = StringUtil.safeToString(request.getParameter("password"),
//				"");
//		if("admin".equals(name)){
//			request.getSession().setAttribute("username", "admin");
//			return "redirect:/admin/home/index";
//		}
//		return "/jsp/proprietor/login";
//	}
	
	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String name = StringUtil.safeToString(request.getParameter("username"),
				"");
		String pwd = StringUtil.safeToString(request.getParameter("password"),
				"");
		String info = StringUtil
				.safeToString(request.getParameter("error"), "");
		String wether_open = SettingUtil.getSetting("open_login");
		// System.out.println(name);
		// System.out.println(psw);
		Map map = new HashMap<String, String>();
		map.put("username", name);
		map.put("pwd", pwd);
		log.info("登录-------------------");

//		if ("1".equals(info)) {
//			request.setAttribute("error", "登陆超时或登录异常，请重新登陆");
//			return "/jsp/proprietor/login";
//		}
//
//		if ("".equals(name)) {
//			request.setAttribute("error", "用户名不能为空");
//			return "/jsp/proprietor/login";
//		}
//		if ("".equals(pwd)) {
//			request.setAttribute("error", "密码不能为空");
//			return "/jsp/proprietor/login";
//		}
		if(!name.isEmpty() && !pwd.isEmpty()){
			Admin admin =adminService.checkLogin(map);
			if (null !=admin) {
				// 跳转到list列表页面
				log.info("当前登录用户" + name);
				//Admin admin = adminService.getAdminByUsername(name);
				request.getSession().setAttribute("adminid", admin.getId());
				request.getSession().setAttribute("adminname", admin.getName());
				request.getSession().setAttribute("username", name);
				return "redirect:/admin/home/index";
			} else {
				// 跳转到登陆页面 并且提示错误
				request.setAttribute("error", "用户名或密码错误，请重新输入！");
				return "/jsp/proprietor/login";
			}
		}else{
			return "/jsp/proprietor/login";
		}
		
	}
	
	/**
	 * 显示修改密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showupdatePWD")
	public String showupdatePWD(HttpServletRequest request,
			HttpServletResponse response) {

		String username = StringUtil.safeToString(request.getSession()
				.getAttribute("username"), "");
		request.setAttribute("username", username);

		return "/jsp/proprietor/change_pwd";
	}
	
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/changepwd")
	public String change_pwd(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("username");
		String pwd1 = request.getParameter("password1");
		String pwd2 = request.getParameter("password2");
		String msg = "修改成功 ";
		if (pwd1.equals(pwd2)) {
			Admin admin = adminService.getAdminByUsername(name);
			if (null != admin) {
				admin.setPwd(pwd1);
				adminService.saveAdmin(admin);
				request.setAttribute("issuccess", 1);
			} else {
				msg = "用户不存在";
				request.setAttribute("issuccess", 0);
			}

		} else {
			msg = "两次密码输入不一致，请重新输入";
		}
		request.setAttribute("message", msg);
		return "/jsp/proprietor/modifypwd";
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
		request.getSession().removeAttribute("adminname");
		request.getSession().removeAttribute("name");
		request.getSession().invalidate();
		log.info("清除用户");
		return "/jsp/proprietor/tologin";

	}
	
	/**
	 * 跳转到登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tologin")
	public String tologin(HttpServletRequest request,
			HttpServletResponse response) {

		return "/jsp/proprietor/tologin";

	}
	
	

	@RequestMapping(value = "/demo")
	public String demo(HttpServletRequest request,
			HttpServletResponse response) {

		return "/jsp/proprietor/demo";

	}
	
	/**
	 * 上传文件测试
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/upload")
	public String upload(HttpServletRequest request,HttpServletResponse response) {
		
		String name=request.getParameter("name");
		System.out.println(name);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        /**构建图片保存的目录**/  
        String logoPathDir = "/images/empimg";   
        /**得到图片保存目录的真实路径**/  
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
        /**根据真实路径创建目录**/  
        File logoSaveFile = new File(logoRealPathDir);   
        if(!logoSaveFile.exists())   
            logoSaveFile.mkdirs();         
        /**页面控件的文件流**/  
        MultipartFile multipartFile = multipartRequest.getFile("file");    
        /**获取文件的后缀**/  
        String suffix = multipartFile.getOriginalFilename().substring
        				(multipartFile.getOriginalFilename().lastIndexOf("."));   
//        /**使用UUID生成文件名称**/  
//        String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称   
        String logImageName = multipartFile.getOriginalFilename();
        /**拼成完整的文件保存路径加文件**/  
        String fileName = logoRealPathDir + File.separator   + logImageName;              
        File file = new File(fileName); 
        try {   
            multipartFile.transferTo(file);   
        } catch (IllegalStateException e) {   
            e.printStackTrace();   
        } catch (IOException e) {          
            e.printStackTrace();   
        }   

        try {
			Map rmap=new HashMap();
			rmap.put("data", "11111");
			JSONObject resultJSON = JSONObject.fromObject(rmap);
			PrintWriter out = response.getWriter();
			out.println(resultJSON);
			out.flush();
			out.close();		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "/jsp/proprietor/demo";
        
	}
	
	
	/**
	 * 上传文件测试
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/upload2")
	public String upload2(HttpServletRequest request,HttpServletResponse response) {
		
		//创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断 request 是否有文件上传,即多部分请求
		if(multipartResolver.isMultipart(request)){
			//转换成多部分request  
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
	        String logoPathDir = "/images/empimg";   
	        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);  
			//取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				//取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if(file != null){
					//取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					//如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if(myFileName.trim() !=""){
						System.out.println(myFileName);
						//重命名上传后的文件名
						//String fileName = "demoUpload" + file.getOriginalFilename();
						//定义上传路径
						//String path = "H:/" + fileName;
						String fileName = logoRealPathDir + File.separator+myFileName;  
						File localFile = new File(fileName);
						try {
							file.transferTo(localFile);
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		}
		return "/jsp/proprietor/demo";
	}
	
	@RequestMapping (value = "/showchangpwd")
	public String showChangPwd(HttpServletRequest request,
			HttpServletResponse response){
	    request.setAttribute("issuccess", 0);
		return "/jsp/proprietor/modifypwd";
	}
	
}
