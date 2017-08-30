/**
 * 
 */
package com.uralian.cgiats.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author skurapati
 *
 */
public class ExcelEx {
public static void main(String[] args) {
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet("Recruiters Day Wise Performace");

	
	// Setup some styles that we need for the Cells
	HSSFCellStyle BLUE_14 = wb.createCellStyle();
	HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette(); 
	palette.setColorAtIndex((short)40, (byte)47, (byte)117, (byte)181); 
	BLUE_14.setFillForegroundColor(palette.getColor(40).getIndex());
//	BLUE_14.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//	BLUE_14.setBottomBorderColor(HSSFColor.BLACK.index);
//	GREEN.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//	GREEN.setBorderRight(HSSFCellStyle.BORDER_THIN);
//	GREEN.setBottomBorderColor(HSSFColor.BLACK.index);
	BLUE_14.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	
	HSSFFont font1 = wb.createFont();
	font1.setColor(HSSFColor.WHITE.index);
	font1.setFontHeightInPoints((short)14);
	BLUE_14.setFont(font1);
	BLUE_14.setAlignment(CellStyle.ALIGN_CENTER);

	HSSFCellStyle YELLOW = wb.createCellStyle();
	HSSFPalette palette2 = ((HSSFWorkbook)wb).getCustomPalette(); 
	palette2.setColorAtIndex((short)41, (byte)255, (byte)255, (byte)0); 
	YELLOW.setFillForegroundColor(palette2.getColor(41).getIndex());
	YELLOW.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	
	HSSFFont font2 = wb.createFont();
	font2.setFontHeightInPoints((short)11);
	font2.setColor(HSSFColor.BLACK.index);
	font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
	YELLOW.setFont(font2);
	YELLOW.setAlignment(CellStyle.ALIGN_CENTER);
	
	
	HSSFCellStyle BLUE = wb.createCellStyle();
	HSSFPalette palette3 = ((HSSFWorkbook)wb).getCustomPalette(); 
	palette3.setColorAtIndex((short)42, (byte)47, (byte)117, (byte)181); 
	BLUE.setFillForegroundColor(palette3.getColor(42).getIndex());
	BLUE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	BLUE.setWrapText(true);
	
	HSSFFont font3 = wb.createFont();
	font3.setFontHeightInPoints((short)10);
	font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
	font3.setColor(HSSFColor.WHITE.index);
	BLUE.setFont(font3);
	
	
	HSSFCellStyle WHITE = wb.createCellStyle();
	WHITE.setWrapText(true);
	
	HSSFFont font5 = wb.createFont();
	font5.setFontHeightInPoints((short)10);
	font5.setColor(HSSFColor.BLUE.index);
	font5.setBoldweight(Font.BOLDWEIGHT_BOLD);
	WHITE.setFont(font5);
	
	
	HSSFCellStyle WHITE_BLACK_BOLD = wb.createCellStyle();
	WHITE_BLACK_BOLD.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	WHITE_BLACK_BOLD.setBorderRight(HSSFCellStyle.BORDER_THIN);
	WHITE_BLACK_BOLD.setBorderTop(HSSFCellStyle.BORDER_THIN);
	WHITE_BLACK_BOLD.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	WHITE_BLACK_BOLD.setBottomBorderColor(HSSFColor.BLACK.index);
	WHITE_BLACK_BOLD.setRightBorderColor(HSSFColor.BLACK.index);
	WHITE_BLACK_BOLD.setTopBorderColor(HSSFColor.BLACK.index);
	WHITE_BLACK_BOLD.setLeftBorderColor(HSSFColor.BLACK.index);
	WHITE_BLACK_BOLD.setWrapText(true);
	
	HSSFFont font6 = wb.createFont();
	font6.setFontHeightInPoints((short)10);
	font6.setColor(HSSFColor.BLACK.index);
	font6.setBoldweight(Font.BOLDWEIGHT_BOLD);
	WHITE_BLACK_BOLD.setFont(font6);
	

	HSSFCellStyle WHITE_GREEN_BOLD = wb.createCellStyle();
	
	HSSFFont font7 = wb.createFont();
	font7.setFontHeightInPoints((short)10);
	font7.setColor(HSSFColor.GREEN.index);
	font7.setBoldweight(Font.BOLDWEIGHT_BOLD);
	WHITE_GREEN_BOLD.setAlignment(CellStyle.ALIGN_RIGHT);
	WHITE_GREEN_BOLD.setFont(font7);
	
	
	HSSFCellStyle WHITE_BLUE_BOLD = wb.createCellStyle();
	
	HSSFFont font8 = wb.createFont();
	font8.setFontHeightInPoints((short)10);
	font8.setColor(HSSFColor.DARK_BLUE.index);
	font8.setBoldweight(Font.BOLDWEIGHT_BOLD);
	WHITE_BLUE_BOLD.setAlignment(CellStyle.ALIGN_RIGHT);
	WHITE_BLUE_BOLD.setFont(font8);
	
	HSSFRow row=sheet.createRow(0);
	HSSFCell cell= row.createCell(0);
	cell.setCellValue("CGI ATS : Category wise Report of 08-15-2017,Tuesday");
	cell.setCellStyle(BLUE_14);
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
	
	row=sheet.createRow(1);
	cell= row.createCell(0);
	cell.setCellValue("Total Job Orders : 12, Total Submissions : 23, Total BDM's : 6, Total Recruiters : 37, Avg Submissions Per Recruiter : 0.62");
	cell.setCellStyle(YELLOW);
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(1, 1, 0, 12), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(1, 1, 0, 12), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(1, 1, 0, 12), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(1, 1, 0, 12), sheet, wb);
	
