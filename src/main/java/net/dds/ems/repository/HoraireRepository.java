package net.dds.ems.repository;

import net.dds.ems.entity.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoraireRepository extends JpaRepository<Horaire,Integer> {
    List<Horaire> findByRevendeurId(Integer revendeurId);

    Horaire findByRevendeurIdAndJour(Integer revendeurId, String jour);

}
