package com.web.controller.employee;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.web.entity.Upload;
import com.web.service.UploadService;


@Controller
@RequestMapping("/userdownload")
public class UserDownloadController {
	private static final Log log = LogFactory.getLog(UserDownloadController.class);
	@Autowired
	public	UploadService uploadService; 
	/**
	 * 查询相关资料
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listdatum")
	public String listdatum(HttpServletRequest request, HttpServletResponse response) {

		
		String type=request.getParameter("type");
		Map<String, String> map=new HashMap<String, String>();
		map.put("type", type);
		Map emp =(Map)request.getSession().getAttribute("emp");
	  	String brand=(String) emp.get("brand");
		map.put("brand", brand);
		List<Map> list = uploadService.listAllupload(map);
		request.setAttribute("list", list);
		request.setAttribute("type", type);
		
		return "/jsp/website/listdatum";
	}

	@RequestMapping(value = "/showdatum")
	public String showdatum(HttpServletRequest request, HttpServletResponse response) {

		  String id=request.getParameter("id");
		   Upload upload=  uploadService.getuploadInfoById(Integer.parseInt(id));
		   
		   
		    if(null!=upload){
		    	  
		    	//  request.setAttribute("utype", upload.getUtype());
		    	  request.setAttribute("getAddname", upload.getAddname());
		    	  request.setAttribute("getRoute", upload.getRoute());
		      }
		return "/jsp/website/showcontent";

	}
	
	/**
	 * 下载文件 通用 按照编号识别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("download")
	public void download_1(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		log.info("下载文件--id----" + id);
		Upload upload = uploadService.getuploadInfoById(Integer
				.parseInt(id));
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "doc" + "/";
		String fileName = upload.getAddname();
		// 当前文件路径
		String nowPath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "doc" + "/" + upload.getRoute();
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
	
	/**
	 * 查询用户手册
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listmanual")
	public String listmanual(HttpServletRequest request, HttpServletResponse response) {
		 
		return "/jsp/website/listmanual";
	}
	

}
