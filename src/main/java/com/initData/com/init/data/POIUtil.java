package com.initData.com.init.data;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIUtil {
	/** excel表头行数 */
	public static final int XLS_HEADER_ROW = 1;
	public static XSSFSheet sheetAt = null;
	public static void initExcel(File excelFile){
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(new FileInputStream(excelFile));
			sheetAt = xssfWorkbook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 整数
	public static DecimalFormat df = new DecimalFormat("0");
	// 格式化日期字符串
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 格式化数字
	public static DecimalFormat nf = new DecimalFormat("0.00");

	/**
	 * <p>
	 * 获取单元格值浮点值
	 * </p>
	 * 
	 * @param xssfCell
	 *            单元格对象
	 * @return String 转换的内容值
	 */
	public static Double getCellDoubleValue(XSSFCell xssfCell) {
		if (null != xssfCell && HSSFCell.CELL_TYPE_NUMERIC == xssfCell.getCellType()) {
			return xssfCell.getNumericCellValue();
		}
		return 0.0;
	}

	/**
	 * <p>
	 * 获取单元格值整形值
	 * </p>
	 * 
	 * @param cell
	 *            单元格对象
	 * @return String 转换的内容值
	 */
	public static Integer getCellIntegerValue(HSSFCell cell) {
		if (null != cell && HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			Double v = cell.getNumericCellValue();
			return v.intValue();
		}
		return 0;
	}

	/**
	 * <p>
	 * 获取单元格值字符串值
	 * </p>
	 * 
	 * @param xssfCell
	 *            单元格对象
	 * @return String 转换的内容值
	 */
	public static String getCellStringValue(XSSFCell xssfCell) {
		if (xssfCell == null) {
			return "";
		}
		String cellValue = "";
		switch (xssfCell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串类型
			cellValue = xssfCell.toString();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0) {
				cellValue = "";
			}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值类型
			// 添加时间类型
			if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = sdf.format(
						HSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue()))
						.toString();
			} else {
				DecimalFormat df = new DecimalFormat("#");//使用DecimalFormat类对科学计数法格式的数字进行格式化
				Double v = xssfCell.getNumericCellValue();
				cellValue = df.format(v);
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			xssfCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(xssfCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
}
