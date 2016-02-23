package com.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.dao.DistributorDao;
import com.web.dao.EmpImportDao;
import com.web.dao.EmployeeDao;
import com.web.entity.Distributor;
import com.web.entity.Org_employee;


@Service("importService")
@Transactional
public class ImportService {
	@Autowired
	private EmployeeDao empDao;
	@Autowired
	private DistributorDao disDao;
	@Autowired
	private EmpImportDao empIMPDao;
	
	public void saveEMP(Org_employee emp){
		empDao.saveEmployee(emp);
	}
	
	public void saveDis(Distributor dis){
		disDao.saveDistributor(dis);
	}
	
	public void updateDis(Distributor dis){
		disDao.updateDistributor(dis);
	}
	
	public Distributor getDisByCode(String code){
		return disDao.getDistributorByCode(code);
	}
	
	public List<Org_employee> getEmpsByDealerCode(String dealercode){
		return disDao.getEmpsByDealerCode(dealercode);
	}
	
	public String importEMPS(List<Map> list){
		
		return empIMPDao.importEMPData(list);
	}
	
	public void setEWID(List<Org_employee> list){
		empIMPDao.setEWID(list);
	}
	
	public String importBonus(List<Map> list){
		
		return empIMPDao.importBonusData(list);
	}
	
	public String updateBonus(List<Map> list){
	    return empIMPDao.updateBonusData(list);
	}
}
