package net.dds.ems.repository;

import net.dds.ems.entity.Role;
import net.dds.ems.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Integer> {
}
