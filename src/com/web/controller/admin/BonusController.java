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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.utils.DataImport;
import com.utils.DateUtil;
import com.utils.JxlUtil;
import com.utils.LigerUtils;
import com.utils.StringUtil;
import com.web.service.BonusService;

@Controller
@RequestMapping("/admin/bonus")
public class BonusController {
	private static final Log log = LogFactory.getLog(BonusController.class);

	@Autowired
	public BonusService bonusService;

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
		List<Map> bList = bonusService.searchBatchs(new HashMap());
		request.setAttribute("blist", bList);

		return "/jsp/proprietor/listbonus";
	}

	/**
	 * 奖金列表控件调用的方法
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchbonus", method = RequestMethod.POST)
	public void searchbonus(HttpServletRequest request,
			HttpServletResponse response) {
		String page = StringUtil.safeToString(request.getParameter("page"), "");
		String pagesize = StringUtil.safeToString(request
				.getParameter("pagesize"), "");

		String batch = StringUtil.safeToString(request.getParameter("batch"),
				"");
		String type = StringUtil.safeToString(request.getParameter("type"), "");
		String dealer = StringUtil.safeToString(request.getParameter("dealer"),
				"");
		String dealercode = StringUtil.safeToString(request
				.getParameter("dealercode"), "");
		String status = StringUtil.safeToString(request.getParameter("status"),
				"");
		String name = StringUtil
				.safeToString(request.getParameter("pname"), "");

		String start_date = StringUtil.safeToString(request
				.getParameter("start_date"), "");
		String end_date = StringUtil.safeToString(request
				.getParameter("end_date"), "");
		String ewid = StringUtil.safeToString(request.getParameter("ewid"), "");
		try {
			Map<String, String> p = new HashMap<String, String>();
			p.put("batch", batch);
			p.put("type", type);
			p.put("dealer", dealer);
			p.put("dealercode", dealercode);
			p.put("status", status);
			p.put("name", name);
			p.put("ewid", ewid);
			p.put("begintime", start_date);
			p.put("endtime", end_date);
			p.put("firstrs", String.valueOf(Integer.valueOf(page)
					* Integer.valueOf(pagesize) - Integer.valueOf(pagesize)));
			p.put("maxrs", pagesize);
			List<Map> emp = bonusService.searchBonusdetail(p);
			PrintWriter out = response.getWriter();
			out.println(LigerUtils.getJsonMap(emp, bonusService
					.countBonusdetail(p)));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 奖金列表控件调用的方法
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/removebonus", method = RequestMethod.POST)
	public void removebonus(HttpServletRequest request,
			HttpServletResponse response) {
		String ids = request.getParameter("ids");

		String[] idss = ids.split(",");
		for (int i = 0; i < idss.length; i++) {
			bonusService.delBonusById(idss[i]);
		}
	}

	@RequestMapping(value = "/exportbonus")
	public void exportBonus(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("导出数据");
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "template" + "/";
		String f1 = path + "bonus_list.xls";

		// String brands =
		// StringUtil.safeToString(request.getParameter("brands"), "");
		String batch = request.getParameter("batch");

		String type = StringUtil.safeToString(request.getParameter("type"), "");
		String status = StringUtil.safeToString(request.getParameter("status"),
				"");

		String start_date = StringUtil.safeToString(request
				.getParameter("start_date"), "");
		String dealercode = StringUtil.safeToString(request
				.getParameter("dealercode"), "");
		String pname = StringUtil.safeToString(request.getParameter("pname"),
				"");
		String dealer = StringUtil.safeToString(request.getParameter("dealer"),
				"");
		String end_date = StringUtil.safeToString(request
				.getParameter("end_date"), "");

		String d = DateUtil.getcurrentDatetime("yyyyMMdd");
		String f2 = path + d + ".xls";
		Map para = new HashMap();
		para.put("batch", batch);
		para.put("dealercode", dealercode);
		para.put("type", type);
		para.put("pname", pname);
		para.put("dealer", dealer);
		para.put("begintime", start_date);
		para.put("endtime", end_date);

		// // 如果登陆的不是管理员 查询条件固定经销商
		// String username = StringUtil.safeToString(request.getSession()
		// .getAttribute("username"), "");
		// if (!"admin".equals(username)) {
		// para.put("dealercode", username);
		// }
		JxlUtil.writeExcel(f1, f2, bonusService.exportBonusdetail(para), 0, 1);
		String fileName = "bonus_list_" + d + ".xls";
		// 当前文件路径
		String nowPath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "template" + "/" + d + ".xls";
		response.setContentType("application/vnd.ms-excel");
		File file = new File(nowPath);

		// 清空response
		response.reset();
		try {
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gbk"), "iso-8859-1")); // 转码之后下载的文件不会出现中文乱码
			response.addHeader("Content-Length", "" + file.length());
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

	@RequestMapping(value = "/updatebonus")
	public String updatebonus(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "更新成功";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			/** 构建EXCEL保存的目录 **/
			String dataPathDir = "/datas/bonusdata";
			/** 得到EXCEL保存目录的真实路径 **/
			String dataRealPathDir = request.getSession().getServletContext()
					.getRealPath(dataPathDir);
			/** 根据真实路径创建目录 **/
			File dataSaveFile = new File(dataRealPathDir);
			if (!dataSaveFile.exists())
				dataSaveFile.mkdirs();
			/** 页面控件的文件流 **/
			MultipartFile multipartFile = multipartRequest
					.getFile("uploadedfile");
			/** 获取文件的后缀 **/
			if (null != multipartFile.getOriginalFilename()
					&& !"".equals(multipartFile.getOriginalFilename())) {
				String suffix = multipartFile.getOriginalFilename().substring(
						multipartFile.getOriginalFilename().lastIndexOf("."));
				String dataFileName = multipartFile.getOriginalFilename();
				// System.out.println("dataFileName=" + dataFileName);
				/** 拼成完整的文件保存路径加文件 **/
				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String strdate = df.format(date) + "_updatabonus";
				String fileName = dataRealPathDir
						+ File.separator
						+ strdate
						+ dataFileName.substring(dataFileName.indexOf("."),
								dataFileName.length());
				File file = new File(fileName);
				multipartFile.transferTo(file);

				DataImport importData = new DataImport();
				List<Map> list = importData.getDataAuto(fileName, 0);
				for(Map map:list){
					String ewid=StringUtil.safeToString(map.get("0"), "");
					
				}
			} else {
				result = "文件不能为空";
			}

		} catch (Exception e) {
			result = "更新失败";
		}
		request.setAttribute("updatemsg", result);
		return "/jsp/proprietor/showbonus";
	}
}
