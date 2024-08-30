package net.dds.ems.repository;

import net.dds.ems.entity.Notification;
import net.dds.ems.entity.NumeroAutorise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeroAutoriseRepository extends JpaRepository<NumeroAutorise,Integer> {
}
