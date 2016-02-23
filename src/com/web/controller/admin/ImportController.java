package com.web.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.utils.DataImport;
import com.utils.StringUtil;
import com.web.dao.EmpImportDao;
import com.web.entity.Admin;
import com.web.entity.Distributor;
import com.web.entity.Modify_name;
import com.web.entity.Org_employee;
import com.web.service.EmployeeService;
import com.web.service.ImportService;

@Controller 
@RequestMapping (value = "/admin/import")
public class ImportController {
	
	@Autowired EmployeeService  empService;
	
	@Autowired ImportService impService;
	
	@RequestMapping (value = "/showimport")
	public String showImport(HttpServletRequest request,
			HttpServletResponse response){
		return "/jsp/proprietor/showimport";
	}
    
	@RequestMapping (value = "/showbonus")
	public String showBonus(HttpServletRequest request,
			HttpServletResponse response){
		return "/jsp/proprietor/showbonus";
	}
	
	@RequestMapping("download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "template" + "/";
		String fileName = request.getParameter("file");
		String filePath = path + "";
		// 当前文件路径
		String nowPath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "template" + "/" + fileName;
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
	 * 导入人员信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value="/uploadata")
	public String uploadEMP(HttpServletRequest request,HttpServletResponse response){
		this.uploadData(request, response,"/datas/empdata");
		return "redirect:importemp";
	}
	/**
	 * 导入奖金
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value="/uploadbonus")
	public String uploadBonus(HttpServletRequest request,HttpServletResponse response){
		this.uploadData(request, response,"/datas/bonusdata");
		return "redirect:importbonus";
	}
	
	public void uploadData(HttpServletRequest request,HttpServletResponse response,String dataFilePath) {
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	        /**构建EXCEL保存的目录**/  
	        String dataPathDir = dataFilePath;   
	        /**得到EXCEL保存目录的真实路径**/  
	        String dataRealPathDir = request.getSession().getServletContext().getRealPath(dataPathDir);
	        /**根据真实路径创建目录**/  
	        File dataSaveFile = new File(dataRealPathDir);   
	        if(!dataSaveFile.exists())   
	            dataSaveFile.mkdirs();         
	        /**页面控件的文件流**/  
	        MultipartFile multipartFile = multipartRequest.getFile("uploadedfile");    
	        /**获取文件的后缀**/  
	        String suffix = multipartFile.getOriginalFilename().substring
	        				(multipartFile.getOriginalFilename().lastIndexOf("."));  
	        String dataFileName = multipartFile.getOriginalFilename();
	        //System.out.println("dataFileName=" + dataFileName);
	        /**拼成完整的文件保存路径加文件**/
	        Calendar cal = Calendar.getInstance();
	        Date date = cal.getTime();
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	        String strdate = df.format(date);
	        String fileName = dataRealPathDir + File.separator   + strdate + dataFileName.substring(dataFileName.indexOf("."), dataFileName.length()); 	    
	        File file = new File(fileName); 
	        try {   
	            multipartFile.transferTo(file); 
	            //request.setAttribute("msg", "上传成功，请将数据导入数据库！");
	        } catch (IllegalStateException e) {   
	            e.printStackTrace();   
	        } catch (IOException e) {          
	            e.printStackTrace();   
	        }        	        
		}
       /**
        * Get data from excel 	
        * @param request
        * @param response
        * @return
        * @throws IOException
        */
	public List<Map> importData(HttpServletRequest request, HttpServletResponse response, String dataFilePath)
			throws IOException {
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        /**构建EXCEL保存的目录**/  
        String dataPathDir = dataFilePath;   
        /**得到EXCEL保存目录的真实路径**/  
        String dataRealPathDir = request.getSession().getServletContext().getRealPath(dataPathDir);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strdate = df.format(date);
        String filePath = dataRealPathDir+"/"+strdate+".xls";
        //System.out.println("file path = " + dataRealPathDir+"/"+strdate+".xls");
		// 更新表记录
		DataImport importData = new DataImport();		
		List<Map> list = importData.getDataAuto(filePath, 0);
		return list;
	}
	
	@RequestMapping(value="/importemp")
	public String importEmp(HttpServletRequest request, 
			HttpServletResponse response){
		List<Map> list;
		List<Org_employee> emplist = empService.getEMPWithoutEWID();
		try {
			list = importData(request, response,"/datas/empdata");
			String msg = impService.importEMPS(list);
			impService.setEWID(emplist);
			request.setAttribute("msg", msg);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/proprietor/showimport";		
	}
	/**
	 * 导入奖金信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/importbonus")
	public String importBonus(HttpServletRequest request, 
			HttpServletResponse response){
		List<Map> list;
		try {
			//获取Excel中的数据
			list = importData(request, response,"/datas/bonusdata");
			//导入数据
			String msg = impService.importBonus(list);
			request.setAttribute("msg", msg);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/proprietor/showbonus";		
	}
	
	/**
	 * 上传奖金发放状态清单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping (value="/updatebonus")
    public String updateBonus(HttpServletRequest request,HttpServletResponse response){
        this.uploadData(request, response,"/datas/bonusdata/update");
        return "redirect:updatebo";
    }
	/**
	 * 更新奖金发放状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updatebo")
    public String updateBo(HttpServletRequest request, 
            HttpServletResponse response){
        List<Map> list;
        try {
            //获取Excel中的数据
            list = importData(request, response,"/datas/bonusdata/update");
            //导入数据
            String msg = impService.updateBonus(list);
            request.setAttribute("updatemsg", msg);           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/jsp/proprietor/showbonus";     
    }
}
