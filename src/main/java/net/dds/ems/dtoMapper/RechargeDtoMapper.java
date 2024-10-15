package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RechargeDto;
import net.dds.ems.entity.Recharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RechargeDtoMapper implements Function<Recharge, RechargeDto>{

    @Autowired
    private ServiceDtoMapper serviceDtoMapper;

    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Override
    public RechargeDto apply(Recharge recharge) {
        return new RechargeDto(
                recharge.getId(),
                recharge.getType(),
                recharge.getMontant(),
                recharge.getDate(),
                recharge.getStatut(),
                serviceDtoMapper.apply(recharge.getService()),
                recharge.getAdmin().getNom(),
                revendeurDtoMapper.apply(recharge.getAssignerA()),
                recharge.getActeur()
                );
    }
}
