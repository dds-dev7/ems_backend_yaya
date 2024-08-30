package net.dds.ems.repository;

import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
