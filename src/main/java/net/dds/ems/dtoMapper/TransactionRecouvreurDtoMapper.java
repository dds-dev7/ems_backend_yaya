package net.dds.ems.dtoMapper;

import net.dds.ems.dto.TransactionRecouvreurDto;
import net.dds.ems.entity.TransactionRecouvreur;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransactionRecouvreurDtoMapper implements Function<TransactionRecouvreur, TransactionRecouvreurDto>{

    @Override
    public TransactionRecouvreurDto apply(TransactionRecouvreur recouvrement) {
        return new TransactionRecouvreurDto(
                recouvrement.getId(),
                recouvrement.getType(),
                recouvrement.getMontant(),
                recouvrement.getEffectuerPar().getNom(),
                recouvrement.getAssignerA().getNom(),
                recouvrement.getDate());
    }
}
