package net.dds.ems.dtoMapper;

import net.dds.ems.dto.AdminDto;
import net.dds.ems.entity.Admin;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdminDtoMapper implements Function<Admin, AdminDto>{

    @Override
    public AdminDto apply(Admin admin) {
        return new AdminDto(admin.getNom(), admin.getNumero(),admin.getStatut(),admin.getRole().getNom());
    }
}
