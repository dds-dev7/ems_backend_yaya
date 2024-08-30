package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RechargeDto;
import net.dds.ems.entity.Recharge;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RechargeDtoMapper implements Function<Recharge, RechargeDto>{

    @Override
    public RechargeDto apply(Recharge recharge) {
        return new RechargeDto(
                recharge.getId(),
                recharge.getType(),
                recharge.getMontant(),
                recharge.getDate(),
                recharge.getStatut(),
                recharge.getService().getNom(),
                recharge.getAdmin().getNom(),
                recharge.getAssignerA().getNom(),
                recharge.getActeur()
                );
    }
}
