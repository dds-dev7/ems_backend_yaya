package net.dds.ems.dtoMapper;

import net.dds.ems.dto.TransfertInternationalDto;
import net.dds.ems.entity.TransfertInternational;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransfertInternationalDtoMapper implements Function<TransfertInternational, TransfertInternationalDto>{

    @Override
    public TransfertInternationalDto apply(TransfertInternational transfertInternational) {
        return new TransfertInternationalDto(
                transfertInternational.getId(),
                transfertInternational.getCompte().getMnc(),
                transfertInternational.getDestinateur(),
                transfertInternational.getExpediteur().getNom(),
                transfertInternational.getMontant(),
                transfertInternational.getFrais(),
                transfertInternational.getDate());
    }
}
