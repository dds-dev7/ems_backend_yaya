package net.dds.ems.dtoMapper;

import net.dds.ems.dto.TransfertInternationalDto;
import net.dds.ems.entity.TransfertInternational;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransfertInternationalDtoMapper implements Function<TransfertInternational, TransfertInternationalDto>{

    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Autowired
    private CompteInternationaleDtoMapper compteInternationaleDtoMapper;

    @Override
    public TransfertInternationalDto apply(TransfertInternational transfertInternational) {
        return new TransfertInternationalDto(
                transfertInternational.getId(),
                compteInternationaleDtoMapper.apply(transfertInternational.getCompte()),
                transfertInternational.getDestinateur(),
                revendeurDtoMapper.apply(transfertInternational.getExpediteur()),
                transfertInternational.getMontant(),
                transfertInternational.getFrais(),
                transfertInternational.getDate());
    }
}
