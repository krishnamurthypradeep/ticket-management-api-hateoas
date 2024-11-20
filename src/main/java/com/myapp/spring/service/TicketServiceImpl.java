package com.myapp.spring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myapp.spring.api.model.TicketDto;
import com.myapp.spring.api.model.TicketFilterDto;
import com.myapp.spring.exception.AgentNotFoundException;
import com.myapp.spring.exception.InvalidTicketStateException;
import com.myapp.spring.exception.MissingDescriptionException;
import com.myapp.spring.model.Agent;
import com.myapp.spring.model.Status;
import com.myapp.spring.model.Ticket;
import com.myapp.spring.repository.AgentRepository;
import com.myapp.spring.repository.TicketRepository;
import com.myapp.spring.util.ErrorMessages;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    private final AgentRepository agentRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, AgentRepository agentRepository) {
        this.ticketRepository = ticketRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public TicketDto createTicket(TicketDto ticketDto) {
        
    	if(ticketDto.description() == null || ticketDto.description().isEmpty()) {
    		throw new MissingDescriptionException(ErrorMessages.DESCRIPTION_REQUIRED);
    	}

        Ticket newTicket = new Ticket();
        newTicket.setDescription(ticketDto.description());
        newTicket.setStatus(Status.NEW);
        newTicket.setCreatedDate(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(newTicket);

        return convertToDto(savedTicket);
    }

    @Override
    public TicketDto assignAgentToTicket(Long ticketId, Long agentId) throws Exception {
        Ticket existingTicket = getTicket(ticketId);

        if(existingTicket.getStatus()!= Status.NEW) {
        	throw new InvalidTicketStateException(ErrorMessages.DESCRIPTION_REQUIRED);
        }
        

        Agent assignedAgent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("AGENT_NOT_FOUND"));

        existingTicket.setStatus(Status.IN_PROGRESS);
        existingTicket.setAssignedAgent(assignedAgent);

        Ticket savedTicket = ticketRepository.save(existingTicket);

        return convertToDto(savedTicket);
    }

    @Override
    public TicketDto resolveTicket(Long ticketId) throws Exception {
        Ticket existingTicket = getTicket(ticketId);

       

        existingTicket.setStatus(Status.RESOLVED);
        Ticket updatedTicket = ticketRepository.save(existingTicket);

        return convertToDto(updatedTicket);
    }

    @Override
    public TicketDto closeTicket(Long ticketId) throws Exception {
        Ticket existingTicket = getTicket(ticketId);

        validateTicketBeforeClosing(existingTicket);

        existingTicket.setStatus(Status.CLOSED);
        existingTicket.setClosedDate(LocalDateTime.now());
        Ticket updatedTicket = ticketRepository.save(existingTicket);

        return convertToDto(updatedTicket);
    }

    @Override
    public TicketDto updateTicket(Long ticketId, TicketDto ticketDto) throws Exception {
        Ticket existingTicket = getTicket(ticketId);

        if(existingTicket.getStatus() == Status.CLOSED) {
        	throw new InvalidTicketStateException("Closed Tickets Cannot Be Updated");
        }
        

        existingTicket.setDescription(ticketDto.description());
        existingTicket.setResolutionSummary(ticketDto.resolutionSummary());
        existingTicket.setClosedDate(ticketDto.closedDate());
        existingTicket.setStatus(ticketDto.status());
        Ticket updatedTicket = ticketRepository.save(existingTicket);

        return convertToDto(updatedTicket);
    }

    @Override
    public TicketDto getTicketById(Long ticketId) throws Exception {
        Ticket existingTicket = getTicket(ticketId);

        return convertToDto(existingTicket);
    }

    @Override
    public List<TicketDto> getTickets(TicketFilterDto ticketFilterDto) {
       

        List<Ticket> filteredTickets = ticketRepository.findWithFilters(
                ticketFilterDto.status(),
                ticketFilterDto.startDate(),
                ticketFilterDto.endDate(),
                ticketFilterDto.assignedAgent()
        );

        return filteredTickets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TicketDto convertToDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedDate(),
                ticket.getClosedDate(),
                ticket.getAssignedAgent() != null ? ticket.getAssignedAgent().getName() : null,
                ticket.getResolutionSummary()
        );
    }

    private Ticket getTicket(Long ticketId) throws Exception {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new Exception("TICKET_NOT_FOUND"));
        return existingTicket;
    }

    private void validateTicketBeforeClosing(Ticket existingTicket) throws Exception {
        

        if (existingTicket.getStatus() != Status.RESOLVED) {
            throw new InvalidTicketStateException("ONLY_RESOLVED_TICKETS_CAN_BE_CLOSED");
        }
    }
}
