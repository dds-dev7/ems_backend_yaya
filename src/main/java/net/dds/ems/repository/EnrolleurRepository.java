package net.dds.ems.repository;

import net.dds.ems.entity.CompteInternationale;
import net.dds.ems.entity.Enrolleur;
import net.dds.ems.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EnrolleurRepository extends JpaRepository<Enrolleur,Integer> {
    @Transactional
    void deleteByRole(Role role);


    @Query("SELECT MAX(e.numeroIdentifiant) FROM Enrolleur e")
    Integer findMaxNumeroIdentifiant();
}
