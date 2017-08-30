package com.uralian.cgiats.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uralian.cgiats.dto.SubmittalDto;
import com.uralian.cgiats.model.JobOrder;
import com.uralian.cgiats.model.JobOrderCategory;
import com.uralian.cgiats.model.UserRole;
import com.uralian.cgiats.web.SchedulerEmailBean;

public class ExcelGeneratorJobOrderScheduler {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private HSSFCellStyle cs = null;
	private HSSFCellStyle csBold = null;
	
	HSSFCellStyle WHITE_BLACK_BOLD =null;
	HSSFCellStyle WHITE_BLUE_BOLD =null;
	HSSFCellStyle WHITE_GREEN_BOLD =null;
	HSSFCellStyle EVEN_COLOR =null;
	HSSFCellStyle ODD_COLOR =null;

	public byte[] createExcel(List<JobOrder> jobOrderList, String email) {

		byte[] output = null;
		try {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Job Orders List");

			// Setup some styles that we need for the Cells
			setCellStyles(wb);

			setColWidthAndMargin(sheet);

			int rowIndex = 0;
			rowIndex = insertHeaderInfo(sheet, rowIndex, false);
			rowIndex = insertJODetailInfo(sheet, rowIndex, jobOrderList, null);
			String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
			if (rowIndex >= 1) {

				String fileName = "JobOrder_" + timeStamp + ".xls";
				output = wb.getBytes();
				SchedulerEmailBean seb = new SchedulerEmailBean();
				if (email != null) {
					seb.sendEmailWithAttachment(email, "CGIATS: Report of JobOrders without Submittals",
							"Hi all, <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;Please find the attachment containing list of joborders without submittals from more than 48 hours.<br/><br/><b>*** This is an automatically generated email, please do not reply ***</b>",
							fileName, wb, rowIndex);
				} else {
					log.info("To Address is Empty ::");
				}

				try {
					FileOutputStream fileOut = new FileOutputStream("./Mail/JobOrder/" + fileName);
					wb.write(fileOut);
					fileOut.close();
					System.out.println("file was copied " + fileName);

				} catch (FileNotFoundException fnfe) {

				}
			}
		} catch (Exception e) {
			log.info("Exception in exporting all records to excel :: " + e);
			e.printStackTrace();
		}
		return output;
	}

