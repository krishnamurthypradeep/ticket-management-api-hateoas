package com.myapp.spring.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.api.model.TicketDto;
import com.myapp.spring.api.model.TicketDtoModel;
import com.myapp.spring.api.model.TicketFilterDto;
import com.myapp.spring.service.TicketService;
import com.myapp.spring.util.TicketDtoModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController  {
	
	private TicketService ticketService;
	
	private TicketDtoModelAssembler ticketDtoModelAssembler;

	public TicketController(TicketService ticketService, TicketDtoModelAssembler ticketDtoModelAssembler) {
		this.ticketService = ticketService;
		this.ticketDtoModelAssembler = ticketDtoModelAssembler;
	}
	
	@GetMapping("/{id}")
	  public ResponseEntity<TicketDtoModel> getTicket(@PathVariable("id") Long id)  {
	    TicketDto ticketDto = null;
		try {
			ticketDto = ticketService.getTicketById(id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	    TicketDtoModel ticketModel = ticketDtoModelAssembler.toModel(ticketDto);

	    return ResponseEntity.ok().cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
	    		.eTag(String.valueOf(ticketModel.getId())).body(ticketModel);
	  }

	
		@GetMapping
		public ResponseEntity<CollectionModel<TicketDtoModel>> getTickets( TicketFilterDto ticketFilterDto) throws Exception {
			
			List<TicketDtoModel> tickets =	ticketService.getTickets(ticketFilterDto).stream()
			.map(ticketDtoModelAssembler::toModel).collect(Collectors.toList());
		
			CollectionModel<TicketDtoModel> ticketCollectionModel =
			        CollectionModel.of(
			            tickets, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class)
			                            .getTickets(ticketFilterDto)).withSelfRel());

			    return ResponseEntity.ok(ticketCollectionModel);
		
		}
		
		@PostMapping
		public ResponseEntity<TicketDto> addTicket(@Valid @RequestBody TicketDto ticketDto) throws Exception {
			// TODO Auto-generated method stub
			TicketDto createdTicket = ticketService.createTicket(ticketDto);
			return new ResponseEntity<TicketDto>(createdTicket,HttpStatus.CREATED);
		}
	
		@PutMapping("/{ticketId}")
		public ResponseEntity<TicketDto> updateTicket(@PathVariable Long ticketId, @Valid TicketDto ticketDto) throws Exception {
			// TODO Auto-generated method stub
			
			TicketDto updatedTicketDto= ticketService.updateTicket(Long.valueOf(ticketId), ticketDto);
			return ResponseEntity.ok(updatedTicketDto);
		}
		
	
	
	
	
}




