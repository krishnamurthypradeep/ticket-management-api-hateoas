package com.myapp.spring.util;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myapp.spring.api.model.TicketDto;
import com.myapp.spring.api.model.TicketDtoModel;
import com.myapp.spring.controller.TicketController;

import jakarta.servlet.http.HttpServletRequest;

// Option1: 
// Spring Data Rest -> Hateoas
// add the links

// Option2:

// Spring Hateoas

// Get The Entities From Service Layer then convert it to Dto and add the links


@Component
public class TicketDtoModelAssembler extends RepresentationModelAssemblerSupport<TicketDto, TicketDtoModel>  {

	private final HttpServletRequest request;

	  public TicketDtoModelAssembler(HttpServletRequest request) {
	    super(TicketController.class, TicketDtoModel.class);
	    this.request = request;
	  }

	  @Override
	  public TicketDtoModel toModel(TicketDto entity) {
		  TicketDtoModel model = instantiateModel(entity);
		  System.out.println("entity  ******* "+entity);
		  
		  
	   
		  model.setId(entity.id());
		  model.setDescription(entity.description());
		  model.setCreatedDate(entity.createdDate());
		  model.setClosedDate(entity.closedDate());
		  model.setStatus(entity.status());
		  model.setAssignedAgent(entity.assignedAgent());
		  
		  System.out.println("model  ******* "+model.getId());
		  try {
			model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class)
			    .getTicket(entity.id())).withSelfRel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    String currentUriString =
	        ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();

	    if (currentUriString.endsWith("/tickets/" + entity.id())
	        || ("POST".equals(request.getMethod())
	        && currentUriString.endsWith("/tickets"))) {
	      try {
			model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class)
			       .getTickets(null)).withRel("tickets"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

	    return model;
	  }
	}
	

