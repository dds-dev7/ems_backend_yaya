package net.dds.ems.repository;

import net.dds.ems.entity.SousCaissier;
import net.dds.ems.entity.TransactionRecouvreur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecouvreurRepository extends JpaRepository<TransactionRecouvreur,Integer> {
}
