package net.dds.ems.repository;

import net.dds.ems.entity.Admin;
import net.dds.ems.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent,Integer> {
}