	public byte[] createExcelForOneDayWithoutSubmittals(Map<Integer, Integer> jobOrderWithCountMap, List<JobOrder> jobOrderList, String email) {

		byte[] output = null;
		try {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Job Orders List");

			// Setup some styles that we need for the Cells
			setCellStyles(wb);

			setColWidthAndMargin(sheet);

			int rowIndex = 0;
			rowIndex = insertHeaderInfo(sheet, rowIndex, true);
			rowIndex = insertJODetailInfo(sheet, rowIndex, jobOrderList, jobOrderWithCountMap);
			String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
			if (rowIndex >= 1) {

				String fileName = "JobOrder_" + timeStamp + ".xls";
				output = wb.getBytes();
				SchedulerEmailBean seb = new SchedulerEmailBean();
				if (email != null) {
					seb.sendEmailWithAttachment(email, "CGIATS: Report of JobOrders without required Submittals in more than 24 hours",
							"Hi all, <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;Please find the attachment containing list of joborders without required submittals from more than 24 hours.<br/><br/><b>*** This is an automatically generated email, please do not reply ***</b>",
							fileName, wb, rowIndex);
				} else {
					log.info("To Address is Empty ::");
				}

				try {
					FileOutputStream fileOut = new FileOutputStream("./Mail/JobOrder/" + fileName);
					wb.write(fileOut);
					fileOut.close();
					System.out.println("file was copied " + fileName);

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
					log.error(fnfe.getMessage());
				}
			}
		} catch (Exception e) {
			log.info("Exception in exporting all records to excel :: " + e);
			e.printStackTrace();
		}
		return output;
	}
private Map<String,Integer> categoryWiseCellIndex(){
	Map<String,Integer> categoryWiseCellIndexMap = new HashMap<String, Integer>();
	categoryWiseCellIndexMap.put(JobOrderCategory.DirectCustomerRelationship.toString(), 8);
	categoryWiseCellIndexMap.put(JobOrderCategory.VMSPortal.toString(), 10);
	categoryWiseCellIndexMap.put(JobOrderCategory.ThirdParty.toString(), 12);
	return categoryWiseCellIndexMap;
}
	public byte[] createExcelFor_RecDayWisePerformaceWithJobOrders(Map<String,SubmittalDto> recWithPerformanceMap,List<Map<String,Object>> jobOrdersList,Map<String, List<SubmittalDto>> dmWithRecMap,Date fromDate,Date toDate,String emailIds) {

		byte[] output = null;
		try {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Recruiters Day Wise Performace");
			Map<String,Integer> categoryWiseCellIndexMap = categoryWiseCellIndex();
			// Setup some styles that we need for the Cells
			setCellStyles(wb);

//			setColWidthAndMargin(sheet);

			
			int jobOrderCount=0;
			Set<String> recruiters=new HashSet<String>();
			Integer totalSubmittals = 0;
			
			if(jobOrdersList!=null && jobOrdersList.size()>0){
				for(Map<String,Object> map:jobOrdersList){
						jobOrderCount+=(Integer)map.get(Constants.NOJOB_ORDERS);
					}
				}
			if(dmWithRecMap!=null && dmWithRecMap.size()>0){
				for(String dm:dmWithRecMap.keySet()){
					for(SubmittalDto submittalDto:dmWithRecMap.get(dm)){
						if(recWithPerformanceMap.get(submittalDto.getUserId())!=null && recWithPerformanceMap.get(submittalDto.getUserId()).getLevel().contains(UserRole.Recruiter.name()) && !recWithPerformanceMap.get(submittalDto.getUserId()).getLevel().equalsIgnoreCase(Constants.JR_RECRUITER)){
							recruiters.add(submittalDto.getUserId());
						}
					totalSubmittals+=Integer.parseInt(submittalDto.getSubmittedCount());
					}
				}
			}
			String excelTitle="Total Job Orders : "+jobOrderCount+", Total Submissions : "+totalSubmittals+", Total BDM's : "+dmWithRecMap.size()+", Total Recruiters : "+recruiters.size()+", Avg Submissions Per Recruiter "+((fromDate)!=null?("In This Month : "):": ")+Utils.getTwoDecimalDoubleFromObj((Double.valueOf(totalSubmittals)/Double.valueOf(recruiters.size()==0?1:recruiters.size())));
			int rowIndex = 0;
			Map<String,Integer> sumOfSubByCategories = new HashMap<String,Integer>();
			Map<String,Integer> sumOfJobByCategories = new HashMap<String,Integer>();
			rowIndex = insertHeaderInfoForDayWisePeformance(sheet, rowIndex,wb,excelTitle,fromDate,toDate);
			rowIndex = insertDetailInfoForDayWisePeformance(sheet, rowIndex,recWithPerformanceMap, dmWithRecMap,categoryWiseCellIndexMap,jobOrdersList,sumOfSubByCategories,sumOfJobByCategories);
			rowIndex = insertDataForFooter(sheet, rowIndex, categoryWiseCellIndexMap, sumOfSubByCategories, sumOfJobByCategories);
			
//			String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
			if (rowIndex >= 1) {
				String fileName = "USStaffingReport_" +((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+"_To_":"")+ Utils.convertDateToStringWithMonth(toDate) + ".xls";
				output = wb.getBytes();
				SchedulerEmailBean seb = new SchedulerEmailBean();
//				if (email != null) {US Staffing Report-15 Aug 2017
					seb.sendEmailWithAttachment(emailIds, "CGI ATS : US Staffing Report for "+((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+" To ":"")+Utils.convertDateToStringWithMonth(toDate),
							"Hi,<br> Please find the attached Category wise Job orders report of "+((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+" To "+Utils.convertDateToStringWithMonth(toDate):Utils.convertDateToStringWithMonth(toDate)+","+ new SimpleDateFormat("EEEE").format(toDate))+".<br><br><br/><br/><b>*** This is an automatically generated email, please do not reply ***</b>",
							fileName, wb, rowIndex);
//				} else {
//					log.info("To Address is Empty ::");
//				}

				try {
					FileOutputStream fileOut = new FileOutputStream("./Mail/JobOrder/" + fileName);
					wb.write(fileOut);
					fileOut.close();
					System.out.println("file was copied " + fileName);

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
					log.error(fnfe.getMessage());
				}
			}
		} catch (Exception e) {
			log.info("Exception in exporting all records to excel :: " + e);
			e.printStackTrace();
		}
		return output;
	}
	
	
	public byte[] createExcelFor_ConsultantInfoReport(List<SubmittalDto> list,Date fromDate,Date toDate,String emailIds) {

		byte[] output = null;
		try {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Consultant Info Report");
			// Setup some styles that we need for the Cells
			setCellStyles(wb);

//			setColWidthAndMargin(sheet);

			
			
			
			int rowIndex = 0;
			rowIndex = insertHeaderInfoForConsultantInfoReport(sheet, rowIndex,wb);
			rowIndex = insertDetailInfoForConsultantInfoReport(sheet, list, rowIndex);
			
//			String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
			if (rowIndex >= 1) {
				String fileName = "ConsultantInfoReport_" +((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+"_To_":"")+ Utils.convertDateToStringWithMonth(toDate) + ".xls";
				output = wb.getBytes();
				SchedulerEmailBean seb = new SchedulerEmailBean();
//				if (email != null) {US Staffing Report-15 Aug 2017
					seb.sendEmailWithAttachment(emailIds, "CGI ATS : Consultant Info Report for "+((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+" To ":"")+Utils.convertDateToStringWithMonth(toDate),
							"Hi,<br> Please find the attached Consultant Info Report of "+((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+" To "+Utils.convertDateToStringWithMonth(toDate):Utils.convertDateToStringWithMonth(toDate))+".<br><br><br/><br/><b>*** This is an automatically generated email, please do not reply ***</b>",
							fileName, wb, rowIndex);
//				} else {
//					log.info("To Address is Empty ::");
//				}

				try {
					FileOutputStream fileOut = new FileOutputStream("./Mail/JobOrder/" + fileName);
					wb.write(fileOut);
					fileOut.close();
					System.out.println("file was copied " + fileName);

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
					log.error(fnfe.getMessage());
				}
			}
		} catch (Exception e) {
			log.info("Exception in exporting all records to excel :: " + e);
			e.printStackTrace();
		}
		return output;
	}
	
	private int insertHeaderInfoForConsultantInfoReport(HSSFSheet sheet, int index,HSSFWorkbook wb) {
		String[] headerNames = {"Consultant Name","Account Name","Job Category","Start Date","End Date","Status","Recruiter Name","DM Name"};
		if(WHITE_BLUE_BOLD == null){
			WHITE_BLUE_BOLD = wb.createCellStyle();
			
			HSSFFont font8 = wb.createFont();
			font8.setFontHeightInPoints((short)10);
			font8.setColor(HSSFColor.DARK_BLUE.index);
			font8.setBoldweight(Font.BOLDWEIGHT_BOLD);
			WHITE_BLUE_BOLD.setAlignment(CellStyle.ALIGN_CENTER);
			WHITE_BLUE_BOLD.setFont(font8);
		}
		
		HSSFRow row=sheet.createRow(0);
		row.setHeight((short)300);
		HSSFCell cell = null;
		
		for (int i = 0; i < headerNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headerNames[i]);
			cell.setCellStyle(WHITE_BLUE_BOLD);
			sheet.setColumnWidth(i, 9500);
		}
		return 1;
	}
	
	private int insertDetailInfoForConsultantInfoReport(HSSFSheet sheet,List<SubmittalDto> list,int index) {
		try {
			String[] headerValues = {"candidateName","clientName","jobCategory","startDate","endDate","status","recName","dmName"};
			HSSFRow row=null;
			HSSFCell cell = null;
			if (list != null && list.size() > 0) {
				for (SubmittalDto dto : list) {
					row = sheet.createRow(index++);
					row.setHeight((short)300);
					for (int i = 0; i < headerValues.length; i++) {
						cell = row.createCell(i);
						cell.setCellValue(String.valueOf(PropertyUtils.getProperty(dto, headerValues[i])));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return index;
	}
	
	@SuppressWarnings("deprecation")
	private int insertHeaderInfoForDayWisePeformance(HSSFSheet sheet, int index,HSSFWorkbook wb,String excelTitle,Date fromDate,Date toDate) {
		
		HSSFCellStyle BLUE_14 = wb.createCellStyle();
		HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette(); 
		palette.setColorAtIndex((short)40, (byte)47, (byte)117, (byte)181); 
		BLUE_14.setFillForegroundColor(palette.getColor(40).getIndex());
//		BLUE_14.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//		BLUE_14.setBottomBorderColor(HSSFColor.BLACK.index);
//		GREEN.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		GREEN.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		GREEN.setBottomBorderColor(HSSFColor.BLACK.index);
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
		
		EVEN_COLOR = wb.createCellStyle();
		HSSFPalette palette4 = ((HSSFWorkbook)wb).getCustomPalette(); 
		palette4.setColorAtIndex((short)45, (byte)221, (byte)235, (byte)247); 
		EVEN_COLOR.setFillForegroundColor(palette4.getColor(45).getIndex());
		setBordersToStyle(EVEN_COLOR);
		EVEN_COLOR.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFFont font4 = wb.createFont();
		font4.setFontHeightInPoints((short)10);
		font4.setColor(HSSFColor.BLACK.index);
		EVEN_COLOR.setFont(font4);
		EVEN_COLOR.setAlignment(CellStyle.ALIGN_CENTER);
		
		ODD_COLOR = wb.createCellStyle();
		HSSFPalette palette5 = ((HSSFWorkbook)wb).getCustomPalette(); 
		palette5.setColorAtIndex((short)46, (byte)252, (byte)228, (byte)214); 
		ODD_COLOR.setFillForegroundColor(palette5.getColor(46).getIndex());
		setBordersToStyle(ODD_COLOR);
		ODD_COLOR.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFFont font9 = wb.createFont();
		font9.setFontHeightInPoints((short)10);
		font9.setColor(HSSFColor.BLACK.index);
		ODD_COLOR.setFont(font9);
		ODD_COLOR.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		WHITE_BLACK_BOLD = wb.createCellStyle();
//		WHITE_BLACK_BOLD.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		WHITE_BLACK_BOLD.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		WHITE_BLACK_BOLD.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		WHITE_BLACK_BOLD.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		WHITE_BLACK_BOLD.setBottomBorderColor(HSSFColor.BLACK.index);
//		WHITE_BLACK_BOLD.setRightBorderColor(HSSFColor.BLACK.index);
//		WHITE_BLACK_BOLD.setTopBorderColor(HSSFColor.BLACK.index);
//		WHITE_BLACK_BOLD.setLeftBorderColor(HSSFColor.BLACK.index);
		setBordersToStyle(WHITE_BLACK_BOLD);
		WHITE_BLACK_BOLD.setAlignment(CellStyle.ALIGN_CENTER);
		WHITE_BLACK_BOLD.setWrapText(true);
		
		HSSFFont font6 = wb.createFont();
		font6.setFontHeightInPoints((short)10);
		font6.setColor(HSSFColor.BLACK.index);
		font6.setBoldweight(Font.BOLDWEIGHT_BOLD);
		WHITE_BLACK_BOLD.setFont(font6);
		

		WHITE_GREEN_BOLD = wb.createCellStyle();
		
		HSSFFont font7 = wb.createFont();
		font7.setFontHeightInPoints((short)10);
		font7.setColor(HSSFColor.GREEN.index);
		font7.setBoldweight(Font.BOLDWEIGHT_BOLD);
		WHITE_GREEN_BOLD.setAlignment(CellStyle.ALIGN_RIGHT);
		WHITE_GREEN_BOLD.setFont(font7);
		
		
		WHITE_BLUE_BOLD = wb.createCellStyle();
		
		HSSFFont font8 = wb.createFont();
		font8.setFontHeightInPoints((short)10);
		font8.setColor(HSSFColor.DARK_BLUE.index);
		font8.setBoldweight(Font.BOLDWEIGHT_BOLD);
		WHITE_BLUE_BOLD.setAlignment(CellStyle.ALIGN_CENTER);
		WHITE_BLUE_BOLD.setFont(font8);
		
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell= row.createCell(0);
		cell.setCellValue("CGI ATS : Category wise Report of "+((fromDate)!=null?Utils.convertDateToStringWithMonth(fromDate)+" To "+Utils.convertDateToStringWithMonth(toDate):Utils.convertDateToStringWithMonth(toDate)+","+ new SimpleDateFormat("EEEE").format(toDate)));
		cell.setCellStyle(BLUE_14);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
		HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
		HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
		HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, new CellRangeAddress(0, 0, 0, 12), sheet, wb);
		
		row=sheet.createRow(1);
		cell= row.createCell(0);
		cell.setCellValue(excelTitle);
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
		//To Get Year
		Calendar d=Calendar.getInstance();
		d.setTime(toDate);
		cell.setCellValue(d.get(Calendar.YEAR)+" - YTD Starts");
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
		
		for (int i=0; i<15; i++){
			//This is hard coded for giving more width to the columns
			if(i==1){
				sheet.setColumnWidth(i, 9500);
			}else if(i==7 || i==9 || i==11){
				sheet.setColumnWidth(i, 7500);
			}else if(i==4){
				sheet.setColumnWidth(i, 5500);
			}else{
			sheet.autoSizeColumn(i);
			}
			}
	

		return 4;
	}
	
	private void setBordersToStyle(HSSFCellStyle style){
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
	}
	private int insertDetailInfoForDayWisePeformance(HSSFSheet sheet, int index,Map<String,SubmittalDto> recWithPerformanceMap,
			Map<String, List<SubmittalDto>> dmWithRecMap, Map<String, Integer> categoryWiseCellIndexMap,
			List<Map<String, Object>> jobOrdersList,Map<String,Integer> sumOfSubByCategories,Map<String,Integer> sumOfJobByCategories) {
		try {
			//This is to store the each recruiter's row
			Map<String,Integer> recWithRow=new HashMap<String,Integer>();
			int dmCount=1;
			for(String dmName:dmWithRecMap.keySet()){
				int sno=1;
				dmCount++;
				//Inserting DM's info into a new Row
				HSSFRow row=sheet.createRow(index++);
				HSSFCell cell= row.createCell(1);
				cell.setCellValue(dmWithRecMap.get(dmName).get(0).getDmName());
				cell.setCellStyle(WHITE_BLACK_BOLD);
				cell= row.createCell(2);
				cell.setCellValue(Constants.DM);
				cell.setCellStyle(WHITE_BLACK_BOLD);
				
				cell= row.createCell(3);
				cell.setCellValue(dmWithRecMap.get(dmName).get(0).getDmOfficeLocation());
				cell.setCellStyle(WHITE_BLACK_BOLD);
				int noOfJobOrders = 0;
				if (jobOrdersList != null && jobOrdersList.size() > 0) {
					for (Map<String, Object> map : jobOrdersList) {
						if (map.get(Constants.NOJOB_ORDERS) != null) {
							// Check if the DM did any job orders
							if (map.get(Constants.USERID).equals(dmName)) {
								cell = row.createCell(categoryWiseCellIndexMap.get(map.get(Constants.JOB_CATEGORY)) - 1);
								cell.setCellValue((Integer) map.get(Constants.NOJOB_ORDERS));
								cell.setCellStyle(cs);
								cell.setCellStyle(WHITE_BLACK_BOLD);
								noOfJobOrders += (Integer) map.get(Constants.NOJOB_ORDERS);
								if(sumOfJobByCategories.get(map.get(Constants.JOB_CATEGORY))!=null){
									sumOfJobByCategories.put((String)map.get(Constants.JOB_CATEGORY), sumOfJobByCategories.get(map.get(Constants.JOB_CATEGORY))+(Integer)map.get(Constants.NOJOB_ORDERS));
								}else{
									sumOfJobByCategories.put((String)map.get(Constants.JOB_CATEGORY), (Integer)map.get(Constants.NOJOB_ORDERS));
								}
							}
						}
					}
				}
				cell = row.getCell(1);
				cell.setCellValue(dmWithRecMap.get(dmName).get(0).getDmName() + " - (Req's " + noOfJobOrders + " )");
//				setRowColor(dmCount, row);
				for(SubmittalDto dto:dmWithRecMap.get(dmName)){
					index = insertData(sheet, index,dmCount,recWithPerformanceMap, dto,categoryWiseCellIndexMap,recWithRow.get(dto.getUserId()) != null?sno:(sno++),recWithRow,sumOfSubByCategories);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return index;
		

	}
	private int insertData(HSSFSheet sheet, int index,int dmCount,Map<String,SubmittalDto> recWithPerformanceMap,SubmittalDto dto,Map<String,Integer> categoryWiseCellIndexMap,int sno,
			Map<String,Integer> recWithRow,Map<String,Integer> sumOfSubByCategories){
		HSSFRow row = null;
		HSSFCell c  = null;
		if (!dto.getUserRole().equalsIgnoreCase(Constants.DM)) {
			if(dto.getJobCategory()!=null && dto.getJobCategory().trim().length()>0){
				if(dto.getJobCategory().equalsIgnoreCase(Constants.NOT_SPECIFIED)){
					dto.setJobCategory(JobOrderCategory.VMSPortal.toString());
				}
			if(sumOfSubByCategories.get(dto.getJobCategory())!=null){
				sumOfSubByCategories.put(dto.getJobCategory(), sumOfSubByCategories.get(dto.getJobCategory())+Integer.parseInt(dto.getSubmittedCount()));
			}else{
				sumOfSubByCategories.put(dto.getJobCategory(), Integer.parseInt(dto.getSubmittedCount()));
			}
			}
			
			if (recWithRow.get(dto.getUserId()) != null) {
				row = sheet.getRow(recWithRow.get(dto.getUserId()));
			} else {
				//Storing each recruiter index
				recWithRow.put(dto.getUserId(), index);
				
				row = sheet.createRow(index);
				c = row.createCell(0);

				c.setCellValue(sno);
				
				c = row.createCell(1);
				c.setCellValue(dto.getRecName());
				
				c = row.createCell(2);
				c.setCellValue(recWithPerformanceMap.get(dto.getUserId()).getLevel());
				c = row.createCell(3);
				c.setCellValue(dto.getRecOfficeLocation());
				c = row.createCell(4);
				c.setCellValue(recWithPerformanceMap.get(dto.getUserId()).getStrRecJoinDate());
				c = row.createCell(5);
				c.setCellValue(recWithPerformanceMap.get(dto.getUserId()).getStartedCount()!=null?Integer.parseInt(recWithPerformanceMap.get(dto.getUserId()).getStartedCount()):0);
				c.setCellType(Cell.CELL_TYPE_NUMERIC);
				c = row.createCell(6);
				c.setCellType(Cell.CELL_TYPE_NUMERIC);
				c.setCellValue(recWithPerformanceMap.get(dto.getUserId()).getAvgHires()!=null?Double.parseDouble(recWithPerformanceMap.get(dto.getUserId()).getAvgHires()):0.0);
				index++;
			}
		}
		if (dto.getJobCategory() != null && dto.getJobCategory().trim().length() > 0) {
			if(dto.getJobCategory().equalsIgnoreCase(Constants.NOT_SPECIFIED)){
				dto.setJobCategory(JobOrderCategory.VMSPortal.toString());
			}
			if (row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory()) - 1) != null && row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory()) - 1).getRichStringCellValue().getString().trim().length()>0) {
				c = row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory()) - 1);
				c.setCellValue(c.getStringCellValue()+","+ dto.getJobPosted());
			} else {
				c = row.createCell(categoryWiseCellIndexMap.get(dto.getJobCategory()) - 1);
				c.setCellValue(dto.getJobPosted());
			}

			if (row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory())) != null && (row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory())).getCellType()==Cell.CELL_TYPE_NUMERIC) && row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory())).getNumericCellValue()>0) {
				c = row.getCell(categoryWiseCellIndexMap.get(dto.getJobCategory()));
//				c.setCellValue(c.getStringCellValue()+","+ dto.getSubmittedCount());
				setCellType(((Double)c.getNumericCellValue()).intValue()+","+ dto.getSubmittedCount(), c);
			} else {
				c = row.createCell(categoryWiseCellIndexMap.get(dto.getJobCategory()));
//				c.setCellValue(dto.getSubmittedCount());
				setCellType(dto.getSubmittedCount(), c);
			}
		}
		setRowColor(dmCount, row);
		return index;
	}
	
	private void setCellType(String value,HSSFCell cell){
		if(value.contains(",")){
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(value);
		}else{
			cell.setCellValue(Integer.parseInt(value));
		}
		
		
	}
	private void setRowColor(int dmCount,HSSFRow row){
		Iterator<Cell> cellIterator = row.cellIterator();
		row.setHeight((short)300);
        while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if(dmCount%2==0){
             	cell.setCellStyle(EVEN_COLOR);
     		}else{
     			cell.setCellStyle(ODD_COLOR);
     		}
        }
        addColumnsAndSetColor(dmCount, row);
	}
	private void addColumnsAndSetColor(int dmCount,HSSFRow row)
	{
		for(int i=0;i<13;i++){
			if(row.getCell(i)==null){
				HSSFCell cell = row.createCell(i);
				 if(dmCount%2==0){
			         	cell.setCellStyle(EVEN_COLOR);
			 		}else{
			 			cell.setCellStyle(ODD_COLOR);
			 		}
			}
		}
	}
	private int insertDataForFooter(HSSFSheet sheet, int index,Map<String,Integer> categoryWiseCellIndexMap,Map<String,Integer> sumOfSubByCategories,Map<String,Integer> sumOfJobByCategories){
		HSSFRow row = null;
		HSSFCell c  = null;
				
				row = sheet.createRow(index);
				c = row.createCell(0);
				c.setCellStyle(WHITE_GREEN_BOLD);
				c.setCellValue("Grand Total :");
				sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 3));
		if (sumOfJobByCategories != null && sumOfJobByCategories.size() > 0) {
			for(String category:sumOfJobByCategories.keySet()){
				c = row.createCell(categoryWiseCellIndexMap.get(category) - 1);
				c.setCellValue(sumOfJobByCategories.get(category));
				c.setCellStyle(WHITE_BLUE_BOLD);
			}
		}
		if (sumOfSubByCategories != null && sumOfSubByCategories.size() > 0) {
			for(String category:sumOfSubByCategories.keySet()){
				c = row.createCell(categoryWiseCellIndexMap.get(category));
				c.setCellValue(sumOfSubByCategories.get(category));
				c.setCellStyle(WHITE_BLUE_BOLD);
			}
		}
		return index;
	}
	
	public byte[] createExcelOpenJOs(List<JobOrder> jobOrderList, String email) {

		byte[] output = null;
		try {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Job Orders List");

			// Setup some styles that we need for the Cells
			setCellStyles(wb);

			setColWidthAndMargin(sheet);

			int rowIndex = 0;
			rowIndex = insertHeaderInfo(sheet, rowIndex, false);
			rowIndex = insertJODetailInfo(sheet, rowIndex, jobOrderList, null);
			String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
			log.info("rowIndex>>" + rowIndex);
			if (rowIndex >= 1) {

				String fileName = "JobOrder_" + timeStamp + ".xls";
				log.info("output>>" + output);
				output = wb.getBytes();
				log.info("output>>" + output);
				log.info("Workbook length in bytes: " + output.length);

				SchedulerEmailBean seb = new SchedulerEmailBean();
				if (email != null) {
					seb.sendEmailWithAttachment(email, "CGIATS: Report of JobOrders in Open status from more than 10 Days",
							"Hi all, <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;Please find the attachment containing list of joborders in open status from more than 10 days.<br/><br/><b>*** This is an automatically generated email, please do not reply ***</b>",
							fileName, wb, rowIndex);
				} else {
					log.info("To Address is Empty ::");
				}

				try {
					FileOutputStream fileOut = new FileOutputStream("./Mail/JobOrder/" + fileName);
					wb.write(fileOut);
					fileOut.close();
					System.out.println("file was copied " + fileName);

				} catch (FileNotFoundException fnfe) {

				}
			}
		} catch (Exception e) {
			log.info("Exception in exporting all records to excel :: " + e);
			e.printStackTrace();
		}
		return output;
	}

	public void setColWidthAndMargin(HSSFSheet sheet) {
		// Set Column Widths
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 6000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 6000);
		sheet.setColumnWidth(10, 6000);

		// Setup the Page margins - Left, Right, Top and Bottom
		sheet.setMargin(HSSFSheet.LeftMargin, 0.25);
		sheet.setMargin(HSSFSheet.RightMargin, 0.25);
		sheet.setMargin(HSSFSheet.TopMargin, 0.75);
		sheet.setMargin(HSSFSheet.BottomMargin, 0.75);
	}

	private void setCellStyles(HSSFWorkbook wb) {

		// font size 10
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 10);

		// Simple style
		cs = wb.createCellStyle();
		cs.setFont(f);

		// Bold Fond
		HSSFFont bold = wb.createFont();
		bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bold.setFontHeightInPoints((short) 10);

		// Bold style
		csBold = wb.createCellStyle();
		csBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
		csBold.setBottomBorderColor(HSSFColor.BLACK.index);
		csBold.setFont(bold);

