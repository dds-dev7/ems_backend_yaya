package net.dds.ems.repository;

import net.dds.ems.entity.Admin;
import net.dds.ems.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Transactional
     void deleteByRole(Role role);

}
