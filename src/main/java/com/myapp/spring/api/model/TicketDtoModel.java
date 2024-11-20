package com.myapp.spring.api.model;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.myapp.spring.model.Status;

@Relation(collectionRelation = "tickets")
public class TicketDtoModel extends RepresentationModel<TicketDtoModel> {
	
	private Long id;
    private String description;
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime closedDate;
    private String assignedAgent;
    private String resolutionSummary;
    
    
    

	public TicketDtoModel() {
		// TODO Auto-generated constructor stub
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public Status getStatus() {
		return status;
	}




	public void setStatus(Status status) {
		this.status = status;
	}




	public LocalDateTime getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}




	public LocalDateTime getClosedDate() {
		return closedDate;
	}




	public void setClosedDate(LocalDateTime closedDate) {
		this.closedDate = closedDate;
	}




	public String getAssignedAgent() {
		return assignedAgent;
	}




	public void setAssignedAgent(String assignedAgent) {
		this.assignedAgent = assignedAgent;
	}




	public String getResolutionSummary() {
		return resolutionSummary;
	}




	public void setResolutionSummary(String resolutionSummary) {
		this.resolutionSummary = resolutionSummary;
	}
	
	

}