//		cs.setBorderTop((short) 1);
//		cs.setBorderBottom((short) 1);
//		cs.setBorderLeft((short) 1);
//		cs.setBorderRight((short) 1);
		cs.setFont(f);
	}

	private int insertHeaderInfo(HSSFSheet sheet, int index, boolean isExtraColumnRequired) {

		int rowIndex = index;
		HSSFRow row = null;
		HSSFCell c = null;

		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("Job Order #");
		c.setCellStyle(csBold);

		c = row.createCell(1);
		c.setCellValue("Priority");
		c.setCellStyle(csBold);

		c = row.createCell(2);
		c.setCellValue("Status");
		c.setCellStyle(csBold);

		c = row.createCell(3);
		c.setCellValue("Position Type");
		c.setCellStyle(csBold);

		c = row.createCell(4);
		c.setCellValue("Title");
		c.setCellStyle(csBold);

		c = row.createCell(5);
		c.setCellValue("Client");
		c.setCellStyle(csBold);

		c = row.createCell(6);
		c.setCellValue("Location");
		c.setCellStyle(csBold);

		c = row.createCell(7);
		c.setCellValue("DM");
		c.setCellStyle(csBold);

		c = row.createCell(8);
		c.setCellValue("Assigned To");
		c.setCellStyle(csBold);

		c = row.createCell(9);
		c.setCellValue("Created Date");
		c.setCellStyle(csBold);

		c = row.createCell(10);
		c.setCellValue("Updated Date");
		c.setCellStyle(csBold);
		if (isExtraColumnRequired) {
			c = row.createCell(11);
			c.setCellValue("No Of Resumes Required");
			c.setCellStyle(csBold);

			c = row.createCell(12);
			c.setCellValue("Filled Submittals");
			c.setCellStyle(csBold);
		}
		rowIndex++;
		return rowIndex;
	}

	private int insertDetailInfo(HSSFSheet sheet, int index, JobOrder jobOrder, Map<Integer, Integer> jobOrderWithCountMap) {

		HSSFRow row = null;
		HSSFCell c = null;
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		row = sheet.createRow(index);
		c = row.createCell(0);
		if (jobOrder.getId() != null)
			c.setCellValue(jobOrder.getId());
		c.setCellStyle(cs);

		c = row.createCell(1);
		c.setCellValue(jobOrder.getPriority().name());
		c.setCellStyle(cs);

		c = row.createCell(2);
		c.setCellValue(jobOrder.getStatus().name());
		c.setCellStyle(cs);

		c = row.createCell(3);
		c.setCellValue(jobOrder.getJobType().name());
		c.setCellStyle(cs);

		c = row.createCell(4);
		if (jobOrder.getTitle() != null)
			c.setCellValue(jobOrder.getTitle());
		else
			c.setCellValue("");
		c.setCellStyle(cs);

		c = row.createCell(5);
		if (jobOrder.getCustomer() != null)
			c.setCellValue(jobOrder.getCustomer());
		else
			c.setCellValue("");
		c.setCellStyle(cs);

		c = row.createCell(6);
		if (jobOrder.getLocation() != null)
			c.setCellValue(jobOrder.getLocation());
		else
			c.setCellValue("");
		c.setCellStyle(cs);

		c = row.createCell(7);
		c.setCellValue(jobOrder.getCreatedBy());
		c.setCellStyle(cs);

		c = row.createCell(8);
		if (jobOrder.getAssignedToUser() != null)
			c.setCellValue(jobOrder.getAssignedToUser().getFullName());
		else
			c.setCellValue("");
		c.setCellStyle(cs);

		c = row.createCell(9);
		c.setCellValue(df.format(jobOrder.getCreatedOn()));
		c.setCellStyle(cs);

		c = row.createCell(10);
		if (jobOrder.getUpdatedOn() != null)
			c.setCellValue(df.format(jobOrder.getUpdatedOn()));
		else
			c.setCellValue("");
		c.setCellStyle(cs);

		if (jobOrderWithCountMap != null) {
			c = row.createCell(11);
			if (jobOrder.getNoOfResumesRequired() != null) {
				c.setCellValue(jobOrder.getNoOfResumesRequired());
			} else {
				c.setCellValue("");
			}
			c.setCellStyle(cs);

			c = row.createCell(12);
			if (jobOrderWithCountMap.get(jobOrder.getId()) != null) {
				c.setCellValue(jobOrderWithCountMap.get(jobOrder.getId()));
			} else {
				c.setCellValue("");
			}
			c.setCellStyle(cs);
		}
		return index;

	}

	private int insertJODetailInfo(HSSFSheet sheet, int index, List<JobOrder> jobOrders, Map<Integer, Integer> jobOrderWithCountMap) {
		int rowIndex = 0;

		try {
			for (int i = 0; i < jobOrders.size(); i++) {
				rowIndex = index + i;

				insertDetailInfo(sheet, rowIndex, jobOrders.get(i), jobOrderWithCountMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowIndex;
	}

}