package com.cts.FeedbackManagementSystems.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.FeedbackManagementSystems.entity.Event;
import com.cts.FeedbackManagementSystems.model.Dashboard;
import com.cts.FeedbackManagementSystems.model.request.EmailMessage;
import com.cts.FeedbackManagementSystems.service.EventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EventsController {
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/getEvents")
	@PreAuthorize("hasRole('PMO') or hasRole('ADMIN')")
	public Flux<Event> getAllEvents(){
		return eventService.getAllEvents();
	}
	
	@PostMapping(value = "/addEvent")
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<ResponseEntity<Event>> addEvent(@RequestBody Event event) {
		System.out.println(event.getEventName());
	    System.out.println(event.getBusinessUnit());
		return eventService.saveEvent(event).map(savedEvent -> ResponseEntity.ok(savedEvent));
	}
	
	@GetMapping("/getEvent/{id}")
	@PreAuthorize("hasRole('PMO') or hasRole('ADMIN') or hasRole('POC')")
	public Mono<Event> getEventById(@PathVariable Integer id){
		return eventService.searchEventById(id);
	}
	
	@GetMapping("/getEvents/{eventId}")
	@PreAuthorize("hasRole('PMO') or hasRole('ADMIN') or hasRole('POC')")
	public Flux<Event> getEventsById(@PathVariable String eventId) {
		return eventService.searchEventsById(eventId);
	}
	
	@DeleteMapping(value = "/deleteEvent/{eventId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<Void> deleteEvent(@PathVariable Integer eventId) {
		return eventService.deleteEvent(eventId);
	}
	
	@DeleteMapping(value = "/deleteAllEvents")
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<Void> deleteAllEvents() {
		return eventService.deleteAllEvents();
	}
	
	@PostMapping("/sendEmail")
	public Mono<String> sendMail(@RequestBody EmailMessage emailMessage) { 
		return eventService.sendMail(emailMessage);
	}
	
	@GetMapping(value = "/getDashboard")
	@PreAuthorize("hasRole('PMO') or hasRole('ADMIN')")
	public Flux<Dashboard> dashboardData() {
		Dashboard dashBoard = new Dashboard();
		
		Mono<Long> countMono = eventService.getAllEvents().count();
		countMono.subscribe(subs -> dashBoard.setCount(Integer.parseInt(subs.toString())));
		
		Mono<Integer> totalVolunteersMono = eventService.getAllEvents().map(x -> x.getTotalVolunteers()).reduce(0, (p, q) -> p + q);
		totalVolunteersMono.subscribe(subs -> dashBoard.setTotalVolunteers(Integer.parseInt(subs.toString())));

		Mono<Integer> livesImpactedMono = eventService.getAllEvents().map(x -> x.getLivesImpacted()).reduce(0, (p, q) -> p + q);
		livesImpactedMono.subscribe(subs -> dashBoard.setLivesImpacted(Integer.parseInt(subs.toString())));
		
		Mono<Integer> totalParticipantsMono = eventService.getAllEvents().map(x -> x.getTotalParticipants()).reduce(0, (p, q) -> p + q);
		totalParticipantsMono.subscribe(subs -> dashBoard.setTotalParticipants(Integer.parseInt(subs.toString())));

		List<Dashboard> list = new ArrayList<>();
		list.add(dashBoard);
		

		return Flux.fromIterable(list);
	
	}



}
