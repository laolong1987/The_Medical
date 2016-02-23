package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class JxlUtil {

	public void writeExcel(OutputStream out, List list, String[] title) {
		if (list == null) {
			throw new IllegalArgumentException("要写入excel的数据不能为空！");
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet ws = workbook.createSheet("sheet 1", 0);// 创建sheet
			int rowNum = 0; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
			if (title != null) {
				putRow(ws, 0, title);// 压入标题
				rowNum = 1;
			}
			for (int i = 0; i < list.size(); i++, rowNum++) {// 写sheet
				Object[] cells = (Object[]) list.get(i);
				putRow(ws, rowNum, cells); // 压一行到sheet
			}
			workbook.write();
			workbook.close(); // 一定要关闭, 否则没有保存Excel
		} catch (RowsExceededException e) {
			System.out.println("jxl write RowsExceededException: "
					+ e.getMessage());
		} catch (WriteException e) {
			System.out.println("jxl write WriteException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("jxl write file i/o exception!, cause by: "
					+ e.getMessage());
		}
	}

	public static void writeExcel(String filepath, String filepath2,
			List list1, int sheet,int row) {

		File file = new File(filepath);
		File file2 = new File(filepath2);
		Workbook book = null;

		try {
			book = Workbook.getWorkbook(file);
			WritableWorkbook workbook = Workbook.createWorkbook(file2, book);
			WritableSheet ws = workbook.getSheet(sheet);// 创建sheet
			int rowNum =row; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
			for (int i = 0; i < list1.size(); i++, rowNum++) {// 写sheet
				Object[] cells = (Object[]) list1.get(i);
				putRow(ws, rowNum, cells); // 压一行到sheet
			}
			// workbook.write();
			// workbook.close();
			// ws = workbook.getSheet(1);// 创建sheet
			// rowNum = 3; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
			// for (int i = 0; i < list2.size(); i++, rowNum++) {// 写sheet
			// Object[] cells = (Object[]) list2.get(i);
			// putRow(ws, rowNum, cells); // 压一行到sheet
			// }
			// workbook.write();
			// workbook.close();
			// ws = workbook.getSheet(2);// 创建sheet
			// rowNum = 3; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
			// for (int i = 0; i < list3.size(); i++, rowNum++) {// 写sheet
			// Object[] cells = (Object[]) list3.get(i);
			// putRow(ws, rowNum, cells); // 压一行到sheet
			// }
			workbook.write();

			workbook.close(); // 一定要关闭, 否则没有保存Excel
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			System.out.println("jxl write RowsExceededException: "
					+ e.getMessage());
		} catch (WriteException e) {
			System.out.println("jxl write WriteException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("jxl write file i/o exception!, cause by: "
					+ e.getMessage());
		}
	}

	private static void putRow(WritableSheet ws, int rowNum, Object[] cells)
			throws RowsExceededException, WriteException {

		for (int j = 0; j < cells.length; j++) {// 写一行
//			if (isNumeric(cells[j].toString())) {
				if (false) {
				jxl.write.Number numbercell = new jxl.write.Number(j, rowNum,
						Integer.parseInt(cells[j].toString()));
				ws.addCell(numbercell);
			} else {
				Label cell = new Label(j, rowNum, "" + cells[j]);
				ws.addCell(cell);
			}
		}
	}

	public static void main(String[] args) {
//		JxlUtil importData = new JxlUtil();
//		List<String[]> list = new ArrayList<String[]>();
//		String[] a = { "1", "2", "3" };
//		list.add(a);

		// importData.writeExcel("/path_config.properties", list, 3, 0);
String aaString="413023198011234516";
		System.out.println(isNumeric(aaString));
	}

	public static boolean isNumeric(String str) {
		if (str == null || str.equals("") || str.length()>10) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
