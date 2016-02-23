package com.web.controller.admin;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import com.sun.org.apache.bcel.internal.generic.NEW;
import com.utils.LigerUtils;
import com.utils.StringUtil;
import com.utils.TimeUtil;
import com.web.entity.Doc_news;
import com.web.entity.Doc_relation;
import com.web.entity.Upimg;
import com.web.entity.Upload;
import com.web.service.ContentService;
import com.web.service.UploadService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/admin/Content")
public class ContentController {
	private static final Log log = LogFactory.getLog(HomeController.class);
	@Autowired
	public ContentService contentService;
	@Autowired
	public UploadService uploadService;
	@RequestMapping(value = "/listcontent")
	public String contentlistcontent(HttpServletRequest request, HttpServletResponse response) {

		return "/jsp/proprietor/content/listcontent";

	}

	@RequestMapping(value = "/listupload")
	public String uploadlistController(HttpServletRequest request, HttpServletResponse response) {

		return "/jsp/proprietor/content/listupload";

	}
	
	
	
	@RequestMapping(value = "/listimg")
	public String uploadlistlistimg(HttpServletRequest request, HttpServletResponse response) {

		return "/jsp/proprietor/content/listimg";

	}
	
	
	
	/**
	 * 列表控件调用的方法
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchdocnews", method = RequestMethod.POST)
	public void searchdocnews(HttpServletRequest request,
			HttpServletResponse response) {
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");
		String name = StringUtil.safeToString(request
				.getParameter("name"), "");

		String brand = StringUtil.safeToString(request
				.getParameter("brand"), "");

		String son = StringUtil.safeToString(request
				.getParameter("son"), "");
		String son4 = StringUtil.safeToString(request
				.getParameter("son4"), "");
//		//正则表达式
//        Pattern pp = Pattern.compile("*<!--[endif]-->.*");
//       //测试用的html代码
//        String str = "<html><body>aa<!--[endif]-->bb<!--[endif]--></body></html>";
//        Matcher mm = pp.matcher(str);
//       //去除标签
//       String result = mm.replaceAll("");         
//
//       System.out.println("ashdahsgdhagsdhagdjasgdajsdasjhgdhasgdh"+result);
		

		try {
			Map<String, String> p = new HashMap<String, String>();

			p.put("firstrs", String.valueOf(Integer.valueOf(page)
					* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			p.put("name", name);
			Map pMap=new HashMap();
			p.put("type", "1");//品牌
			Map m1=contentService.searchDoc_relation(p);
			pMap=new HashMap();
			p.put("type", "2");//品牌大区
			Map m2=contentService.searchDoc_relation(p);
			pMap=new HashMap();
			p.put("type", "3");//延保大区
			Map m3=contentService.searchDoc_relation(p);
			p.put("brand", brand);//
			p.put("son", son);//
			p.put("son4", son4);//
			List<Map> emp = contentService.searchDocnews(p);
			for(Map m:emp){
				String id=StringUtil.safeToString(m.get("id"), "");
				m.put("brand", m1.get(id));
				m.put("son1", m2.get(id));
				m.put("son4", m3.get(id));
			}
			
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, contentService.countDocnewsl(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 显示一条需要更新的信息回显jsp页面
	 * 
	 */

	@RequestMapping(value = "/updatcontent")
	public String updatcontent(HttpServletRequest request,
			HttpServletResponse response) {
		String id = StringUtil.safeToString(request.getParameter("id"), "");

		Doc_news doc_news =new Doc_news(); 
//		Doc_relation doc_relation=new Doc_relation();
		if(!"".equals(id) && null!=id){
			doc_news=contentService.getcontentInfoById(Integer.parseInt(id));
			
		} 
		// Shsgm_new shsgmNew = orgService.getPeopleInfoById(4);
		Map<String, String> map=new HashMap<String, String>();
		map.put("did", id);
		   List<Map> list=	contentService.getrelationInfoById(map);
	 String str=  list.toString();
	 System.out.println("asdasd----"+str);

		//   System.out.println(list);
			String str_temp ="";
			if(str.indexOf(",")==-1){
				str_temp=	str.replace("[","['");
				str_temp=	str_temp.replace("]","']");
				System.out.println("asdasgdhasgdhasgdhgasdgasg"+str_temp);
			}else{
			
			int i = 0;
			while(i>=0){
				int j = i;
				i = str.indexOf(",",j+1);
				if(j==0)
					str_temp +="'"+str.substring(j, i)+"'";
				else if(i==-1)
					str_temp +=","+"'"+str.substring(j+1,str.length())+"'";
				else
					str_temp +=","+"'"+str.substring(j+1,i)+"'";
			};
			str_temp=	str_temp.replace("'[","['");
			str_temp=	str_temp.replace("]'","']");
			}
		//	System.out.println(str_temp);
		request.setAttribute("list", str_temp);
		request.setAttribute("doc_news", doc_news);
		request.setAttribute("ewid", setEWid(String.valueOf(doc_news.getId())));
		request.setAttribute("sysdate", TimeUtil.getSysDate());
		return "/jsp/proprietor/content/addcontent";

	}
	
	
	
	
	
	
	
