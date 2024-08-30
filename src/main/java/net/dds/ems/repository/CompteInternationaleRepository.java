package net.dds.ems.repository;

import net.dds.ems.entity.Caissier;
import net.dds.ems.entity.CompteInternationale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteInternationaleRepository extends JpaRepository<CompteInternationale,Integer> {
}
