package net.dds.ems.repository;

import net.dds.ems.entity.Role;
import net.dds.ems.entity.ServiceRevendeur;
import net.dds.ems.entity.SousCaissier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SousCaissierRepository extends JpaRepository<SousCaissier,Integer> {
    @Transactional
    void deleteByRole(Role role);


    @Query("SELECT MAX(sc.numeroIdentifiant) FROM SousCaissier sc")
    Integer findMaxNumeroIdentifiant();
}
