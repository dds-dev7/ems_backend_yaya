package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.dto.TransactionRecouvreurDto;
import net.dds.ems.entity.TransactionRecouvreur;
import net.dds.ems.entity.TransactionRecouvreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransactionRecouvreurDtoMapper implements Function<TransactionRecouvreur, TransactionRecouvreurDto>{

    @Autowired
    private RecouvreurDtoMapper recouvreurDtoMapper;

    @Autowired
    private CaissierDtoMapper caissierDtoMapper;

    @Override
    public TransactionRecouvreurDto apply(TransactionRecouvreur recouvrement) {
        return new TransactionRecouvreurDto(
                recouvrement.getId(),
                recouvrement.getType(),
                recouvrement.getMontant(),
                recouvreurDtoMapper.apply(recouvrement.getEffectuerPar()),
                caissierDtoMapper.apply(recouvrement.getAssignerA()),
                recouvrement.getDate());
    }

    public TransactionRecouvreur toEntity(TransactionRecouvreurDto transactionRecouvreurDto) {
        TransactionRecouvreur transactionRecouvreur = new TransactionRecouvreur();
        transactionRecouvreur.setId(transactionRecouvreurDto.id());
        transactionRecouvreur.setType(transactionRecouvreurDto.type());
        transactionRecouvreur.setMontant(transactionRecouvreurDto.montant());
        transactionRecouvreur.setEffectuerPar(recouvreurDtoMapper.toEntity(transactionRecouvreurDto.effectuerPar()));
        transactionRecouvreur.setAssignerA(caissierDtoMapper.toEntity(transactionRecouvreurDto.assignerA()));
        return transactionRecouvreur;
    }
}
