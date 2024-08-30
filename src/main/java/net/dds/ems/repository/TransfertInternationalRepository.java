package net.dds.ems.repository;

import net.dds.ems.entity.TransactionRecouvreur;
import net.dds.ems.entity.TransfertInternational;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfertInternationalRepository extends JpaRepository<TransfertInternational,Integer> {
}