	/**
	 * 添加
	 * 
	 */

	@RequestMapping(value = "/addcontent")
	public String addcontent(HttpServletRequest request,
			HttpServletResponse response) {
		String id = StringUtil.safeToString(request.getParameter("id"), "");

		
		
		
		
		if(!"".equals(id) && null!=id){
			Doc_news		 doc_news = contentService.getcontentInfoById(Integer.parseInt(id));
			request.setAttribute("doc_news", doc_news);
		}	
		Doc_news doc_news =new Doc_news(); 
	//	[id=0, name=null, type=0, content=null, status=0, author=null, createtime=null, utype=0, brand=null, sgmregion=null, sfrregion=null]
		String str_temp ="['asdasdasd','asdas']";

		request.setAttribute("list", str_temp);
		//request.setAttribute("doc_news", doc_news);
	
		request.setAttribute("sysdate", TimeUtil.getSysDate());
		return "/jsp/proprietor/content/addcontent";

	}
	public String setEWid(String id) {
		String result = "";
		if (id.length() == 1) {
			result = "000" + id;
		} else if (id.length() == 2) {
			result = "00" + id;
		} else if (id.length() == 3) {
			result = "0" + id;
		} else {
			result = id;
		}
		return "EW" + result;
	}
	
	
	

	/**
	 * img列表控件调用的方法
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchimg", method = RequestMethod.POST)
	public void searchimg(HttpServletRequest request,
			HttpServletResponse response) {
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");

//		String brand[] =request.getParameterValues("brand");
//		String son[] =request.getParameterValues("son");
//		String son4[] =request.getParameterValues("son4");
//		
//		List<String> brands =new ArrayList<String>();
//		for(String b:brand){
//			brands.add(b);
//		}
//		
//		List<String> sgmr =new ArrayList<String>();
//		for(String s:son){
//			sgmr.add(s);
//		}
//		
//		List<String> sfrr =new ArrayList<String>();
//		for(String s:son4){
//			sfrr.add(s);
//		}

		try {
			Map<String, String> p = new HashMap<String, String>();

			p.put("firstrs", String.valueOf(Integer.valueOf(page)
					* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			List<Map> emp = contentService.searchimg(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, contentService
							.countimgsl(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 显示一条需要更新的信息轮播图信息回显jsp页面
//	 * 
//	 */
//
//	@RequestMapping(value = "/updatimg")
//	public String updatimg(HttpServletRequest request,
//			HttpServletResponse response) {
//		String id = StringUtil.safeToString(request.getParameter("id"), "");
//
//		Upimg upimg=new Upimg();
//		if(!"".equals(id) && null!=id){
//			upimg=contentService.getimgInfoById(Integer.parseInt(id));
//		} 
//		// Shsgm_new shsgmNew = orgService.getPeopleInfoById(4);
//		request.setAttribute("upimg", upimg);
//		request.setAttribute("ewid", setEWid(String.valueOf(upimg.getId())));
//		request.setAttribute("sysdate", TimeUtil.getSysDate());
//		return "/jsp/proprietor/content/listimg";
//
//	}
	
//	
//	/**
//	 * 添加轮播图
//	 * 
//	 */
//
//	@RequestMapping(value = "/addimg")
//	public String addimg(HttpServletRequest request,
//			HttpServletResponse response) {
//		String id = StringUtil.safeToString(request.getParameter("id"), "");
//
//		if(!"".equals(id) && null!=id){
//			Doc_news doc_news = contentService.getcontentInfoById(Integer.parseInt(id));
//			request.setAttribute("doc_news", doc_news);
//		}	
//		
//		return "/jsp/proprietor/content/addimg";
//
//	}	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 更新的信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/update_content")
	public String update_content(HttpServletRequest request,HttpServletResponse response) {

		String id = StringUtil.safeToString(request.getParameter("id"), "");
		
		String author = StringUtil.safeToString(request
				.getParameter("author"), "admin");
		String name =StringUtil.safeToString(request.getParameter("name"), "");
		String content =StringUtil.safeToString(request.getParameter("content"), "");
		String status =StringUtil.safeToString(request.getParameter("status"), "0");
		String type =StringUtil.safeToString(request.getParameter("type"), "1");
		String utype =StringUtil.safeToString(request.getParameter("utype"), "0");	
		String brand[] =request.getParameterValues("brand");
		String son[] =request.getParameterValues("son");
		String son4[] =request.getParameterValues("son4");
		
		
		
		
		
		
		Doc_news doc_news =new Doc_news(); 

		if(!"".equals(id) && null!=id){
			doc_news=contentService.getcontentInfoById(Integer.parseInt(id));
		} 
		
		if("1".equals(utype)){
			
			
		
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	        String logoPathDir = "/pdf";   
	       
	        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
	       
	        File logoSaveFile = new File(logoRealPathDir);   
	        if(!logoSaveFile.exists())   
	            logoSaveFile.mkdirs();         
	       
	        MultipartFile multipartFile = multipartRequest.getFile("file");   
	    	if(multipartFile.getOriginalFilename()==""){
	    	
	    		doc_news.setContent(doc_news.getContent());
	    		
	    	} else{ 
	      
	        String suffix = multipartFile.getOriginalFilename().substring
	        				(multipartFile.getOriginalFilename().lastIndexOf("."));   
//	    
	       String logImageName = UUID.randomUUID().toString()+ suffix;//鏋勫缓鏂囦欢鍚嶇О   
	       // String logImageName = multipartFile.getOriginalFilename();
	    
	        String fileName = logoRealPathDir + File.separator   + logImageName;              
	        File file = new File(fileName); 
	        try {   
	            multipartFile.transferTo(file);   
	        } catch (IllegalStateException e) {   
	            e.printStackTrace();   
	        } catch (IOException e) {          
	            e.printStackTrace();   
	        }   
			doc_news.setContent(logImageName);
		}
		}		else{
			doc_news.setContent(content);
		}

	
	if(doc_news.getUtype()==1){
			
			doc_news.setContent(doc_news.getContent());
		}
		
		
		
		//

		doc_news.setAuthor(author);
		doc_news.setName(name);
		doc_news.setCreatetime(new Date());
		//doc_news.setReleasetime(new Date());
		doc_news.setStatus(Integer.parseInt(status));
		doc_news.setType(Integer.parseInt(type));
//	if(doc_news.getUtype()==1){
//			
//			doc_news.setUtype(doc_news.getUtype());
//		}else{
		doc_news.setUtype(Integer.parseInt(utype));
	//	}
		contentService.savecontentInfoId(doc_news);
		 contentService.delrelation(id);
		if(brand!=null){
		for(String b:brand){
			Doc_relation docRelation=new Doc_relation();
//			if(!"".equals(id) && null!=id){
//				
//				docRelation=contentService.getrelationInfoById(Integer.parseInt(id));
//				
//				
//				
//			} 
			
		
			//docRelation.setId(docRelation.getId());
			docRelation.setType(1);
			docRelation.setName(b);
			
			docRelation.setDid(doc_news.getId());
//			Map<String, String> p = new HashMap<String, String>();
//			p.put("did", id);
//			p.put("name", b);
		    
		
			contentService.savecontentrela(docRelation);
		}
		}
		if(son!=null){
		for(String s:son){
			Doc_relation docRelation=new Doc_relation();
			
//			if(!"".equals(id) && null!=id){
//				
//				docRelation=contentService.getrelationInfoById(Integer.parseInt(id));
//				
//				
//				
//			} 
			docRelation.setType(2);
			docRelation.setName(s);
			docRelation.setDid(doc_news.getId());
			contentService.savecontentrela(docRelation);
		}
		}
		if(son4!=null){
		for(String s:son4){
			Doc_relation docRelation=new Doc_relation();
//				if(!"".equals(id) && null!=id){
//				
//				docRelation=contentService.getrelationInfoById(Integer.parseInt(id));
//				
//				
//				
//			} 
			docRelation.setType(3);
			docRelation.setName(s);
			docRelation.setDid(doc_news.getId());
			contentService.savecontentrela(docRelation);
			request.setAttribute("list", new ArrayList());
			request.setAttribute("info", "0");
			
			if(!"".equals(id)){
				request.setAttribute("doc_news", doc_news);
			}else{
				request.setAttribute("doc_news", new Doc_news());
			}
		}
		}
	//	return "redirect:listcontent";
	
        return "/jsp/proprietor/content/addcontent";

	}
	

	
	/**
	 * 上传列表控件调用的方法
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchupload", method = RequestMethod.POST)
	public void searchupload(HttpServletRequest request,
			HttpServletResponse response) {
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");

		try {
			Map<String, String> p = new HashMap<String, String>();

			p.put("firstrs", String.valueOf(Integer.valueOf(page)
					* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			List<Map> emp = uploadService.searchupload(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, uploadService
							.countupload(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/upload")
	public String upload(HttpServletRequest request,HttpServletResponse response) {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        /**构建图片保存的目录**/  
        String logoPathDir = "/doc";   
        /**得到图片保存目录的真实路径**/  
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
        System.out.println("logoRealPathDir:"+logoRealPathDir);
        /**根据真实路径创建目录**/  
        File logoSaveFile = new File(logoRealPathDir);   
        if(!logoSaveFile.exists())   
            logoSaveFile.mkdirs();         
//        String suffix=logoRealPathDir.substring(logoRealPathDir.lastIndexOf('.'));
//        System.out.println("suffix"+suffix);
        /**页面控件的文件流**/  
        MultipartFile multipartFile = multipartRequest.getFile("file");    
      String luojiname=  multipartFile.getOriginalFilename();
     System.out.println("addname:"+luojiname);//原路径
        /**获取文件的后缀**/  
       String suffix = multipartFile.getOriginalFilename().substring
        				(multipartFile.getOriginalFilename().lastIndexOf("."));
        /**获取用户名**/
      // String username = StringUtil.safeToString(request.getSession()
		//		.getAttribute("username"), "");
//        /**使用UUID生成文件名称**/  
      String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称   
//        String logImageName = multipartFile.getOriginalFilename();
       // String logImageName = username + suffix;
      System.out.println("logImageName"+logImageName);
        /**拼成完整的文件保存路径加文件**/  
        String fileName = logoRealPathDir + File.separator   + logImageName;
        System.out.println(fileName);
        //写入
        File file = new File(fileName); 
        try {   
            multipartFile.transferTo(file);   
        } catch (IllegalStateException e) {   
            e.printStackTrace();   
        } catch (IOException e) {          
            e.printStackTrace();   
        }
       

//		try {
//			PrintWriter out = response.getWriter();
//			out.println("保存成功");
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
	//}
		System.out.println("保存成功");
		 /**保存图片路径到数据库**/
        String docPath = logoPathDir + "/"+logImageName;
      //  Org_employee employee = uploadService.getuploadInfoById();
        		
