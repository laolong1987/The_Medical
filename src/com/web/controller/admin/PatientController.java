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
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.utils.DataImport;
import com.utils.DateUtil;
import com.utils.JxlUtil;
import com.utils.LigerUtils;
import com.utils.StringUtil;
import com.web.service.BonusService;
import com.web.service.PatientService;

@Controller
@RequestMapping("/admin/patient")
public class PatientController {
    private static final Log log = LogFactory.getLog(PatientController.class);

    @Autowired
    public PatientService patientService;

    @RequestMapping(value = "/SearchDataBase", method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody Map<String, String> param) {

//	return patientService.search(param).getResult();
	return null;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveDatabase(@RequestBody Map<String, String> param) {
//	patientService.save(param);
	return "success";
    }

    @RequestMapping(value = "/removeDatabase", method = RequestMethod.POST)
    @ResponseBody
    public Object removeEnum(@RequestBody List<Map> params) {
	List<Integer> ids = new ArrayList<Integer>();
	for (Map map : params) {
	    ids.add(Integer.parseInt(map.get("id").toString()));
	}
//	patientService.removeDatabase(ids);
	return "success";
    }
}
