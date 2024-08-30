package net.dds.ems.repository;

import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RecouvreurRepository extends JpaRepository<Recouvreur,Integer> {
    @Transactional
    public void deleteByRole(Role role);

    @Query("SELECT MAX(r.numeroIdentifiant) FROM Recouvreur r")
    Integer findMaxNumeroIdentifiant();
}