        System.out.println("docPath:"+docPath);
        
	String id = StringUtil.safeToString(request.getParameter("id"), "");
		
		String author = StringUtil.safeToString(request
				.getParameter("author"), "");
		String name =StringUtil.safeToString(request.getParameter("name"), "");
		String content =StringUtil.safeToString(request.getParameter("content"), "");
		String status =StringUtil.safeToString(request.getParameter("status"), "");
		String type =StringUtil.safeToString(request.getParameter("type"), "");
		String brand =StringUtil.safeToString(request.getParameter("brand"), "");	
		//String brandregion =StringUtil.safeToString(request.getParameter("brandregion"), "");	
	//	String yanbaoregion =StringUtil.safeToString(request.getParameter("yanbaoregion"), "");	
	
    	Upload upload=new Upload();
    	// upload=uploadService.getuploadInfoById(Integer.parseInt(id));
    	upload.setAuthor(author);
		upload.setName(name);
		upload.setReleasetime(new Date());
		upload.setRoute(logImageName);
		upload.setStatus(Integer.parseInt(status));
		upload.setType(Integer.parseInt(type));
		
		upload.setAddname(luojiname);
		upload.setBrand(brand);
//	upload.setBrandregion(1);
	//	upload.setYanbaoregion(1);
	//	upload.setBrandregion(Integer.parseInt(brandregion));
		//upload.setYanbaoregion(Integer.parseInt(yanbaoregion));
		
		
		
