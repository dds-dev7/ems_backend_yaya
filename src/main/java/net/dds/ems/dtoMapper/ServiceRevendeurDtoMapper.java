package net.dds.ems.dtoMapper;

import net.dds.ems.dto.ServiceRevendeurDto;
import net.dds.ems.entity.ServiceRevendeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ServiceRevendeurDtoMapper implements Function<ServiceRevendeur, ServiceRevendeurDto>{

    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Autowired
    private ServiceDtoMapper serviceDtoMapper;

    @Override
    public ServiceRevendeurDto apply(ServiceRevendeur serviceRevendeur) {
        return new ServiceRevendeurDto(
                serviceRevendeur.getId(),
                serviceRevendeur.getSoldeAutorise(),
                serviceDtoMapper.apply(serviceRevendeur.getService()),
                revendeurDtoMapper.apply(serviceRevendeur.getRevendeur()),
                serviceRevendeur.getMontantCaisse());
    }
}
