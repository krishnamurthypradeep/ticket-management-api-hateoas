package com.myapp.spring.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.myapp.spring.model.Agent;
import com.myapp.spring.model.Status;
import com.myapp.spring.model.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketFilterRepositoryImpl implements TicketFilterRepository{
	
	private static final String STATUS_FIELD = "status";
	private static final String CREATED_DATE_FIELD = "createdDate";
	private static final String ASSIGNED_AGENT_FIELD="assignedAgent";
	private static final String NAME_FIELD = "name";
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
    public List<Ticket> findWithFilters(List<Status> statuses,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate,
                                        String assignedAgent) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = cb.createQuery(Ticket.class);
        Root<Ticket> ticketRoot = query.from(Ticket.class);
        Join<Ticket, Agent> agentJoin = ticketRoot.join(ASSIGNED_AGENT_FIELD, JoinType.LEFT);

        List<Predicate> predicates = buildPredicates(statuses, startDate, endDate, assignedAgent, cb, ticketRoot, agentJoin);

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    private List<Predicate> buildPredicates(List<Status> statuses,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate,
                                            String assignedAgent,
                                            CriteriaBuilder cb,
                                            Root<Ticket> ticketRoot,
                                            Join<Ticket, Agent> agentJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (statuses != null && !statuses.isEmpty()) {
            predicates.add(ticketRoot.get(STATUS_FIELD).in(statuses));
        }

        if (startDate != null && endDate != null) {
            predicates.add(cb.between(ticketRoot.get(CREATED_DATE_FIELD), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(ticketRoot.get(CREATED_DATE_FIELD), startDate));
        } else if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(ticketRoot.get(CREATED_DATE_FIELD), endDate));
        }

        if (assignedAgent != null && !assignedAgent.trim().isEmpty()) {
            predicates.add(cb.equal(agentJoin.get(NAME_FIELD), assignedAgent));
        }

        return predicates;
    }

}



