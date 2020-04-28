package com.cts.FeedbackManagementSystems.service;

import com.cts.FeedbackManagementSystems.entity.Event;
import com.cts.FeedbackManagementSystems.model.request.EmailMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
	
	public Flux<Event> getAllEvents();
	
	public Mono<Event> saveEvent(Event event);
	
	public Mono<Event> searchEventById(Integer id);
	
	public Flux<Event> searchEventsById(String eventId);
	
	public Mono<Void> deleteEvent(Integer eventId);
	
	public Mono<Void> deleteAllEvents();
	
	public Mono<String> sendMail(EmailMessage emailMessage);
	

}
