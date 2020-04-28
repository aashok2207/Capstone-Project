package com.cts.FeedbackManagementSystems.exceltodb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.FeedbackManagementSystems.entity.Event;
import com.cts.FeedbackManagementSystems.repo.EventRepository;

import reactor.core.publisher.Flux;

@RestController
public class DataProcessInDbController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@GetMapping(value = "/readAndSaveExcel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Object> saveFile() throws Exception {
		
	   //File localFile = new File("D:\\Capstone Project\\excelData.xlsx"); 
	   File localFile = new File("src/main/resources/excelData.xlsx");
	   FileInputStream file = new FileInputStream(localFile);
	   List<Event> eventList = new ArrayList<Event>();

	   
	   XSSFWorkbook workbook = new XSSFWorkbook(localFile);
       XSSFSheet worksheet =  workbook.getSheetAt(0);
    	  
		 for(int i=1;i<worksheet.getPhysicalNumberOfRows();i++)
		  {
			  Event tempEvents = new Event();
			  XSSFRow row = worksheet.getRow(i);
			 
			  tempEvents.setEventId(row.getCell(0).getStringCellValue());
			  tempEvents.setEventName(row.getCell(1).getStringCellValue());
			  tempEvents.setEventDate(row.getCell(2).getDateCellValue());
			  tempEvents.setBusinessUnit(row.getCell(3).getStringCellValue());
			  tempEvents.setVenue(row.getCell(4).getStringCellValue());
			  tempEvents.setTotalVolunteers((int)row.getCell(5).getNumericCellValue());
			  tempEvents.setTotalParticipants((int)row.getCell(6).getNumericCellValue());
			  tempEvents.setLivesImpacted((int)row.getCell(7).getNumericCellValue());
			  tempEvents.setVolunteerHours((int)row.getCell(8).getNumericCellValue());
			  tempEvents.setTravelHours((int) row.getCell(9).getNumericCellValue());
			  tempEvents.setStatus(row.getCell(10).getStringCellValue());
			  tempEvents.setBaseLocation(row.getCell(11).getStringCellValue());
			  tempEvents.setBeneficiaryName(row.getCell(12).getStringCellValue());
			  tempEvents.setCouncilName(row.getCell(13).getStringCellValue());
			  tempEvents.setEventDescription(row.getCell(14).getStringCellValue());
			  eventList.add(tempEvents);
			  
		  }
		  workbook.close();
		  file.close();
		  return eventRepository.saveAll(eventList)
				 .map(savedEvent -> ResponseEntity.ok(savedEvent));
		
    }

}
