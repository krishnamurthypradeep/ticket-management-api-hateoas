package com.myapp.spring.service;

import java.util.List;

import com.myapp.spring.api.model.TicketDto;
import com.myapp.spring.api.model.TicketFilterDto;
import com.myapp.spring.model.Ticket;

public interface TicketService {
	
	TicketDto assignAgentToTicket(Long ticketId, Long agentId) throws Exception;
	
	
	TicketDto resolveTicket(Long ticketId) throws Exception;
	
	TicketDto closeTicket(Long ticketId) throws Exception;
	
	TicketDto createTicket(TicketDto ticketDto);
	
	TicketDto updateTicket(Long ticketId,TicketDto ticketDto) throws Exception;
	
	
	TicketDto getTicketById(Long ticketId) throws Exception;
	
	List<TicketDto> getTickets(TicketFilterDto ticketFilterDto);
	

}
