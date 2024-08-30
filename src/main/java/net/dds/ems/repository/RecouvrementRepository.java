package net.dds.ems.repository;

import net.dds.ems.entity.Recharge;
import net.dds.ems.entity.Recouvrement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecouvrementRepository extends JpaRepository<Recouvrement,Integer> {
}
