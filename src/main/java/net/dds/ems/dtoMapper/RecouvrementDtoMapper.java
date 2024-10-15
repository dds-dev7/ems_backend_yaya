package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RecouvrementDto;
import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.entity.Recouvrement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecouvrementDtoMapper implements Function<Recouvrement, RecouvrementDto>{

    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Autowired
    private RecouvreurDtoMapper recouvreurDtoMapper;

    @Override
    public RecouvrementDto apply(Recouvrement recouvrement) {
        return new RecouvrementDto(
                recouvrement.getId(),
                recouvrement.getMontant(),
                recouvrement.getService().getNom(),
                recouvreurDtoMapper.apply(recouvrement.getAgentRecouvreur()),
                revendeurDtoMapper.apply(recouvrement.getAgentRevendeur()),
                recouvrement.getDate());
    }
}
