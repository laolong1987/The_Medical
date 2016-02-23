package com.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
/**
 * 上传文件
 * @author 高扬
 *
 */
public class UploadUtil {

	/**
	 * 上传文件
	 * @return Map value:
	 */
	public static Map<String,String> upload(HttpServletRequest request,String logoPathDir,String filename,String filesavename){
		Map<String,String> result=new HashMap<String, String>();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        /**构建图片保存的目录**/  
//        String logoPathDir = "/images/empimg";  
        /**得到图片保存目录的真实路径**/  
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);   
        /**根据真实路径创建目录**/  
        File logoSaveFile = new File(logoRealPathDir);   
        if(!logoSaveFile.exists())   
            logoSaveFile.mkdirs();         
        /**页面控件的文件流**/  
        MultipartFile multipartFile = multipartRequest.getFile(filename);    
        /**获取文件的后缀**/  
        String suffix = multipartFile.getOriginalFilename().substring
        				(multipartFile.getOriginalFilename().lastIndexOf("."));
//        /**使用UUID生成文件名称**/  
//        String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称   
//        String logImageName = multipartFile.getOriginalFilename();
        String logImageName = filesavename + suffix;
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
        result.put("logoRealPathDir", logoRealPathDir);//得到图片保存目录的真实路径
        result.put("suffix", suffix);//获取文件的后缀
        result.put("filesavename", filesavename);//文件保存名称
		return result;
	}
}
