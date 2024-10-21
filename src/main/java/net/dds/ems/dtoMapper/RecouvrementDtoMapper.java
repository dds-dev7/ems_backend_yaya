package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RecouvrementDto;
import net.dds.ems.dto.RecouvrementDto;
import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.entity.Recouvrement;
import net.dds.ems.entity.Recouvrement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecouvrementDtoMapper implements Function<Recouvrement, RecouvrementDto>{

    @Autowired
    private ServiceDtoMapper serviceDtoMapper;

    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Autowired
    private RecouvreurDtoMapper recouvreurDtoMapper;

    @Override
    public RecouvrementDto apply(Recouvrement recouvrement) {
        return new RecouvrementDto(
                recouvrement.getId(),
                recouvrement.getMontant(),
                serviceDtoMapper.apply(recouvrement.getService()),
                recouvreurDtoMapper.apply(recouvrement.getAgentRecouvreur()),
                revendeurDtoMapper.apply(recouvrement.getAgentRevendeur()),
                recouvrement.getDate());
    }

    public Recouvrement toEntity(RecouvrementDto recouvrementDTO) {
        Recouvrement recouvrement = new Recouvrement();
        recouvrement.setId(recouvrementDTO.id());
        recouvrement.setMontant(recouvrementDTO.montant());
        recouvrement.setService(serviceDtoMapper.toEntity(recouvrementDTO.service()));
        recouvrement.setAgentRecouvreur(recouvreurDtoMapper.toEntity(recouvrementDTO.agentRecouvreur()));
        recouvrement.setAgentRevendeur(revendeurDtoMapper.toEntity(recouvrementDTO.agentRevendeur()));
        recouvrement.setDate(recouvrementDTO.date());
        return recouvrement;
    }
}
