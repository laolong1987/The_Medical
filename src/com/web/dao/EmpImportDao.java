package com.web.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.utils.DataImport;
import com.utils.StringUtil;
import com.web.entity.Batch;
import com.web.entity.Bo_Bonusdetail_Failer;
import com.web.entity.Bo_Bonusdetail_Org;
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Distributor;
import com.web.entity.DistributorBonus;
import com.web.entity.Modify_name;
import com.web.entity.Org_employee;
import com.web.service.EmployeeService;
import com.web.service.ImportService;
@Repository
public class EmpImportDao {	
	private static final Log log = 	LogFactory.getLog(EmpImportDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
    @Autowired     
	private EmployeeDao empDao;
    
	@Autowired
	private DistributorDao disDao;
	
	@Autowired 
	private EmployeeService empService;
	
	@Autowired
	private BonusDao bonusDao;
	
	@Autowired 
	private BatchDao batchDao;
	
	@Autowired
	private DistributorBonusDao  disBonusDao;
	
	public String importEMPData(List<Map> list){
		Session session = sessionFactory.openSession(); 
		DataImport di = new DataImport();
		String msg = "";
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Transaction tr = null;
		int totalCount = list.size();
		int successCount = 0;
		int rownum = 0;
		try {			
			tr = session.beginTransaction();			
			for (Map map : list) {
			    rownum = list.indexOf(map);
				String brand = StringUtil.safeToString(map.get("0"), "").trim();
				if(!"".equals(brand)){
					if(!StringUtil.validateBrand(brand)){
						return msg = "第" +(rownum + 2)+ "行数据有误:品牌：" + brand + "格式不正确";
					}
				}
				String region = StringUtil.safeToString(map.get("1"), "").trim();
				String dealercode = StringUtil.safeToString(map.get("2"), "")
						.trim();
				if(!"".equals(dealercode)){
					if(!StringUtil.validateDealerCode(dealercode)){
						return msg ="第" +(rownum + 2)+ "行数据有误:经销商代码：" + dealercode + "格式不正确";
					}
				}
				String dealername = StringUtil.safeToString(map.get("3"), "").trim();
				if(!"".equals(dealername)){
					if(!StringUtil.validateCheseString(dealername)){
						return msg ="第" +(rownum + 2)+ "行数据有误:经销商名称：" + dealername + "格式不正确";
					}
				}
				String dis_status = StringUtil.safeToString(map.get("4"), "")
						.trim();
				if(!"".equals(dis_status)){
					if(!StringUtil.validateCheseString(dis_status)){
						return msg ="第" +(rownum + 2)+ "行数据有误:经销商状态：" +  dis_status + "格式不正确";
					}
				}
				String dossid = StringUtil.safeToString(map.get("5"), "").trim();
				//加dossid的验证
				if(!"".equals(dossid)){
					if(!StringUtil.validateDossID(dossid)){
						return msg ="第" +(rownum + 2)+ "行数据有误:DOSS ID：" +  dossid + "格式不正确";
					}
				}
							
				String empname = StringUtil.safeToString(map.get("6"), "")
				.trim();
				if(!"".equals(empname)){
					if(!StringUtil.validateEmpName(empname)){
						return msg ="姓名：" + empname + "格式不正确";
					}
				}
				String gender = StringUtil.safeToString(map.get("7"), "")
				.trim();
				String degree = StringUtil.safeToString(map.get("8"), "")
				.trim();
				String position = StringUtil.safeToString(map.get("9"), "")
				.trim();
				if(!"".equals(position)){
					if(!StringUtil.validatePosition(position)){
						return msg ="第" +(rownum + 2)+ "行数据有误:岗位：" + position  + "格式不正确";
					}
				}
				String service_status = StringUtil.safeToString(map.get("10"), "").trim();
				String idCard = StringUtil.safeToString(map.get("11"), "").trim();
				if(!"".equals(idCard)){
					if(!StringUtil.validateIdCard(idCard)){
						return msg ="身份证：" + idCard + "格式不正确";
					}
				}
				String phone = StringUtil.safeToString(map.get("12"), "").trim();
				if(!"".equals(phone)){
					if(!StringUtil.validatePhone(phone)){
						return msg ="第" +(rownum + 2)+ "行数据有误:手机号码：" + phone + "格式不正确";
					}
				}
				String email = StringUtil.safeToString(map.get("13"), "").trim();
				if(!"".equals(email)){
					if(!StringUtil.validateEmail(email)){
						return msg ="第" +(rownum + 2)+ "行数据有误:邮件地址：" + email + "格式不正确";
					}
				}
				String bank = StringUtil.safeToString(map.get("14"), "").trim();	
				String openbank = StringUtil.safeToString(map.get("15"), "").trim();
				String account = StringUtil.safeToString(map.get("16"), "").trim();
				if(!"".equals(account)){
					if(!StringUtil.validateAccount(account)){
						return msg ="第" +(rownum + 2)+ "行数据有误:银行账号："+ account + "格式不正确";
					}
				}
				//String bonus = StringUtil.safeToString(map.get("17"), "").trim();
				String driverL = StringUtil.safeToString(map.get("17"), "").trim();
				String licenceDate = StringUtil.safeToString(map.get("18"), "").trim();
				if(!"".equals(licenceDate)){
					if(!StringUtil.validateDate(licenceDate)){
						return msg ="第" +(rownum + 2)+ "行数据有误:驾照领取日期："+ licenceDate + "格式不正确";
					}
				}
				String fserive_brand = StringUtil.safeToString(map.get("19"), "").trim();
				if(!"".equals(fserive_brand)){
					if(!StringUtil.validateBrand(fserive_brand)){
						return msg ="第" +(rownum + 2)+ "行数据有误:第一次服务品牌：" + fserive_brand + "格式不正确";
					}
				}
				String is_trained = StringUtil.safeToString(map.get("20"), "").trim();
				String applyPositionDate = StringUtil.safeToString(map.get("21"), "").trim();
				if(!"".equals(applyPositionDate)){
					if(!StringUtil.validateDate(applyPositionDate)){
						return msg ="第" +(rownum + 2)+ "行数据有误:岗位申请日期：" + applyPositionDate + "格式不正确";
					}
				}
				String joinDate = StringUtil.safeToString(map.get("22"), "").trim();
				if(!"".equals(joinDate)){
					if(!StringUtil.validateDate(joinDate)){
						return msg ="第" +(rownum + 2)+ "行数据有误:入职日期：" + joinDate + "格式不正确" ;
					}
				}
				String passTrainDate = StringUtil.safeToString(map.get("23"), "").trim();
				if(!"".equals(passTrainDate)){
					if(!StringUtil.validateDate(passTrainDate)){
						return msg ="第" +(rownum + 2)+ "行数据有误:通过培训日期：" + passTrainDate + "格式不正确" ;
					}
				}
				String resignationDate = StringUtil.safeToString(map.get("24"), "").trim();
				if(!"".equals(resignationDate)){
					if(!StringUtil.validateDate(resignationDate)){
						return msg ="第" +(rownum + 2)+ "行数据有误:离职日期：" + resignationDate + "格式不正确" ;
					}
				}
				String LMSDate = StringUtil.safeToString(map.get("25"), "").trim();
				if(!"".equals(LMSDate)){
					if(!StringUtil.validateDate(LMSDate)){
						return msg ="第" +(rownum + 2)+ "行数据有误:LMSDate：" + LMSDate + "格式不正确" ;
					}
				}
				//导入经销商或更新经销商			
				if("总经理".equals(position)){
					Distributor dis = disDao.getDistributorByCode(dealercode);
					if(dis == null){
						Distributor newdis = new Distributor();						
						newdis.setCode(dealercode);
						newdis.setRegion(region);
						if("待上线".equals(dis_status)){
							newdis.setStatus(0);
							newdis.setUnavailable_date(sdf.format(date));
							newdis.setUpdateDate(sdf.format(date));
						}
						if("上线".equals(dis_status)){
							newdis.setStatus(1);
							newdis.setAvailableDate(sdf.format(date));
							newdis.setUpdateDate(sdf.format(date));
						}
						if("停业".equals(dis_status)){
							newdis.setStatus(2);
							newdis.setUnavailable_date(sdf.format(date));
							newdis.setUpdateDate(sdf.format(date));
						}
						if("下线".equals(dis_status)){
							newdis.setStatus(3);
							newdis.setUnavailable_date(sdf.format(date));
							newdis.setUpdateDate(sdf.format(date));
						}
						newdis.setName(dealername);
						newdis.setBrand(brand);	
						newdis.setCreateDate(sdf.format(date));
						disDao.saveDistributor(newdis);
					}else{
						int d_status = 0;
						if("待上线".equals(dis_status)){
							d_status = 0;
							dis.setUnavailable_date(sdf.format(date));
						}
						if("上线".equals(dis_status)){
							d_status = 1;
							dis.setAvailableDate(sdf.format(date));
						}
						if("停业".equals(dis_status)){
							d_status = 2;
							dis.setUnavailable_date(sdf.format(date));
						}	
						if("下线".equals(dis_status)){
							d_status = 3;
							dis.setUnavailable_date(sdf.format(date));
						}
						if(dis.getStatus() != d_status){
							dis.setStatus(d_status);
							session.saveOrUpdate(dis);
							//disDao.updateDistributor(dis);
						}
					}
				}
				
				//导入人员信息
				if(!"".equals(dossid)){
					Org_employee emp = empDao.getEmployeeByDossid(dossid);
					if(emp != null){						
					    emp.setBrand(brand);
						emp.setRegion(region);
						emp.setDealer(dealername);
						emp.setDealercode(dealercode);
						emp.setDoss_id(dossid);
						emp.setName(empname);
						//Set sex
			            emp.setSex(gender);
						//Set dgree
						emp.setDegree(degree);
						emp.setPosition(position);
						emp.setInservice(service_status);
						
						if("注销申请中".equals(service_status) || "冻结".equals(service_status)
								|| "离职".equals(service_status) || "草稿".equals(service_status)){
							
							//判断离职日期是否已经过了30天，如果已经超过30天将其账号锁定
							if(!"".equals(resignationDate)){
								String now = sdf.format(date);
								Date today = di.parseDate(now);
								Date resDate = di.parseDate(resignationDate);
								//设置离职时间						
								emp.setDimission_time(di.parseDate(resignationDate));
								int diffDays = (int) ((today.getTime() - resDate.getTime())/(86400000));
								if(diffDays > 30){
								    emp.setIsactived(2);
								}
							}else{
								emp.setDimission_time(date);
							}
							
						}
						emp.setIdcard(idCard);
						emp.setPhone(phone);
						emp.setMail(email);
						emp.setBankname(bank);
						emp.setOpenbank(openbank);
						emp.setAccountbank(account);
						//设置是否有驾照
						emp.setLicense(driverL);
						//设置领照时间
						//System.out.println("date == " + sdf.parse(licenceDate));
						if("".equals(licenceDate)){
							emp.setGet_license_time(null);
						}else{
						    emp.setGet_license_time(di.parseDate(licenceDate));
						}
						//设置首次服务品牌
						emp.setFirst_service_brand(fserive_brand);
						//设置是否受过品牌培训
						emp.setIstrained(is_trained);
						//设置岗位申请时间
						emp.setCpat(di.parseDate(applyPositionDate));
						//设置更新时间
						emp.setUpdatetime(date);
						//设置入职时间
						emp.setCaet(di.parseDate(joinDate));
						//设置通过岗位培训时间
						emp.setPass_train_time(di.parseDate(passTrainDate));
						
						//设置LMS导入时间
						emp.setLms_date(LMSDate);
						emp.setUpdatetime(date);
						empDao.saveEmployee(emp);
						successCount =successCount + 1;

					}else{
						Org_employee newemp = new Org_employee();
						String ewid = empService.getEWID(dealercode);
						newemp.setEwid(ewid);
						newemp.setBrand(brand);
						newemp.setRegion(region);
						newemp.setDealer(dealername);
						newemp.setDealercode(dealercode);
						newemp.setDoss_id(dossid);
						newemp.setName(empname);
						newemp.setName(empname);
						//Set sex
						newemp.setSex(gender);
						//Set dgree
						newemp.setDegree(degree);
						newemp.setPosition(position);
						newemp.setInservice(service_status);
						
						if("注销申请中".equals(service_status) || "冻结".equals(service_status)
								|| "离职".equals(service_status) || "草稿".equals(service_status)){
							//newemp.setIsactived(2);
							//判断离职日期是否已经过了30天，如果已经超过30天将其账号锁定
							if(!"".equals(resignationDate)){
								String now = sdf.format(date);
								Date today = di.parseDate(now);
								Date resDate = di.parseDate(resignationDate);								
								//设置离职时间
								newemp.setDimission_time(di.parseDate(resignationDate));
								int diffDays = (int) ((today.getTime() - resDate.getTime())/(86400000));
								//int differdays = today.getDate() - resDate
								if(diffDays > 30){
									newemp.setIsactived(2);
								}else{
									newemp.setIsactived(0);
								}
							}else{
								newemp.setDimission_time(date);
							}
						}else{
							newemp.setIsactived(0);
						}
						
						
						newemp.setIdcard(idCard);
						newemp.setPhone(phone);
						newemp.setMail(email);
						newemp.setBankname(bank);
						newemp.setOpenbank(openbank);
						newemp.setAccountbank(account);
						
						//设置是否有驾照
						newemp.setLicense(driverL);
						//设置领照时间					
						newemp.setGet_license_time(di.parseDate(licenceDate));
						//设置首次服务品牌
						newemp.setFirst_service_brand(fserive_brand);
						//设置是否受过品牌培训
						newemp.setIstrained(is_trained);
						//设置岗位申请时间
						newemp.setCpat(di.parseDate(applyPositionDate));
						//设置创建时间
						newemp.setCreatetime(date);
						//设置入职时间
						newemp.setCaet(di.parseDate(joinDate));
						//设置通过岗位培训时间
						newemp.setPass_train_time(di.parseDate(passTrainDate));
						
						//设置LMS导入时间
						newemp.setLms_date(LMSDate);
						
						newemp.setIsregist(2);
						if("总经理".equals(position)){
							newemp.setRole("1");
						}else{
							newemp.setRole("0");
						}
						
						newemp.setCreatetime(date);
						newemp.setUpdatetime(date);
						session.save(newemp);
						successCount = successCount + 1;
						//empService.saveEmployee(newemp);
					}				
				}							
			}
//System.out.println("=======" + rownum);
			tr.commit();

            if(successCount == list.size()){
			    msg ="数据全部上传成功！";
            }
		} catch (HibernateException e) {
			try {
				if(tr != null){
					tr.rollback();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				log.error("无法撤销事务",e1);
				
			}
			msg="数据上传失败： " + successCount + "条数据上传成功," + (totalCount -  successCount) + "条数据上传失败,第" +(rownum + 2)
			     +"行数据有误，请查看";
			e.printStackTrace();
		}catch(NumberFormatException e){
			msg = "数据格式不正确";
		}finally{
			try {
				session.close();
			} catch (HibernateException e) {
				log.error("无法关闭session",e);
			}
			return msg;
		}
		 
	}
	
	public void setEWID(List<Org_employee> list){
		Session session = sessionFactory.openSession();
		for(Org_employee emp: list){
			String ewid = empService.getEWID(emp.getDealercode());
			emp.setEwid(ewid);
			session.update(emp);
		}
		if(session != null){
			session.close();
		}
	}
	
	public String importBonusData(List<Map> list){
		Session session = sessionFactory.openSession(); 
		Transaction trb = null;
		DataImport di = new DataImport();
		String msg = "";
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		int btype = 0;
		int bstatus = 3;
		int columns = 0;
		int totalCount = 0;
		int successCount = 0;
		int rownum = 0;
		//Transaction tr = session.beginTransaction();		
			try {
				trb = session.beginTransaction();
				//int i = 0;
				DistributorBonus disBonus = null;
				for(Map map:list){
				    rownum = list.indexOf(map);
					totalCount = list.size();
					//i =+ 1;
					//columns += 1;
					String ewid = StringUtil.safeToString(map.get("0"), "");
					if(!"".equals(ewid)){
						if(!StringUtil.validateEWID(ewid)){
							return msg ="第" +(rownum + 2)+ "行数据有误:EWID: " + ewid + "格式不正确";
						}
					}
					String brand = StringUtil.safeToString(map.get("1"), "");
					if(!"".equals(brand)){
						if(!StringUtil.validateBrand(brand)){
							return msg ="第" +(rownum + 2)+ "行数据有误:品牌：" + brand + "格式不正确";
						}
					}
					String dis_code = StringUtil.safeToString(map.get("2"), "");
					if(!"".equals(dis_code)){
						if(!StringUtil.validateDossID(dis_code)){
							return msg ="第" +(rownum + 2)+ "行数据有误:经销商代码：" + dis_code + "格式不正确";
						}
					}
					String dis_name = StringUtil.safeToString(map.get("3"), "");
					if(!"".equals(dis_name)){
						if(!StringUtil.validateCheseString(dis_name)){
							return msg ="第" +(rownum + 2)+ "行数据有误:经销商名称：" + dis_name + "格式不正确";
						}
					}
					String emp_name = StringUtil.safeToString(map.get("4"), "");
					if(!"".equals(emp_name)){
						if(!StringUtil.validateEmpName(emp_name )){
							return msg ="第" +(rownum + 2)+ "行数据有误:姓名：" + emp_name + "格式不正确";
						}
					}
					String bonus = StringUtil.safeToString(map.get("6"), "0").replaceAll(",", "");
					if(!"".equals(bonus)){
						//验证奖金
						if(!StringUtil.validateBonus(bonus)){
							return msg ="第" +(rownum + 2)+ "行数据有误:奖金：" + bonus + "格式不正确";
						}
					}				
					String tax = StringUtil.safeToString(map.get("7"), "0").replaceAll(",", "");
					if(!"".equals(tax)){
						if(!StringUtil.validateBonus(tax)){
							return msg ="第" +(rownum + 2)+ "行数据有误:税额：" + tax + "格式不正确";
						}
					}
					
                   String realBonus = StringUtil.safeToString(map.get("8"), "0").replaceAll(",", "");
                    if(!"".equals(realBonus)){
                        if(!StringUtil.validateBonus(realBonus)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:个人实得奖金：" + realBonus + "格式不正确";
                        }
                    }
					
					String batch = StringUtil.safeToString(map.get("9"), "");
					if(!"".equals(batch)){
						if(!StringUtil.validateBatch(batch)){
							return msg ="第" +(rownum + 2)+ "行数据有误:批次：" + batch + "格式不正确";
						}
					}
					String bonus_type = StringUtil.safeToString(map.get("10"), "");
					
					if(!"".equals(bonus_type)){
						if(!StringUtil.validateCheseString(bonus_type)){
							return msg ="第" +(rownum + 2)+ "行数据有误:奖金类型：" + bonus_type + "格式不正确";
						}
					}					
					//System.out.println(ewid);
					
					if("延保奖励".equals(bonus_type)){
						btype = 1;
					}else if("竞赛奖励".equals(bonus_type)){
						btype = 2;
					}else if("单项奖励".equals(bonus_type)){
						btype = 3;
					}
					String bonus_status = StringUtil.safeToString(map.get("11"), "");
					if("发放失败".equals(bonus_status)){
						bstatus = 0;
					}else if("已发放".equals(bonus_status)){
						bstatus = 1;
					}else if("待发放".equals(bonus_status)){
						bstatus = 2;
					}
					String reason = StringUtil.safeToString(map.get("12"), "");
					String position = StringUtil.safeToString(map.get("13"), "");
					if(!"".equals(position)){
                        if(!StringUtil.validatePosition(position)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:岗位：" + position + "格式不正确";
                        }
                    }
					String remark = StringUtil.safeToString(map.get("19"), "");
                    
					String service_fee = StringUtil.safeToString(map.get("20"), "0");
					
					int currentBatchCode = batchDao.getCodeByName(batch);
					int newBatchCode = batchDao.getMaxCode() + 1;
					//添加批次
					//Batch isExistbatch = batchDao.getBatchByName(batch);
					Batch newBatch = null;
					if(currentBatchCode == 0){
						//if(null == isExistbatch){
						newBatch = new Batch();
						newBatch.setCode(newBatchCode);
						newBatch.setName(batch);
						session.save(newBatch);
						//}
					}
//					//导入经销商奖金清单
//					if(dis_bonus == "" || dis_bonus == null){
//						dis_bonus = "0";
//					}
//					disBonus = disBonusDao.getDisBonusByBatch(dis_code, batch);
// 				    Map<String,DistributorBonus> dismap = new HashMap<String,DistributorBonus>();
// 				    if(!dismap.containsValue(disBonus)){
// 				    	dismap.put("dis"+i, disBonus);
// 				    }
// 				    
//					if(null == dismap.get("dis"+i)){
//						DistributorBonus newDisBonus = new DistributorBonus();
//						newDisBonus.setBrand(brand);
//						newDisBonus.setDis_code(dis_code);
//						newDisBonus.setBonus_batch(batch);
//						newDisBonus.setBonus(Double.valueOf(dis_bonus));
//						newDisBonus.setBonus_type(btype);
//						//sessionFactory.getCurrentSession().save(newDisBonus);
//						disBonusDao.saveDisBonus(newDisBonus);
//					}else{
//						DistributorBonus updatedbonus = dismap.get("dis"+i);
//						updatedbonus.setBrand(brand);
//						updatedbonus.setDis_code(dis_code);
//						updatedbonus.setBonus_batch(batch);
//						updatedbonus.setBonus(Double.valueOf(dis_bonus));
//						updatedbonus.setBonus_type(btype);
//						//sessionFactory.getCurrentSession().save(disBonus);
//						disBonusDao.saveDisBonus(disBonus);
//					}
					if(bonus == "" || bonus == null || "null".equals(bonus)){
                        bonus = "0";
                    }
					
					if(tax == "" || tax == null || "null".equals(tax)){
					    tax = "0";
					}
					
					if(realBonus == "" || realBonus == null || "null".equals(realBonus)){
					    realBonus = "0";
                    }
					//获取清单中批次代码
					int boBatchCode = batchDao.getCodeByName(batch);
//					//根据批次查找奖金列表
//					List existBolist = bonusDao.getBonusByBatch(boBatchCode);
//					//如果该批次奖金不存在，则导入奖金系统中
//					if(existBolist.size() <= 0){
					//根据ewid获取人员信息， 将已经锁定的人员账号激活
					Org_employee emp = empDao.getEMPByEWID(ewid);
					if(emp.getIsactived() == 0){
						emp.setIsactived(1);
						if("1".equals(emp.getRole())){
					        emp.setRole("0");
						}
					}
					empDao.saveEmployee(emp);
					 //导入个人奖金清单					    
                    Bo_bonusdetail newbo = new Bo_bonusdetail();
                    Bo_Bonusdetail_Org orgbo = new Bo_Bonusdetail_Org();
                    newbo.setEwid(ewid); 
                    newbo.setCreatetime(date);
                    newbo.setUpdatetime(date);
                    newbo.setPosition(position);
                    newbo.setPrice(Double.valueOf(bonus));
                    newbo.setRealizeprice(Double.valueOf(realBonus));
                    newbo.setTaxprice(Double.valueOf(tax));
                    newbo.setDealer(dis_name);
                    newbo.setType(btype);
//	                    if(currentBatchCode != 0){
//	                        newbo.setBatch(currentBatchCode);
//	                    }else{
                        newbo.setBatch(boBatchCode);
//	                    }
                    newbo.setDealercode(dis_code);
                    newbo.setStatus(bstatus);
                    newbo.setReason(reason);
                    newbo.setRemark(remark);
                    newbo.setIs_visible(0);
                    newbo.setService_fee(Integer.parseInt(service_fee));
                    //bonusDao.saveOrUpdateBonus(newbo);
                    session.save(newbo);
                    
                    orgbo.setEwid(ewid); 
                    orgbo.setCreatetime(date);
                    orgbo.setUpdatetime(date);
                    orgbo.setPosition(position);
                    orgbo.setPrice(Double.valueOf(bonus));
                    orgbo.setRealizeprice(Double.valueOf(realBonus));
                    orgbo.setTaxprice(Double.valueOf(tax));
                    orgbo.setDealer(dis_name);
                    orgbo.setType(btype);
//	                    if(currentBatchCode != 0){
//	                        orgbo.setBatch(currentBatchCode);
//	                    }else{
                        orgbo.setBatch(boBatchCode);
//	                    }
                    orgbo.setDealercode(dis_code);
                    orgbo.setStatus(bstatus);
                    orgbo.setReason(reason);
                    orgbo.setRemark(remark);
                    
                    successCount = successCount+ 1;
                    
                    session.save(orgbo);
                    
//					}else{//如果该批次奖金已存在，那么更新奖金的发放状态
//					     List<Bo_bonusdetail>  bolist = bonusDao.getBo_BonusByEwidBatch(ewid, boBatchCode, btype, position, dis_code);
//					     if(bolist.size() > 0){
//					         for(Bo_bonusdetail bo:bolist){
//					             bo.setStatus(bstatus);
//					             bo.setReason(reason);
//					             session.update(bo);
//					         }
//					     }
//					     
//					}
				}
				//session.flush();
				trb.commit();				
				msg = "数据全部上传成功！";
			//System.out.println("====" + columns);	
			} catch (HibernateException e) {
				e.printStackTrace();
				try {
					if(trb != null){
						trb.rollback();
						msg = "数据成功上传"+ successCount + "条，失败" + (totalCount - successCount)+"条；第" + (rownum + 2) +"行数据有误请检查";
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					log.error("无法撤销事务");
				}
			}catch(NumberFormatException e){
				e.printStackTrace();
				msg = "数据格式不正确";
			} finally{
				try {
					if(session != null){
						session.close();
					}
				} catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("无法关闭事务");
				}
			}
		
		return msg;
	}
	
	public String updateBonusData(List<Map> list){
        Session session = sessionFactory.openSession(); 
        Transaction trb = null;
        DataImport di = new DataImport();
        String msg = "";
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        int btype = 0;
        int bstatus = 3;
        int columns = 0;
        int totalCount = 0;
        int successCount = 0;
        int rownum = 0;
        Bo_Bonusdetail_Failer bof = null;
        //Transaction tr = session.beginTransaction();      
            try {
                trb = session.beginTransaction();
                //int i = 0;
                DistributorBonus disBonus = null;
                for(Map map:list){
                    rownum = list.indexOf(map);
                    totalCount = list.size();
                    //i =+ 1;
                    //columns += 1;
                    String ewid = StringUtil.safeToString(map.get("0"), "");
                    if(!"".equals(ewid)){
                        if(!StringUtil.validateEWID(ewid)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:EWID: " + ewid + "格式不正确";
                        }
                    }
                    String brand = StringUtil.safeToString(map.get("1"), "");
                    if(!"".equals(brand)){
                        if(!StringUtil.validateBrand(brand)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:品牌：" + brand + "格式不正确";
                        }
                    }
                    String dis_code = StringUtil.safeToString(map.get("2"), "");
                    if(!"".equals(dis_code)){
                        if(!StringUtil.validateDossID(dis_code)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:经销商代码：" + dis_code + "格式不正确";
                        }
                    }
                    String dis_name = StringUtil.safeToString(map.get("3"), "");
                    if(!"".equals(dis_name)){
                        if(!StringUtil.validateCheseString(dis_name)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:经销商名称：" + dis_name + "格式不正确";
                        }
                    }
                    String emp_name = StringUtil.safeToString(map.get("4"), "");
                    if(!"".equals(emp_name)){
                        if(!StringUtil.validateEmpName(emp_name )){
                            return msg ="第" +(rownum + 2)+ "行数据有误:姓名：" + emp_name + "格式不正确";
                        }
                    }
                    String bonus = StringUtil.safeToString(map.get("6"), "0").replaceAll(",", "");
                    if(!"".equals(bonus)){
                        //验证奖金
                        if(!StringUtil.validateBonus(bonus)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:奖金：" + bonus + "格式不正确";
                        }
                    }               
                    String tax = StringUtil.safeToString(map.get("7"), "0").replaceAll(",", "");
                    if(!"".equals(tax)){
                        if(!StringUtil.validateBonus(tax)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:税额：" + tax + "格式不正确";
                        }
                    }
                    
                   String realBonus = StringUtil.safeToString(map.get("8"), "0").replaceAll(",", "");
                    if(!"".equals(realBonus)){
                        if(!StringUtil.validateBonus(realBonus)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:个人实得奖金：" + realBonus + "格式不正确";
                        }
                    }
                    
                    String batch = StringUtil.safeToString(map.get("9"), "");
                    if(!"".equals(batch)){
                        if(!StringUtil.validateBatch(batch)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:批次：" + batch + "格式不正确";
                        }
                    }
                    String bonus_type = StringUtil.safeToString(map.get("10"), "");
                    
                    if(!"".equals(bonus_type)){
                        if(!StringUtil.validateCheseString(bonus_type)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:奖金类型：" + bonus_type + "格式不正确";
                        }
                    }                   
                    //System.out.println(ewid);
                    
                    if("延保奖励".equals(bonus_type)){
                        btype = 1;
                    }else if("竞赛奖励".equals(bonus_type)){
                        btype = 2;
                    }else if("单项奖励".equals(bonus_type)){
                        btype = 3;
                    }
                    String bonus_status = StringUtil.safeToString(map.get("11"), "");
                    if("发放失败".equals(bonus_status)){
                        bstatus = 0;                       
                    }else if("已发放".equals(bonus_status)){
                        bstatus = 1;
                    }else if("待发放".equals(bonus_status)){
                        bstatus = 2;
                    }
                    String reason = StringUtil.safeToString(map.get("12"), "");
                    String position = StringUtil.safeToString(map.get("13"), "");
                    if(!"".equals(position)){
                        if(!StringUtil.validatePosition(position)){
                            return msg ="第" +(rownum + 2)+ "行数据有误:岗位：" + position + "格式不正确";
                        }
                    }
                    String remark = StringUtil.safeToString(map.get("19"), ""); 
                    
                    String service_fee = StringUtil.safeToString(map.get("20"), "0");
                    int currentBatchCode = batchDao.getCodeByName(batch);
                    //int newBatchCode = batchDao.getMaxCode() + 1;

                    if(bonus == "" || bonus == null || "null".equals(bonus)){
                        bonus = "0";
                    }
                    
                    if(tax == "" || tax == null || "null".equals(tax)){
                        tax = "0";
                    }
                    
                    if(realBonus == "" || realBonus == null || "null".equals(realBonus)){
                        realBonus = "0";
                    }
                    //获取清单中批次代码
                   int boBatchCode = batchDao.getCodeByName(batch);                       
                   List<Bo_bonusdetail>  bolist = bonusDao.getBo_BonusByEwidBatch(ewid, boBatchCode, btype, position, dis_code);
                   if(bolist.size() > 0){
                       for(Bo_bonusdetail bo:bolist){
                           bo.setStatus(bstatus);
                           bo.setReason(reason);
                           bo.setUpdatetime(date);
                           if(bstatus == 0){
                        	   bof = new Bo_Bonusdetail_Failer();
                               bof.setBo_id(bo.getId());
                               bof.setService_fee(Integer.parseInt(service_fee));
                               bof.setEwid(bo.getEwid());
                               bof.setCreate_time(bo.getCreatetime());
                               bof.setFailed_time(date);
                               bof.setBatch(bo.getBatch());
                               session.save(bof);
                               bo.setService_fee(0);
                           }else{
                        	   bo.setService_fee(Integer.parseInt(service_fee));
                           }
                           session.update(bo);
                       }
                   }
                   successCount = successCount+ 1;      

                }
                //session.flush();
                trb.commit();               
                msg = "数据全部更新成功！";
            //System.out.println("====" + columns); 
            } catch (HibernateException e) {
                e.printStackTrace();
                try {
                    if(trb != null){
                        trb.rollback();
                        msg = "数据成功更新"+ successCount + "条，失败" + (totalCount - successCount)+"条；第" + (rownum + 2) +"行数据有误请检查";
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    log.error("无法撤销事务");
                }
            }catch(NumberFormatException e){
                e.printStackTrace();
                msg = "数据格式不正确";
            } finally{
                try {
                    if(session != null){
                        session.close();
                    }
                } catch (HibernateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    log.error("无法关闭事务");
                }
            }
        
        return msg;
    }

}
