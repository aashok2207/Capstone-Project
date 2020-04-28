package com.cts.FeedbackManagementSystems.repo;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cts.FeedbackManagementSystems.entity.Event;

import reactor.core.publisher.Flux;

public interface EventRepository extends ReactiveCrudRepository<Event, Integer> {
	
	@Query("select * from events where event_id= ?")
	public Flux<Event> findAllByEventId(String eventId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * @Query("select * from events where event_id= ?") public Flux<Event>
	 * findAllByEvent_id(String eventId);
	 * 
	 * @Query("select * from events where volunteer_hours= ?") public Flux<Event>
	 * findAllByVolunteer_hours(int volunteerHours);
	 */

}
