package net.dds.ems.dtoMapper;

import net.dds.ems.dto.CompteInternationaleDto;
import net.dds.ems.entity.CompteInternationale;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CompteInternationaleDtoMapper implements Function<CompteInternationale, CompteInternationaleDto>{

    @Override
    public CompteInternationaleDto apply(CompteInternationale compteInternationale) {
        return new CompteInternationaleDto(
                compteInternationale.getId(),
                compteInternationale.getPays(),
                compteInternationale.getMnc(),
                compteInternationale.getCommission()
                );
    }
}
