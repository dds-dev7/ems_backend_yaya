package net.dds.ems.dtoMapper;

import net.dds.ems.dto.AdminDto;
import net.dds.ems.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdminDtoMapper implements Function<Admin, AdminDto>{
    @Autowired
    private RoleDtoMapper roleDtoMapper;
    @Override
    public AdminDto apply(Admin admin) {
        return new AdminDto(admin.getNom(), admin.getNumero(),admin.getStatut(),roleDtoMapper.apply(admin.getRole()));
    }
}
