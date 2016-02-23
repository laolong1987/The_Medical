package com.web.controller.admin;

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

import com.utils.StringUtil;
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Org_employee;
import com.web.entity.Shsgm_new;
import com.web.service.BonusService;
import com.web.service.EmployeeService;

@Controller
@RequestMapping("/admin/home")
public class HomeController {
	private static final Log log = LogFactory.getLog(HomeController.class);

	@Autowired
	EmployeeService empService;

	@Autowired
	public BonusService bonusService;

	@RequestMapping(value = "/index")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		log.info("进入后台主页----" + request.getSession().getAttribute("name"));
		return "/jsp/proprietor/index";

	}

	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request,
			HttpServletResponse response) {
		empService.updateshemnew("4353", "D98ACT");

		return "/jsp/proprietor/welcome";

	}

	/**
	 * 显示所有人员列表
	 * 
	 */
	@RequestMapping(value = "/listemployee")
	public String showEmployee(HttpServletRequest request,
			HttpServletResponse response) {
		return "/jsp/proprietor/listemployee";
	}

	/**
	 * 原始数据导入到新数据库中
	 */

	@RequestMapping(value = "/managedata")
	public String managedata(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> p = new HashMap();
		p.put("2014年H1第1批", "3");
		p.put("2014年H1第2批", "4");
		p.put("2014年H1补发放", "8");
		p.put("2014年H1第3批", "5");
		p.put("6月-7月销售竞赛奖励第1批", "6");
		p.put("6月-7月销售竞赛奖励补发放", "9");
		p.put("9月-10月销售竞赛奖励", "10");
		p.put("2014H2个人奖励", "1");
		p.put("2014H2个人奖励第一批", "1");
		p.put("Top40优秀经销商单项奖励", "11");
		//		
		// List<Map> bList= bonusService.searchBatchs(new HashMap());
		//		
		// 查询全部临时系统数据
		List<Shsgm_new> list = empService.searchShsgm_new(new HashMap());
		int count = 0;
		// 循环临时表所有数据1:1导入奖金表
		for (Shsgm_new shsgmNew : list) {
			count++;
			// 先通过 dossid 在人员表寻找是否有相同记录
			// 判断是否有DOSSID
			try {
				Org_employee emp = new Org_employee();
				if ("未匹配".equals(shsgmNew.getDossid())
						|| "".equals(shsgmNew.getDossid())
						|| null == shsgmNew.getDossid()) {
					// 通过身份证，经销商代码，姓名 二次寻找人员数据
					Map map = new HashMap();
					map.put("idcard", shsgmNew.getIdcard());
					map.put("dealercode", shsgmNew.getDealercode());
					map.put("name", shsgmNew.getPname());
					Org_employee emp2 = empService.serchEmployee(map);
					if (null != emp2) {
						// 匹配成功赋值
						emp = emp2;
					} else {
						// 创建新人员
						// 添加新的EWID
						emp.setEwid(empService
								.getEWID(shsgmNew.getDealercode()));
						// 添加其余属性
						emp.setBrand(shsgmNew.getBrand());
						emp.setDealercode(shsgmNew.getDealercode());
						emp.setDealer(shsgmNew.getName());
						emp.setName(shsgmNew.getPname());
						emp.setPosition(shsgmNew.getPosition());
						emp.setIdcard(shsgmNew.getIdcard());
						emp.setIspass(1);
						emp.setPhone(shsgmNew.getPhonenum());
						emp.setBankname(shsgmNew.getBankname());
						emp.setOpenbank(shsgmNew.getOpenbank());
						emp.setAccountbank(shsgmNew.getAccountbank());
						emp.setDoss_id(shsgmNew.getDossid());
						emp.setCreatetime(new Date());
						emp.setUpdatetime(new Date());
						emp.setAge(0);
						// 添加完成保存
						empService.saveEmployee(emp);
					}
				} else {
					// 有dossid直接获取对象
					emp = empService.getEmployeeByDossid(shsgmNew.getDossid());
					if (null == emp) {
						// 创建新人员
						// 添加新的EWID
						emp = new Org_employee();
						emp.setEwid(empService
								.getEWID(shsgmNew.getDealercode()));
						// 添加其余属性
						emp.setBrand(shsgmNew.getBrand());
						emp.setDealercode(shsgmNew.getDealercode());
						emp.setDealer(shsgmNew.getName());
						emp.setName(shsgmNew.getPname());
						emp.setPosition(shsgmNew.getPosition());
						emp.setIdcard(shsgmNew.getIdcard());
						emp.setPhone(shsgmNew.getPhonenum());
						emp.setDoss_id(shsgmNew.getDossid());
						emp.setBankname(shsgmNew.getBankname());
						emp.setOpenbank(shsgmNew.getOpenbank());
						emp.setIspass(1);
						emp.setAccountbank(shsgmNew.getAccountbank());
						emp.setDoss_id(emp.getDoss_id());
						emp.setCreatetime(new Date());
						emp.setUpdatetime(new Date());
						emp.setAge(0);
						// 添加完成保存
						empService.saveEmployee(emp);
					}
				}
				// 批次枚举

				// 人员获取EWID 绑定奖金表
				// 添加奖金表数据
				Bo_bonusdetail boBonusdetail = new Bo_bonusdetail();
				boBonusdetail.setEwid(emp.getEwid());
				// 设置其余数据
				boBonusdetail.setSalesnum(StringUtil.safeToString(shsgmNew
						.getSalesnum(), ""));
				boBonusdetail.setPeople_num(shsgmNew.getPeople_num());
				boBonusdetail.setEw_sales(shsgmNew.getEw_sales());
				boBonusdetail.setH1(shsgmNew.getH1());
				boBonusdetail.setGrantpercent(shsgmNew.getGrantpercent());
				boBonusdetail.setPrice(Double.parseDouble(shsgmNew
						.getEnd_grantmoney()));
				boBonusdetail.setCreatetime(shsgmNew.getCreatetime());
				boBonusdetail.setUpdatetime(shsgmNew.getCreatetime());
				for (String key : p.keySet()) {
					if (key.equals(shsgmNew.getNewbatch())) {
						boBonusdetail.setBatch(Integer.parseInt(p.get(key)));
					}
				}
				boBonusdetail.setRank(shsgmNew.getRank());
				boBonusdetail.setType(shsgmNew.getType());
				if ("2".equals(shsgmNew.getState())) {
					boBonusdetail.setStatus(0);
				} else if ("3".equals(shsgmNew.getState())) {
					boBonusdetail.setStatus(2);
				} else {
					boBonusdetail.setStatus(Integer.parseInt(shsgmNew
							.getState()));
				}

				boBonusdetail.setGrant_people_num(shsgmNew
						.getGrant_people_num());
				boBonusdetail.setReason(shsgmNew.getReason());
				boBonusdetail.setDealercode(shsgmNew.getDealercode());
				boBonusdetail.setPosition(shsgmNew.getPosition());
				boBonusdetail.setDealer(shsgmNew.getName());
				// 保存奖金表数据
				bonusService.saveOrUpdateBonus(boBonusdetail);

				System.out.println(count);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(count);
			}

		}

		request.setAttribute("msg", "完成");
		return "/jsp/proprietor/listemployee";
	}

	/**
	 * 原始数据导入到新数据库中
	 */

	@RequestMapping(value = "/upmanagedata")
	public String upmanagedata(HttpServletRequest request,
			HttpServletResponse response) {

		//		
		// 查询全部临时系统数据
		List<Shsgm_new> list = empService.searchShsgm_new(new HashMap());
		return "/jsp/proprietor/listemployee";
	}

	/**
	 * 原始数据导入到新数据库中
	 */

	@RequestMapping(value = "/matchdata")
	public String matchData(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map> list1 = empService.searchShsgm_newToMap(new HashMap());

		List<Map> list2 = empService.searchMatchdata(new HashMap());

		List<Map> list3 = empService.searchShsgm_newToMap2(new HashMap());

		List<Map> list4 = empService.searchdealer(new HashMap());

		List<Map> list5 = empService.searchShsgm_newToMap3(new HashMap());

		List<Map> list6 = empService.searchShsgm_newToMap4(new HashMap());

		Map<String, String[]> map = new HashMap<String, String[]>();

		int a = 0; // 在2中找到的用户
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;

		for (Map m : list4) {
			String dealercode = StringUtil
					.safeToString(m.get("dealercode"), "");
			String dealershortname = StringUtil.safeToString(m
					.get("dealershortname"), "");
			String brand = StringUtil.safeToString(m.get("brand"), "");
			String[] arr = new String[2];
			arr[0] = dealershortname;
			arr[1] = brand;
			map.put(dealercode, arr);
		}
		for (Map m1 : list2) {
			String dealercode = StringUtil.safeToString(m1.get("dealercode"),
					"");
			String name = StringUtil.safeToString(m1.get("pName"), "");
			String idc = StringUtil.safeToString(m1.get("idCard"), "");
			String job = StringUtil.safeToString(m1.get("job"), "");
			String phone = StringUtil.safeToString(m1.get("phone"), "");
			String dossid = StringUtil.safeToString(m1.get("dossId"), "");

			String brand = "";
			String dealer = "";
			if (map.containsKey(dealercode)) {
				brand = StringUtil.safeToString(map.get(dealercode)[1], "");
				dealer = StringUtil.safeToString(map.get(dealercode)[0], "");
			}
			System.out.println(a);
			Org_employee emp = new Org_employee();
			emp = empService.getEmployeeByDossid(dossid);
			if (null == emp) {
				emp = new Org_employee();
				emp.setEwid(empService.getEWID(dealercode));
				emp.setBrand(brand);
				emp.setDealercode(dealercode);
				emp.setDealer(dealer);
				emp.setName(name);
				emp.setPosition(job);
				emp.setIdcard(idc);
				emp.setIspass(1);
				emp.setPhone(phone);
				emp.setDoss_id(dossid);
				emp.setCreatetime(new Date());
				emp.setUpdatetime(new Date());
				emp.setAge(0);
				// 添加完成保存
				empService.saveEmployee(emp);
			}
			a++;
			System.out.println(dossid);
		}

		// 在shsgm_new表中自己补全自己的dossid
		// for(Map m1:list5){
		// String name1=StringUtil.safeToString(m1.get("pName"), "");
		// String idc1=StringUtil.safeToString(m1.get("idCard"), "");
		// String code1=StringUtil.safeToString(m1.get("dealercode"), "");
		// String id=StringUtil.safeToString(m1.get("id"), "");
		// for(Map m2:list3){
		// String dossid2=StringUtil.safeToString(m2.get("dossId"), "");
		// String name2=StringUtil.safeToString(m2.get("pName"), "");
		// String idc2=StringUtil.safeToString(m2.get("idCard"), "");
		// String code2=StringUtil.safeToString(m2.get("dealercode"), "");
		// if(name1.equals(name2) && code1.equals(code2) && idc1.equals(idc2)){
		// empService.updateshemnew(id, dossid2);
		// }
		// }
		// }

		// 清空shsgm_new表中的dossid,在doss表中找记录,将dossid写入shsgm_new表中
		// for (Map m1 : list1) {
		// String dossid1 = StringUtil.safeToString(m1.get("dossId"),
		// "").toUpperCase();
		// String name1 = StringUtil.safeToString(m1.get("pName"), "");
		// String idc1 = StringUtil.safeToString(m1.get("idCard"),
		// "").toUpperCase();
		// String code1 = StringUtil.safeToString(m1.get("dealercode"),
		// "").toUpperCase();
		// String id = StringUtil.safeToString(m1.get("id"), "");
		// System.out.println("dossid---" + a);
		// for (Map m2 : list2) {
		// String dossid2 = StringUtil.safeToString(m2.get("dossId"),
		// "").toUpperCase();
		// String name2 = StringUtil.safeToString(m2.get("pName"), "");
		// String idc2 = StringUtil.safeToString(m2.get("idCard"),
		// "").toUpperCase();
		// String code2 = StringUtil.safeToString(m2.get("dealercode"),
		// "").toUpperCase();
		// if (name1.equals(name1) && idc1.equals(idc2)
		// && code1.equals(code2)) {
		// empService.updateshemnew(id, dossid2);
		// break;
		// }
		// }
		// a++;
		// }

		// //处理多条跨店的数据
		// for (Map m1 : list6) {
		// String dossid1 = StringUtil.safeToString(m1.get("dossId"),
		// "").toUpperCase();
		// String name1 = StringUtil.safeToString(m1.get("pName"), "");
		// String idc1 = StringUtil.safeToString(m1.get("idCard"),
		// "").toUpperCase();
		// String code1 = StringUtil.safeToString(m1.get("dealercode"),
		// "").toUpperCase();
		// String id = StringUtil.safeToString(m1.get("id"), "");
		// String state = StringUtil.safeToString(m1.get("state"), "");
		// String position=StringUtil.safeToString(m1.get("position"), "");
		// String phone=StringUtil.safeToString(m1.get("phone"), "");
		//
		// System.out.println("dossid---" +a);
		// for (Map m2 : list2) {
		// String dossid2 = StringUtil.safeToString(m2.get("dossId"),
		// "").toUpperCase();
		// String name2 = StringUtil.safeToString(m2.get("pName"), "");
		// String idc2 = StringUtil.safeToString(m2.get("idCard"),
		// "").toUpperCase();
		// String code2 = StringUtil.safeToString(m2.get("dealercode"),
		// "").toUpperCase();
		// String phone2=StringUtil.safeToString(m2.get("phone"), "");
		// String job=StringUtil.safeToString(m2.get("job"), "");
		// if (name1.equals(name2) && idc1.equals(idc2) && position.equals(job)
		// && position.equals("总经理")) {
		// empService.updateshemnew(id, dossid2);
		// if(!"1".equals(state) && !"".equals(idc2)){
		// empService.updateshemnew2(id, idc2);
		// }
		// break;
		// }else if(name1.equals(name2) && idc1.equals(idc2) &&
		// position.equals(job) && phone2.equals(phone)){
		// empService.updateshemnew(id, dossid2);
		// if(!"1".equals(state) && !"".equals(idc2)){
		// empService.updateshemnew2(id, idc2);
		// }
		// break;
		// }
		// }
		// a++;
		// }

		// for(Map m1:list1){
		// String name1=StringUtil.safeToString(m1.get("pName"), "");
		// String idc1=StringUtil.safeToString(m1.get("idCard"), "");
		// String code1=StringUtil.safeToString(m1.get("dealercode"), "");
		// String id=StringUtil.safeToString(m1.get("id"), "");
		// String position=StringUtil.safeToString(m1.get("position"), "");
		// String phoneNum=StringUtil.safeToString(m1.get("phoneNum"), "");
		// System.out.println("id--"+id);
		// for(Map m2:list2){
		// String dossid2=StringUtil.safeToString(m2.get("dossId"), "");
		// String name2=StringUtil.safeToString(m2.get("pName"), "");
		// String idc2=StringUtil.safeToString(m2.get("idCard"), "");
		// String code2=StringUtil.safeToString(m2.get("dealercode"), "");
		// String job=StringUtil.safeToString(m2.get("job"), "");
		// String phone=StringUtil.safeToString(m2.get("phone"), "");
		// if("总经理".equals(position)){
		// if(code1.equals(code2) && name1.equals(name2) &&
		// position.equals(job)){
		// empService.updateshemnew(id, dossid2);
		// break;
		// }
		// }else{
		// if(code1.equals(code2) && name1.equals(name2) &&
		// phoneNum.equals(phone) && position.equals(job)){
		// empService.updateshemnew(id, dossid2);
		// break;
		// }
		// }
		// }
		// }

		// for(Map m1:list1){
		// String dossid1=StringUtil.safeToString(m1.get("dossId"), "");
		// String name1=StringUtil.safeToString(m1.get("pName"), "");
		// String idc1=StringUtil.safeToString(m1.get("idCard"), "");
		// String code1=StringUtil.safeToString(m1.get("dealercode"), "");
		// String id=StringUtil.safeToString(m1.get("id"), "");
		// System.out.println("dossid---"+dossid1);
		// for(Map m2:list2){
		// String dossid2=StringUtil.safeToString(m2.get("dossId"), "");
		// String name2=StringUtil.safeToString(m2.get("pName"), "");
		// String idc2=StringUtil.safeToString(m2.get("idCard"), "");
		// String code2=StringUtil.safeToString(m2.get("dealercode"), "");
		// //先匹配dossid
		// if(name1.equals(name1) && idc1.equals(idc2) && code1.equals(code2)){
		// empService.updateshemnew(id, dossid2);
		// break;
		// }
		//
		// }
		//		
		// }

		// for(Map m1:list1){
		// String dossid1=StringUtil.safeToString(m1.get("dossId"), "");
		// String name1=StringUtil.safeToString(m1.get("pName"), "");
		// String idc1=StringUtil.safeToString(m1.get("idCard"), "");
		// String code1=StringUtil.safeToString(m1.get("dealercode"), "");
		// System.out.println("dossid---"+dossid1);
		// for(Map m2:list2){
		// String dossid2=StringUtil.safeToString(m2.get("dossId"), "");
		// String name2=StringUtil.safeToString(m2.get("pName"), "");
		// String idc2=StringUtil.safeToString(m2.get("idCard"), "");
		// String code2=StringUtil.safeToString(m2.get("dealercode"), "");
		//			
		// //先匹配dossid
		// if(dossid1.equals(dossid2)){
		// a++;
		// if(!name1.equals(name2)){
		// b++;
		// }
		// if(!idc1.equals(idc2)){
		// c++;
		// }
		// if(!idc1.equals(idc2)&& !name1.equals(name2) ){
		// d++;
		// }
		// break;
		// }
		// }
		//			
		// }

		// for(Map m3:list3){
		// String dossid1=StringUtil.safeToString(m3.get("dossId"), "");
		// String name1=StringUtil.safeToString(m3.get("pName"), "");
		// String idc1=StringUtil.safeToString(m3.get("idCard"), "");
		// String code1=StringUtil.safeToString(m3.get("dealercode"), "");
		// for(Map m2:list2){
		// String dossid2=StringUtil.safeToString(m2.get("dossId"), "");
		// String name2=StringUtil.safeToString(m2.get("pName"), "");
		// String idc2=StringUtil.safeToString(m2.get("idCard"), "");
		// String code2=StringUtil.safeToString(m2.get("dealercode"), "");
		//				
		// if(name1.equals(name2) && idc1.equals(idc2) && code1.equals(code2)){
		// e++;
		// break;
		// }
		//				
		//				
		// }
		// }

		System.out.println("匹配成功：" + a);
		System.out.println("姓名不匹配：" + b);
		System.out.println("身份证不匹配：" + c);
		System.out.println("姓名身份证不匹配：" + d);
		System.out.println("没写DOSSID的：" + e);
		return "/jsp/proprietor/listemployee";
	}
}
