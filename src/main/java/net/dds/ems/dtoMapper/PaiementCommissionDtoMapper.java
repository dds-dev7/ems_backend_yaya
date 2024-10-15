package net.dds.ems.dtoMapper;

import net.dds.ems.dto.PaiementCommissionDto;
import net.dds.ems.entity.PaiementCommission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PaiementCommissionDtoMapper implements Function<PaiementCommission, PaiementCommissionDto>{

    @Autowired
    private ServiceRevendeurDtoMapper serviceRevendeurDtoMapper;

    @Override
    public PaiementCommissionDto apply(PaiementCommission paiementCommission) {
        return new PaiementCommissionDto(
                paiementCommission.getId(),
                paiementCommission.getStatut(),
                paiementCommission.getMontant(),
                serviceRevendeurDtoMapper.apply(paiementCommission.getServiceRevendeur()),
                paiementCommission.getAdmin().getNom(),
                paiementCommission.getDate());
    }
}
