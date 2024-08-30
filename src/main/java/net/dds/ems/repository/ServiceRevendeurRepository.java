package net.dds.ems.repository;

import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Service;
import net.dds.ems.entity.ServiceRevendeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ServiceRevendeurRepository extends JpaRepository<ServiceRevendeur,Integer> {
    List<ServiceRevendeur> findByRevendeur(Revendeur revendeur);

    Optional<ServiceRevendeur> findFirstByRevendeur(Revendeur revendeur);


}
