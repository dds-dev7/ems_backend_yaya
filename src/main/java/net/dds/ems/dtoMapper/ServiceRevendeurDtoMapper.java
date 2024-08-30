package net.dds.ems.dtoMapper;

import net.dds.ems.dto.ServiceRevendeurDto;
import net.dds.ems.entity.ServiceRevendeur;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ServiceRevendeurDtoMapper implements Function<ServiceRevendeur, ServiceRevendeurDto>{

    @Override
    public ServiceRevendeurDto apply(ServiceRevendeur serviceRevendeur) {
        return new ServiceRevendeurDto(
                serviceRevendeur.getId(),
                serviceRevendeur.getSoldeAutorise(),
                serviceRevendeur.getService().getNom(),
                serviceRevendeur.getRevendeur().getNom(),
                serviceRevendeur.getMontantCaisse());
    }
}
