package net.dds.ems.dtoMapper;

import net.dds.ems.dto.ServiceDto;
import net.dds.ems.dto.ServiceDto;
import net.dds.ems.entity.Service;
import net.dds.ems.entity.Service;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ServiceDtoMapper implements Function<Service, ServiceDto>{

    @Override
    public ServiceDto apply(Service service) {
        return new ServiceDto(
                service.getId(),
                service.getNom(),
                service.getAdmin().getNom());
    }

    public Service toEntity(ServiceDto serviceDTO) {
        Service service = new Service();
        service.setId(serviceDTO.id());
        service.setNom(serviceDTO.nom());
        return service;
    }
}
