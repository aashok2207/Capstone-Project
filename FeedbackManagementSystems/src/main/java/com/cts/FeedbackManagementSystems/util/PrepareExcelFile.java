package com.cts.FeedbackManagementSystems.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.cts.FeedbackManagementSystems.entity.Event;
import com.cts.FeedbackManagementSystems.repo.EventRepository;

import reactor.core.publisher.Flux;

@Configuration
public class PrepareExcelFile {
	
	@Autowired
	private EventRepository eventRepository;
	
	private static String[] columns = { "EventId","eventName", "BeneficiaryName", "BaseLocation", "CouncilName", "BusinessUnit",
	"EventDescription"};
	List<Event> output = new ArrayList<Event>();
	
	public void generateExcelFile() {
		Flux<Event> eventsValues = eventRepository.findAll();
		eventsValues.subscribe((nextValue) -> {
			output.add(nextValue);
		});
		copyToExcel(output);
	}
	
	private void copyToExcel(List<Event> output) {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Events");

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		for (Event eve : output) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(eve.getEventId());

			row.createCell(1).setCellValue(eve.getEventName());

			row.createCell(2).setCellValue(eve.getBeneficiaryName());

			row.createCell(3).setCellValue(eve.getBaseLocation());

			row.createCell(4).setCellValue(eve.getCouncilName());

			row.createCell(5).setCellValue(eve.getBusinessUnit());
			
			row.createCell(7).setCellValue(eve.getEventDescription());

		}

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("EventsReports.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
