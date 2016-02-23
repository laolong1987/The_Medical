package com.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.StringUtil;
import com.web.dao.BatchDao;
import com.web.dao.BonusDao;
import com.web.entity.Bo_bonusdetail;


@Service("bonusService")
@Transactional
public class BonusService {
	@Autowired
	public BonusDao bonusDao;
	
	@Autowired
	public BatchDao batchDao;

	/**
	 * 查询奖金明细表
	 * @param map
	 * @return
	 */
	public List<Map> searchBonusdetail(Map map){
		return bonusDao.searchBonusdetail(map);
	}
	
	public List<String[]> exportBonusdetail(Map map){
		List<String[]> resultList = new ArrayList<String[]>();
		List<Map> list = bonusDao.searchBonusdetail(map);
		String ewid = "";
		String idCard = "";
		for (Map m : list) {
			String[] ss = new String[11];
			
			ss[0] = StringUtil.safeToString(m.get("bname"),"");
			String type=StringUtil.safeToString(m.get("type"),"");
			if("1".equals(type)){
				ss[1]="延保奖励";
			}else if("2".equals(type)){
				ss[1]="竞赛奖励";
			}else if("3".equals(type)){
				ss[1] = "单项奖励";
			}
			ss[2] = StringUtil.safeToString(m.get("dealer"),"");
			ss[3] = StringUtil.safeToString(m.get("dealercode"),"");			
			ss[4] = StringUtil.safeToString(m.get("name"),"");
			ss[5] = StringUtil.safeToString(m.get("position"),"");
			ss[6] = StringUtil.safeToString(m.get("price"),"");
			ss[7] = StringUtil.safeToString(m.get("taxprice"),"");
			ss[8] = StringUtil.safeToString(m.get("realizeprice"),"");
			String status=StringUtil.safeToString(m.get("status"),"");
			if("0".equals(status)){
				ss[9]="发放失败";
			}else if("1".equals(status)){
				ss[9]="已发放";
			}else if("2".equals(status)){
				ss[9]="发放中";
			}
			ss[10] = StringUtil.safeToString(m.get("reason"),"");
			resultList.add(ss);		
		}
		return resultList;
	}
	
	/**
	 * 查询奖金明细表记录数
	 * @param map
	 * @return
	 */
	public int countBonusdetail(Map map){
		return bonusDao.countBonusdetail(map);
	}
	public boolean delBonusById(String id){
		return bonusDao.delBonusById(id);
	}
	
	/**
	 * 根据id查询奖金
	 * @param id
	 * @return
	 */
	public Bo_bonusdetail getBonusdetailById(int id){
		return bonusDao.getBonusdetailById(id);
	}
	
	/**
	 * 保存奖金表数据
	 * @param bonus
	 */
    public void saveOrUpdateBonus(Bo_bonusdetail bonus){
    	bonusDao.saveOrUpdateBonus(bonus);
    }
    
    
	public List<Map>  searchBatchs(Map map){
		return  batchDao.searchBatchs(map);
	}
	
   public String getRegionnameByDealerCode(String dealercode){
	   String result="";
	   Map map=new HashMap();
	   map.put("dealercode", dealercode);
	   List<Map> list = bonusDao.searchDealer(map);
	   if(list.size()>0){
		   result=StringUtil.safeToString(list.get(0).get("regionname"), "");
	   }
	   return result;
	
	   
	   
	   
}
   
   
   public String getRegionnameByDealerCodeyb(String dealercode){
	   String result="";
	   Map map=new HashMap();
	   map.put("dealercode", dealercode);
	   List<Map> list = bonusDao.searchDealeryb(map);
	   if(list.size()>0){
		   result=StringUtil.safeToString(list.get(0).get("regionname"), "");
	   }
	   return result;
	
}
   
	public List<Bo_bonusdetail> getBonusbyEWID(String ewid){
		return bonusDao.getBonusbyEWID(ewid);
	}
	
	public String getdelearByDealerCode(String dealercode){
		   String result="";
		   Map map=new HashMap();
		   map.put("dealercode", dealercode);
		   List<Map> list = bonusDao.searchDealer(map);
		   if(list.size()>0){
			   result=StringUtil.safeToString(list.get(0).get("dealer"), "");
		   }
		   return result;
		
	}
	
	public Integer getMaxBatchCode(){
		return batchDao.getMaxBatchCode();
	}
	
	public String changeBonus(Map para){
		int result = bonusDao.adjustBonus(para);
		if(result != 0){
			return "奖金调整成功！";
		}else{
			return "奖金调整失败！";
		}
	}
	
	public String confirmBonus(Map map){
		int result = bonusDao.confirmBonus(map);
		if(result != 0){
			return "确认成功！";
		}else{
			return "确认失败！";
		}
	}
	
	public String cancelConfirmBonus(Map para){
		int result = bonusDao.cancelConfirm(para);
		if(result != 0){
			return "取消成功！";
		}else{
			return "取消失败！";
		}
	}
}
