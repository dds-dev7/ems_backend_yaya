package net.dds.ems.dtoMapper;

import net.dds.ems.dto.CompteInternationaleDto;
import net.dds.ems.entity.CompteInternationale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CompteInternationaleDtoMapper implements Function<CompteInternationale, CompteInternationaleDto>{
    @Autowired
    private RoleDtoMapper roleDtoMapper;
    @Override
    public CompteInternationaleDto apply(CompteInternationale compteInternationale) {
        return new CompteInternationaleDto(
                compteInternationale.getId(),
                compteInternationale.getPays(),
                compteInternationale.getMnc(),
                compteInternationale.getCommission(),
                compteInternationale.getAdmin().getNom());
    }

    public CompteInternationale toEntity(CompteInternationaleDto compteInternationaleDTO) {
        CompteInternationale compteInternationale = new CompteInternationale();
        compteInternationale.setId(compteInternationaleDTO.id());
        compteInternationale.setPays(compteInternationaleDTO.pays());
        compteInternationale.setMnc(compteInternationaleDTO.mNC());
        compteInternationale.setCommission(compteInternationaleDTO.commission());
        return compteInternationale;
    }
}
