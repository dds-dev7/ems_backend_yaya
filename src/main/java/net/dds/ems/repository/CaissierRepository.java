package net.dds.ems.repository;

import net.dds.ems.entity.Agent;
import net.dds.ems.entity.Caissier;
import net.dds.ems.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CaissierRepository extends JpaRepository<Caissier,Integer> {
    @Transactional
    void deleteByRole(Role role);

    @Query("SELECT MAX(c.numeroIdentifiant) FROM Caissier c")
    Integer findMaxNumeroIdentifiant();

}
