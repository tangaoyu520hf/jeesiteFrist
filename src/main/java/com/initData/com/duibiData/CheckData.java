package com.initData.com.duibiData;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.initData.com.init.data.POIUtil;


public class CheckData {
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\Administrator\\Downloads\\分摊费用统计2016-02-26 (2).xlsx");
		File file2 = new File("D:\\work\\123.xlsx");
		XSSFWorkbook xssfWorkbook2 = null;
		xssfWorkbook2 = new XSSFWorkbook(new FileInputStream(file2));
		XSSFSheet sheetAt2 = xssfWorkbook2.getSheetAt(0);
		System.out.println(sheetAt2.getLastRowNum());
		XSSFWorkbook xssfWorkbook = null;
		xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));

		XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
		
		
		Set<String> set = new HashSet<String>();
		
		for(int rowNumber=0;rowNumber<=sheetAt.getLastRowNum();rowNumber++){
			XSSFRow row = sheetAt.getRow(rowNumber);
			String cellStringValue = POIUtil.getCellStringValue(row.getCell(1));
			boolean flag = false;
			for(int rowNumber2=0;rowNumber2<=sheetAt2.getLastRowNum();rowNumber2++){
				XSSFRow row2 = sheetAt2.getRow(rowNumber2);
				String cellStringValue2 = POIUtil.getCellStringValue(row2.getCell(0));
				if(cellStringValue.equals(cellStringValue2)){
					flag = true;
					break;
				}
			}
			if(!flag){
				set.add(cellStringValue);
			}
		}
		for(String str : set){
			System.out.println(str);
		}
	}

}
