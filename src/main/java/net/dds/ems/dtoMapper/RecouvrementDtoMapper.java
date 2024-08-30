package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RecouvrementDto;
import net.dds.ems.entity.Recouvrement;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecouvrementDtoMapper implements Function<Recouvrement, RecouvrementDto>{

    @Override
    public RecouvrementDto apply(Recouvrement recouvrement) {
        return new RecouvrementDto(
                recouvrement.getId(),
                recouvrement.getMontant(),
                recouvrement.getService().getNom(),
                recouvrement.getAgentRecouvreur().getNom(),
                recouvrement.getAgentRevendeur().getNom(),
                recouvrement.getDate());
    }
}