		uploadService.saveupload(upload);
		System.out.println("保存上传成功");

		  PrintWriter out=null ;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  out.print("ok");
          out.flush();
          return null;

	}
	@RequestMapping(value = "/delcontent")
	public void delcontent(HttpServletRequest request,
			HttpServletResponse response) {		
		String ids=request.getParameter("ids");

		String[] idss=ids.split(",");
		for(int i=0;i<idss.length;i++){
			contentService.delcontent(idss[i]);
		System.out.println("delcontentdelcontentdelcontent成功了");
		
	}
	}
	@RequestMapping(value = "/delupload")
	public void delupload(HttpServletRequest request,
			HttpServletResponse response) {
	
		
		String upid=request.getParameter("upid");

		String[] upids=upid.split(",");
		for(int i=0;i<upids.length;i++){
			uploadService.delupload(upids[i]);
		System.out.println("成功了");
		
	}
	}
	
	@RequestMapping(value = "/deluploadimg")
	public void deluploadimg(HttpServletRequest request,
			HttpServletResponse response) {
	
		
		String usid=request.getParameter("usid");

		String[] uspid=usid.split(",");
		for(int i=0;i<uspid.length;i++){
			uploadService.deluploadimg(uspid[i]);
		System.out.println("s成功了");
		
	}
	}
	
	
	
	@RequestMapping(value = "/uploadimg")
	public String uploadimg(HttpServletRequest request,HttpServletResponse response) {
		String id = StringUtil.safeToString(request.getParameter("id"), "");
		
//		String author = StringUtil.safeToString(request
//				.getParameter("author"), "");
		String name =StringUtil.safeToString(request.getParameter("name"), "");
		String content =StringUtil.safeToString(request.getParameter("content"), "");
		String status =StringUtil.safeToString(request.getParameter("status"), "");
		String xhid =StringUtil.safeToString(request.getParameter("xhid"), "");
		String brand =StringUtil.safeToString(request.getParameter("brand"), "");
		String imgpage =StringUtil.safeToString(request.getParameter("imgpage"), "");
		String xgqid =StringUtil.safeToString(request.getParameter("xgqid"), "");
		
		
	   	Upimg upimg=new Upimg();
	   
		Map<String, String> map=new HashMap<String, String>();
		map.put("xhid", xhid);
		map.put("brand",brand);
        List<Upimg> list=contentService.getimgxh(map);
 
	
        	

			if(list.size() > 0){
				if(!"".equals(xgqid) && null!=xgqid){
					
				       for(int i=0;i<list.size();i++)
		        		{
				    	   	int oid= 	list.get(i).getId();
				    	   	upimg.setId(oid);
					    	upimg.setName(list.get(i).getName());
					    	upimg.setCreatetime(new Date());
					    	upimg.setImgpage(list.get(i).getImgpage());
					    	upimg.setStatus(Integer.parseInt(status));
					    	upimg.setXhid(Integer.parseInt(xgqid));
							
							upimg.setBrand(brand);
							contentService.saveuploadlistimg(upimg);
		        		}
//					System.out.println("sad++++++++++++");
//					upimg.setXhid(Integer.parseInt(xgqid));
//					upimg=contentService.getimgInfoById(Integer.parseInt(id));
//					upimg.setName(upimg.getName());
//			    	upimg.setCreatetime(new Date());
//			    	upimg.setImgpage(upimg.getImgpage());
//			    	upimg.setStatus(Integer.parseInt(status));
//			
//					
//					upimg.setBrand(brand);
//					
					
					
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
			        /**构建图片保存的目录**/  
			        String logoPathDir = "/topimg";   
			        /**得到图片保存目录的真实路径**/  
			        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
			        System.out.println("logoRealPathDir:"+logoRealPathDir);
			        /**根据真实路径创建目录**/  
			        File logoSaveFile = new File(logoRealPathDir);   
			        if(!logoSaveFile.exists())   
			            logoSaveFile.mkdirs();         
//			        String suffix=logoRealPathDir.substring(logoRealPathDir.lastIndexOf('.'));
//			        System.out.println("suffix"+suffix);
			        /**页面控件的文件流**/  
			        MultipartFile multipartFile = multipartRequest.getFile("file");    
			      String luojiname=  multipartFile.getOriginalFilename();
			     System.out.println("addname:"+luojiname);//原路径
			        /**获取文件的后缀**/  
			       String suffix = multipartFile.getOriginalFilename().substring
			        				(multipartFile.getOriginalFilename().lastIndexOf("."));
			        /**获取用户名**/
			      // String username = StringUtil.safeToString(request.getSession()
					//		.getAttribute("username"), "");
//			        /**使用UUID生成文件名称**/  
			      String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称   
//			        String logImageName = multipartFile.getOriginalFilename();
			       // String logImageName = username + suffix;
			      System.out.println("logImageName"+logImageName);
			        /**拼成完整的文件保存路径加文件**/  
			        String fileName = logoRealPathDir + File.separator   + logImageName;
			        System.out.println(fileName);
			        //写入
			        File file = new File(fileName); 
			        try {   
			            multipartFile.transferTo(file);   
			        } catch (IllegalStateException e) {   
			            e.printStackTrace();   
			        } catch (IOException e) {          
			            e.printStackTrace();   
			        }
			       

//					try {
//						PrintWriter out = response.getWriter();
//						out.println("保存成功");
//						out.flush();
//						out.close();
//					} catch (IOException e) {
//						e.printStackTrace();
				//}
					System.out.println("保存成功");
					 /**保存图片路径到数据库**/
			        String docPath = logoPathDir + "/"+logImageName;
			      //  Org_employee employee = uploadService.getuploadInfoById();
			        		
			        System.out.println("docPath:"+docPath);
			        
				
				
			 
			    	// upload=uploadService.getuploadInfoById(Integer.parseInt(id));
			    //	upload.setAuthor(author);
			    	upimg.setId(Integer.parseInt(id));
			    	upimg.setName(luojiname);
			    	upimg.setCreatetime(new Date());
			    	upimg.setImgpage(logImageName);
			    	upimg.setStatus(Integer.parseInt(status));
			    	upimg.setXhid(Integer.parseInt(xhid));
					
					upimg.setBrand(brand);
				
					
				}else{
			
					System.out.println("asasda");
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
			        /**构建图片保存的目录**/  
			        String logoPathDir = "/topimg";   
			        /**得到图片保存目录的真实路径**/  
			        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
			        System.out.println("logoRealPathDir:"+logoRealPathDir);
			        /**根据真实路径创建目录**/  
			        File logoSaveFile = new File(logoRealPathDir);   
			        if(!logoSaveFile.exists())   
			            logoSaveFile.mkdirs();         
//			        String suffix=logoRealPathDir.substring(logoRealPathDir.lastIndexOf('.'));
//			        System.out.println("suffix"+suffix);
			        /**页面控件的文件流**/  
			        MultipartFile multipartFile = multipartRequest.getFile("file");    
			      String luojiname=  multipartFile.getOriginalFilename();
			     System.out.println("addname:"+luojiname);//原路径
			        /**获取文件的后缀**/  
			       String suffix = multipartFile.getOriginalFilename().substring
			        				(multipartFile.getOriginalFilename().lastIndexOf("."));
			        /**获取用户名**/
			      // String username = StringUtil.safeToString(request.getSession()
					//		.getAttribute("username"), "");
//			        /**使用UUID生成文件名称**/  
			      String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称   
//			        String logImageName = multipartFile.getOriginalFilename();
			       // String logImageName = username + suffix;
			      System.out.println("logImageName"+logImageName);
			        /**拼成完整的文件保存路径加文件**/  
			        String fileName = logoRealPathDir + File.separator   + logImageName;
			        System.out.println(fileName);
			        //写入
			        File file = new File(fileName); 
			        try {   
			            multipartFile.transferTo(file);   
			        } catch (IllegalStateException e) {   
			            e.printStackTrace();   
			        } catch (IOException e) {          
			            e.printStackTrace();   
			        }
			       

//					try {
//						PrintWriter out = response.getWriter();
//						out.println("保存成功");
//						out.flush();
//						out.close();
//					} catch (IOException e) {
//						e.printStackTrace();
				//}
					System.out.println("保存成功");
					 /**保存图片路径到数据库**/
			        String docPath = logoPathDir + "/"+logImageName;
			      //  Org_employee employee = uploadService.getuploadInfoById();
			        		
			        System.out.println("docPath:"+docPath);
			        
				
				       for(int i=0;i<list.size();i++)
		        		{
				    	   	int oid= 	list.get(i).getId();
				    	   	upimg.setId(oid);
					    	upimg.setName(luojiname);
					    	upimg.setCreatetime(new Date());
					    	upimg.setImgpage(logImageName);
					    	upimg.setStatus(Integer.parseInt(status));
					    	upimg.setXhid(Integer.parseInt(xhid));
							
							upimg.setBrand(brand);
		        		}
			 
			    	// upload=uploadService.getuploadInfoById(Integer.parseInt(id));
			    //	upload.setAuthor(author);
//			        upimg.setId(upimg.getId());
//			    	upimg.setName(luojiname);
//			    	upimg.setCreatetime(new Date());
//			    	upimg.setImgpage(logImageName);
//			    	upimg.setStatus(Integer.parseInt(status));
//			    	upimg.setXhid(Integer.parseInt(xhid));
//					
//					upimg.setBrand(brand);
					
				}
				
				
				
				
			}else{
				
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
		        /**构建图片保存的目录**/  
		        String logoPathDir = "/topimg";   
		        /**得到图片保存目录的真实路径**/  
		        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
		        System.out.println("logoRealPathDir:"+logoRealPathDir);
		        /**根据真实路径创建目录**/  
		        File logoSaveFile = new File(logoRealPathDir);   
		        if(!logoSaveFile.exists())   
		            logoSaveFile.mkdirs();         
//		        String suffix=logoRealPathDir.substring(logoRealPathDir.lastIndexOf('.'));
//		        System.out.println("suffix"+suffix);
		        /**页面控件的文件流**/  
		        MultipartFile multipartFile = multipartRequest.getFile("file");    
		      String luojiname=  multipartFile.getOriginalFilename();
		     System.out.println("addname:"+luojiname);//原路径
		        /**获取文件的后缀**/  
		       String suffix = multipartFile.getOriginalFilename().substring
		        				(multipartFile.getOriginalFilename().lastIndexOf("."));
		        /**获取用户名**/
		      // String username = StringUtil.safeToString(request.getSession()
				//		.getAttribute("username"), "");
//		        /**使用UUID生成文件名称**/  
		      String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称   
//		        String logImageName = multipartFile.getOriginalFilename();
		       // String logImageName = username + suffix;
		      System.out.println("logImageName"+logImageName);
		        /**拼成完整的文件保存路径加文件**/  
		        String fileName = logoRealPathDir + File.separator   + logImageName;
		        System.out.println(fileName);
		        //写入
		        File file = new File(fileName); 
		        try {   
		            multipartFile.transferTo(file);   
		        } catch (IllegalStateException e) {   
		            e.printStackTrace();   
		        } catch (IOException e) {          
		            e.printStackTrace();   
		        }
		       

//				try {
//					PrintWriter out = response.getWriter();
//					out.println("保存成功");
//					out.flush();
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
			//}
				System.out.println("保存成功");
				 /**保存图片路径到数据库**/
		        String docPath = logoPathDir + "/"+logImageName;
		      //  Org_employee employee = uploadService.getuploadInfoById();
		        		
		        System.out.println("docPath:"+docPath);
		        
			
			
		 
		    	// upload=uploadService.getuploadInfoById(Integer.parseInt(id));
		    //	upload.setAuthor(author);
		        if(!"".equals(id) && null!=id){
		        	upimg.setId(Integer.parseInt(id));
		        	
		        }
		    	upimg.setName(luojiname);
		    	upimg.setCreatetime(new Date());
		    	upimg.setImgpage(logImageName);
		    	upimg.setStatus(Integer.parseInt(status));
		    	upimg.setXhid(Integer.parseInt(xhid));
				
				upimg.setBrand(brand);
				
				
					}
					
	contentService.saveuploadlistimg(upimg);
		
	
		System.out.println("保存上传成功");

		  PrintWriter out=null ;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  out.print("ok");
          out.flush();
          return null;

	}
	
	
	


  
}