	row=sheet.createRow(2);
	row.setHeight((short)800);
	cell= row.createCell(0);
	cell.setCellValue("S.No.");
	cell.setCellStyle(WHITE);
	cell= row.createCell(1);
	cell.setCellValue("Name");
	cell.setCellStyle(WHITE);
	cell= row.createCell(2);
	cell.setCellValue("Role");
	cell.setCellStyle(WHITE);
	cell= row.createCell(3);
	cell.setCellValue("Location");
	cell.setCellStyle(WHITE);
	cell= row.createCell(4);
	cell.setCellValue("Date of Joining");
	cell.setCellStyle(WHITE);
	cell= row.createCell(5);
	cell.setCellValue("2017 - YTD Starts");
	cell.setCellStyle(WHITE);
	cell= row.createCell(6);
	cell.setCellValue("Avg Hires Per Month");
	cell.setCellStyle(WHITE);
	
	cell= row.createCell(7);
	cell.setCellValue("Category A - Direct Customer / Relationship");
	cell.setCellStyle(BLUE);
	cell= row.createCell(9);
	cell.setCellValue("Category B - VMS Portal");
	cell.setCellStyle(BLUE);
	cell= row.createCell(11);
	cell.setCellValue("Category C - Third Party");
	cell.setCellStyle(BLUE);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 8));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 7, 8), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 7, 8), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 7, 8), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 7, 8), sheet, wb);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 2, 9, 10));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 9, 10), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 9, 10), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 9, 10), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 9, 10), sheet, wb);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 2, 11, 12));
	
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 11, 12), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 11, 12), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 11, 12), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 2, 11, 12), sheet, wb);
	
	
	row=sheet.createRow(3);
	row.setHeight((short)600);
	cell= row.createCell(7);
	cell.setCellValue("No. of Req's");
	cell.setCellStyle(WHITE_BLACK_BOLD);
	cell= row.createCell(8);
	cell.setCellValue("No. of Submittals");
	cell.setCellStyle(WHITE_BLACK_BOLD);
	cell= row.createCell(9);
	cell.setCellValue("No. of Req's");
	cell.setCellStyle(WHITE_BLACK_BOLD);
	cell= row.createCell(10);
	cell.setCellValue("No. of Submittals");
	cell.setCellStyle(WHITE_BLACK_BOLD);
	cell= row.createCell(11);
	cell.setCellValue("No. of Req's");
	cell.setCellStyle(WHITE_BLACK_BOLD);
	cell= row.createCell(12);
	cell.setCellValue("No. of Submittals");
	cell.setCellStyle(WHITE_BLACK_BOLD);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 0, 0), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 0, 0), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 0, 0), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 0, 0), sheet, wb);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 1, 1), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 1, 1), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 1, 1), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 1, 1), sheet, wb);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 2, 2), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 2, 2), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 2, 2), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 2, 2), sheet, wb);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 3, 3), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 3, 3), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 3, 3), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 3, 3), sheet, wb);
	
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 4, 4));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 4, 4), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 4, 4), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 4, 4), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 4, 4), sheet, wb);
	
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 5, 5), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 5, 5), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 5, 5), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 5, 5), sheet, wb);
	
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
	HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 6, 6), sheet, wb);
	HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 6, 6), sheet, wb);
	HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 6, 6), sheet, wb);
	HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(2, 3, 6, 6), sheet, wb);
	
	String value="3,2";
	row=sheet.createRow(4);
	cell= row.createCell(0);
	cell.setCellValue("12");
	cell= row.createCell(2);
	cell.setCellValue(value);

	
	for (int i=0; i<15; i++){
		sheet.autoSizeColumn(i);
		}
	try {
		OutputStream os=new FileOutputStream(new File("D:\\siva.xls"));
		wb.write(os);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
