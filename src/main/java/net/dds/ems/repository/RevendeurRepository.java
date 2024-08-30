package net.dds.ems.repository;

import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RevendeurRepository extends JpaRepository<Revendeur,Integer> {

    @Transactional
    public void deleteByRole(Role role);

    @Query("SELECT MAX(r.numeroIdentifiant) FROM Revendeur r")
    Integer findMaxNumeroIdentifiant();
}
