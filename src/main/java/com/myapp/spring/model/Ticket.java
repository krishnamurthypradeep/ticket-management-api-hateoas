package com.myapp.spring.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="JPA_NEW_TICKETS")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	
	private String description;
	
	
	private LocalDateTime createdDate;
	
	
	private LocalDateTime closedDate;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	
	private String resolutionSummary;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="assignedAgentId")
	private Agent assignedAgent;
	
	public Ticket() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Ticket(Long id, String description, Status status, LocalDateTime createdDate) {
		this.id = id;
		this.description = description;
		this.status = status;
		this.createdDate = createdDate;
	}



	public Ticket( String description, Status status, LocalDateTime createdDate) {
		
		this.description = description;
		this.status = status;
		this.createdDate = createdDate;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getResolutionSummary() {
		return resolutionSummary;
	}

	public void setResolutionSummary(String resolutionSummary) {
		this.resolutionSummary = resolutionSummary;
	}



	public Agent getAssignedAgent() {
		return assignedAgent;
	}



	public void setAssignedAgent(Agent assignedAgent) {
		this.assignedAgent = assignedAgent;
	}
	
	
	
	
	

}
