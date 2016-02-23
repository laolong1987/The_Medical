package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DataImport {
	/**
	 * Import data from excel file
	 * @param filePath excel file path
	 * @return
	 */
	public List<Map> getDataAuto(String filePath, int sheet_id){		
		ArrayList list = new ArrayList();
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
		
			Workbook book = Workbook.getWorkbook(fis);			
			Sheet sheet = book.getSheet(sheet_id);
			
			int columns = sheet.getColumns();
			int rows = sheet.getRows();
			int realrows = 0;
			for(int i = 1; i < rows; i++){
				Map<String,String> map = new HashMap<String,String>();
				int nullColumns = 0;
				for(int j = 0; j < columns; j++){
					String val = StringUtils.trimToEmpty(sheet.getCell(j,i).getContents());
					if(StringUtils.isBlank(val)){
						nullColumns++;
					}					
					map.put(String.valueOf(j), sheet.getCell(j,i).getContents());
				}
				if(nullColumns < columns){
					list.add(map);
					realrows++;
				}								
			}
			System.out.println("第" + realrows + "行");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
	public Date parseDate(String strdate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if("".equals(strdate) || null == strdate){
			return null;
		}else{
			try {
				date = sdf.parse(strdate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}
	
    public static void main(String[] args) {
		DataImport test = new DataImport();		
		List<Map> list = test.getDataAuto("d:\\aa.xls", 0);
		for(Map<String, String> data:list){
			for(String value:data.values()){
				System.out.println("value = " + value);
			}
		}
	}
}
