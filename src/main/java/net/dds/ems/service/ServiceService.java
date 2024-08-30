package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.ServiceDto;
import net.dds.ems.dtoMapper.ServiceDtoMapper;
import net.dds.ems.entity.Service;
import net.dds.ems.repository.ServiceRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceDtoMapper serviceDtoMapper;


    public Service createService(Service service) throws Exception {
        try {
            this.serviceRepository.save(service);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.serviceRepository.save(service);
    }


    public Service search(int id) {
        Optional<Service> optionalService = this.serviceRepository.findById(id);
        return optionalService.orElseThrow(
                () -> new EntityNotFoundException("Aucune service n'existe avec cet id")
        );
    }

    public Stream<ServiceDto> showService() {
        if (this.serviceRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No Service found");
        }
        return this.serviceRepository.findAll().stream().map(serviceDtoMapper);
    }


    public Stream<ServiceDto> showServiceById(int id) {
        if (this.serviceRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("This Service cannot be found");
        }
        return this.serviceRepository.findById(id).stream().map(serviceDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Service updateService(int id, Service service) throws Exception {
        Service existingService = this.search(id);

        if (existingService.getId() == id) {
            existingService.setNom(service.getNom());
        } else {
            throw new EntityNotFoundException("Exception updating the 'service' check your syntax");
        }
        try {
            this.serviceRepository.save(existingService);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating service");
        }
        return this.serviceRepository.save(existingService);
    }

    public void deleteService(int id) {
        if (!this.serviceRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune service n'existe avec cet id");
        this.serviceRepository.deleteById(id);
    }
}
