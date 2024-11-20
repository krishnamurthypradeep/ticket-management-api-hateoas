package com.myapp.spring.api.model;

import java.time.LocalDateTime;
import java.util.List;

import com.myapp.spring.model.Status;

public record TicketFilterDto(List<Status> status,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              String assignedAgent) {
}