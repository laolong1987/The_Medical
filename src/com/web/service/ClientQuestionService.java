package com.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.StringUtil;
import com.web.dao.ClientQuestionDao;
import com.web.entity.ClientQuestion;
import com.web.entity.Replyclient;





//查询用户提出的所有问题和保存用户的提问信息
@Service("ClientQuestionService")
@Transactional
public class ClientQuestionService {

	@Autowired
	public ClientQuestionDao clientquestiondao;
	//查询用户提问的问题
	@SuppressWarnings("unchecked")
//	public List<Map> listAllQuestionsByEmpid(int id) {
//		return clientquestiondao.listAllQuestionsByEmpid(id);
//	}
	public List<Map> listAllQuestions(Map p){
		return clientquestiondao.listAllQuestions(p);
	}
	public Map getTotalRows(Map p){
		return clientquestiondao.getTotalCount(p);
	}
	//保存用户提问的信息
	public void saveQuetions(ClientQuestion clientquestion){
		
		clientquestiondao.saveQuetions(clientquestion);
		
	}
	/**
	 * 查询表
	 * @param map
	 * @return
	 */
	public List<Map> searchclient(Map map){
		return clientquestiondao.searchclient(map);
	}
	/**
	 * 查询表记录数
	 * @param map
	 * @return
	 */
	public int countclient(Map map){
		return clientquestiondao.countclient(map);
	}
	/**
	 * 根据id查询文档实体
	 * 
	 * @param id
	 * @return
	 */
	public ClientQuestion getclientInfoById(int id) {
		return clientquestiondao.getclientInfoById(id);
	}
	public Replyclient getclientInfoByhfId(int id) {
		return clientquestiondao.getclientInfoByhfId(id);
	}
	//保存的信息回答的信息
	public void saveHclient(Replyclient replyclient){
		
		clientquestiondao.saveHclient(replyclient);
		
	}
//	public List<Map> getDetailCQ(int id){
//		return clientquestiondao.getDetailCQ(id);
//	}
//	public String searchEMPName(String id){
//		return clientquestiondao.searchename(id);
//	}
	public void removeQCByID(String id){
		clientquestiondao.removeQCByID(id);
	}
	
	public List<String[]> exportQuestions(Map map){
		List<String[]> resultList = new ArrayList<String[]>();
		List<Map> list = clientquestiondao.searchclient(map);
		String ewid = "";
		String idCard = "";
		for (Map m : list) {
			String[] ss = new String[7];
			ss[0] = StringUtil.safeToString(m.get("empname"), "");
			ss[1] = StringUtil.safeToString(m.get("title"), "");
			
			if(Integer.valueOf(0).equals(m.get("type"))){
				ss[2] = "奖励";
			}
			if(Integer.valueOf(3).equals(m.get("type"))){
				ss[2] = "投诉";
			}
			
			if(Integer.valueOf(2).equals(m.get("type"))){
				ss[2] = "其他";
			}
			
			if(Integer.valueOf(0).equals(m.get("status"))){
				ss[3] = "待回复";
			}
			if(Integer.valueOf(1).equals(m.get("status"))){
				ss[3] = "已回复";
			}
			ss[4] = StringUtil.safeToString(m.get("countent"), "");
			ss[5] = StringUtil.safeToString(m.get("repcountent"), "");
			ss[6] = StringUtil.safeToString(m.get("ctime"), "");
			resultList.add(ss);
		}
		return resultList;
	}
}
