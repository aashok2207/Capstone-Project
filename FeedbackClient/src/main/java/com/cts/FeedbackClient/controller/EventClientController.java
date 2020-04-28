package com.cts.FeedbackClient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.cts.FeedbackClient.model.Event;

import reactor.core.publisher.Flux;

@RestController
public class EventClientController {
	
	WebClient webClient = WebClient.create("http://localhost:9000");
	
	@GetMapping("/client/getEvents")
	public Flux<Event> getEventsByUsingRetrieve() {
		return webClient.get().uri("/getEvents").retrieve().bodyToFlux(Event.class)
				.log("get all events details in client project");
	}
	
	@GetMapping("/client/retrieve/{eventId}")
	public Flux<Event> getSpecificEventByUsingRetrieve(@PathVariable String eventId) {
		return webClient.get().uri("/getEvents/{eventId}", eventId).retrieve().bodyToFlux(Event.class)
				.log("get particular event in client project");
	}
	
}
