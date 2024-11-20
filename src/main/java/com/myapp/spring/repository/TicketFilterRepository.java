package com.myapp.spring.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.myapp.spring.model.Status;
import com.myapp.spring.model.Ticket;

public interface TicketFilterRepository {

	
	List<Ticket> findWithFilters(List<Status> statuses,
			LocalDateTime startDate,
			LocalDateTime endDate,
			String assignedAgent);
}
