package net.dds.ems.repository;

import net.dds.ems.entity.Role;
import net.dds.ems.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findByNom(String nom);

    @Modifying
    @Query("DELETE FROM Utilisateur u WHERE u.role = :role AND TYPE(u) = :type")
    void deleteByRoleAndType(@Param("role") Role role, @Param("type") String type);

    @Query("SELECT COALESCE(MAX(u.numeroIdentifiant), 10000) FROM Utilisateur u WHERE TYPE(u) = :type")
    Integer findMaxNumeroIdentifiantByType(@Param("type") Class<?> type);

}
