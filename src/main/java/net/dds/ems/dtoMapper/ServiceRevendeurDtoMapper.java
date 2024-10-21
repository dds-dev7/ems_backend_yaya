package net.dds.ems.dtoMapper;

import net.dds.ems.dto.ServiceDto;
import net.dds.ems.dto.ServiceRevendeurDto;
import net.dds.ems.entity.Service;
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

    public ServiceRevendeur toEntity(ServiceRevendeurDto serviceRevendeurDto) {
        ServiceRevendeur serviceRevendeur = new ServiceRevendeur();
        serviceRevendeur.setId(serviceRevendeurDto.id());
        serviceRevendeur.setSoldeAutorise(serviceRevendeurDto.soldeAutorise());
        serviceRevendeur.setService(serviceDtoMapper.toEntity(serviceRevendeurDto.service()));
        serviceRevendeur.setRevendeur(revendeurDtoMapper.toEntity(serviceRevendeurDto.revendeur()));
        serviceRevendeur.setMontantCaisse(serviceRevendeurDto.montantCaisse());
        return serviceRevendeur;
    }
}
