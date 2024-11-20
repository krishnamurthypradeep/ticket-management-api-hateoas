package com.myapp.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.spring.model.Agent;


public interface AgentRepository extends JpaRepository<Agent, Long> {

}
