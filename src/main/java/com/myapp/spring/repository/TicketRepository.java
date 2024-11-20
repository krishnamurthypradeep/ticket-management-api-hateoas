package com.myapp.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.spring.model.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Long> ,TicketFilterRepository{
	
	

}
