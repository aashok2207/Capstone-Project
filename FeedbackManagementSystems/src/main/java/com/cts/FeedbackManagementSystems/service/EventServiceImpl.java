package com.cts.FeedbackManagementSystems.service;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cts.FeedbackManagementSystems.entity.Event;
import com.cts.FeedbackManagementSystems.model.request.EmailMessage;
import com.cts.FeedbackManagementSystems.repo.EventRepository;
import com.cts.FeedbackManagementSystems.util.PrepareExcelFile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private PrepareExcelFile prepareExcelFile;
	
	@Value("${gmail.userName}")
	private String userName;
	
	@Value("${gmail.password}")
	private String password;

	@Override
	public Flux<Event> getAllEvents(){
		return eventRepository.findAll();
	}


	@Override
	public Mono<Event> saveEvent(Event event) {
		return eventRepository.save(event);
	}
	
	@Override
	public Mono<Event> searchEventById(Integer id) {
		return eventRepository.findById(id);
	}

	@Override
	public Flux<Event> searchEventsById(String eventId) {
		return eventRepository.findAllByEventId(eventId);
	}

	@Override
	public Mono<Void> deleteEvent(Integer eventId) {
		return eventRepository.deleteById(eventId);
	}


	@Override
	public Mono<Void> deleteAllEvents() {
		return eventRepository.deleteAll();
	}

	@Override
	public Mono<String> sendMail(EmailMessage emailMessage) {
		prepareExcelFile.generateExcelFile();
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("checkMail21@gmail.com", "2***********");
			}
		};
		Session session = Session.getInstance(props, auth);
		//Session session = Session.getDefaultInstance(props, auth);
		
		try {
		Message msg = new MimeMessage(session);
		msg.addHeader("Content-Transfer-Encoding", "8bit");
		msg.setFrom(new InternetAddress(userName, false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailMessage.getTo_address()));
		msg.setSubject(emailMessage.getSubject());
		msg.setContent(emailMessage.getBody(), "text/html");
		msg.setSentDate(new Date());
		msg.setText("Email received");
		
		BodyPart messageBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();

		String filename = "EventsReports.xlsx";
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		
		Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Mono.just("Report has been sent successfully. Please check your email");
	}
	
	
}
