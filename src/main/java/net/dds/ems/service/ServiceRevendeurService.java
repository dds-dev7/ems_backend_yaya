package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.ServiceRevendeurDto;
import net.dds.ems.dtoMapper.ServiceRevendeurDtoMapper;
import net.dds.ems.entity.ServiceRevendeur;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Service;
import net.dds.ems.repository.ServiceRevendeurRepository;
import net.dds.ems.repository.RevendeurRepository;
import net.dds.ems.repository.ServiceRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class ServiceRevendeurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ServiceRevendeurRepository serviceRevendeurRepository;

    @Autowired
    private ServiceRevendeurDtoMapper serviceRevendeurDtoMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private RevendeurRepository revendeurRepository;


    public ServiceRevendeur createServiceRevendeur(ServiceRevendeur serviceRevendeur)  throws Exception{

        Service service = serviceRevendeur.getService();
        if (service != null && service.getId() != 0) {
            Optional<Service> optionalService = serviceRepository.findById(service.getId());
            serviceRevendeur.setService(optionalService.orElseThrow(()-> new EntityNotFoundException("This service doesn't exist")));
        }else{
            throw new BadRequestException("Error getting the service");
        }

        Revendeur revendeur = serviceRevendeur.getRevendeur();
        if (revendeur != null && revendeur.getId() != 0) {
            Optional<Revendeur> optionalRevendeur= utilisateurRepository.findById(revendeur.getId())
                    .filter(utilisateur -> utilisateur instanceof Revendeur)
                    .map(utilisateur -> (Revendeur) utilisateur);
            serviceRevendeur.setRevendeur(optionalRevendeur.orElseThrow(()-> new EntityNotFoundException("This revendeur doesn't exist")));
        }else{
            throw new BadRequestException("Error getting revendeur");
        }

        try{

            this.serviceRevendeurRepository.save(serviceRevendeur);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.serviceRevendeurRepository.save(serviceRevendeur);
    }


    public ServiceRevendeur search(int id) {
        Optional<ServiceRevendeur> optionalServiceRevendeur = this.serviceRevendeurRepository.findById(id);
        return optionalServiceRevendeur.orElseThrow(
                ()-> new EntityNotFoundException("Aucune serviceRevendeur n'existe avec cet id")
        );
    }

    public Stream<ServiceRevendeurDto> showServiceRevendeur() {
        if(this.serviceRevendeurRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No ServiceRevendeur found")   ;
        }
        return this.serviceRevendeurRepository.findAll().stream().map(serviceRevendeurDtoMapper);
    }

    public Stream<ServiceRevendeurDto> showServiceRevendeurById(int id) {
        if(this.serviceRevendeurRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("This ServiceRevendeur cannot be found")   ;
        }
        return this.serviceRevendeurRepository.findById(id).stream().map(serviceRevendeurDtoMapper);
    }

    public ServiceRevendeur updateServiceRevendeur(int id, ServiceRevendeur serviceRevendeur) throws Exception {
        ServiceRevendeur existingServiceRevendeur= this.search(id);

        if (serviceRevendeur.getSoldeAutorise() != null) {
            existingServiceRevendeur.setSoldeAutorise(serviceRevendeur.getSoldeAutorise());
        }

        if (serviceRevendeur.getService() != null && serviceRevendeur.getService().getId() != null) {
            int idService = serviceRevendeur.getService().getId();
            Optional<Service> optionalService = serviceRepository.findById(idService);
            existingServiceRevendeur.setService(optionalService.orElseThrow(() -> new EntityNotFoundException("Service non trouvé")));
        }

        if (serviceRevendeur.getRevendeur() != null && serviceRevendeur.getRevendeur().getId() != null) {
            int idRevendeur = serviceRevendeur.getRevendeur().getId();
            Optional<Revendeur> optionalRevendeur = utilisateurRepository.findById(idRevendeur)
                    .filter(utilisateur -> utilisateur instanceof Revendeur)
                    .map(utilisateur -> (Revendeur) utilisateur);
            existingServiceRevendeur.setRevendeur(optionalRevendeur.orElseThrow(() -> new EntityNotFoundException("Revendeur non trouvé")));
        }

        if (serviceRevendeur.getMontantCaisse() != null) {
            existingServiceRevendeur.setMontantCaisse(serviceRevendeur.getMontantCaisse());
        }

        try{
            this.serviceRevendeurRepository.save(existingServiceRevendeur);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating serviceRevendeur");
        }
        return this.serviceRevendeurRepository.save(existingServiceRevendeur);
    }

    public void deleteServiceRevendeur(int id) {
        if (!this.serviceRevendeurRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune serviceRevendeur n'existe avec cet id");
        this.serviceRevendeurRepository.deleteById(id);
    }
}
