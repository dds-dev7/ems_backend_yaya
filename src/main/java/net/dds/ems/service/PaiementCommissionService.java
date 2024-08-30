package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.PaiementCommissionDto;
import net.dds.ems.dtoMapper.PaiementCommissionDtoMapper;
import net.dds.ems.entity.*;
import net.dds.ems.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class PaiementCommissionService {

    @Autowired
    private PaiementCommissionRepository paiementCommissionRepository;

    @Autowired
    private PaiementCommissionDtoMapper paiementCommissionDtoMapper;

    @Autowired
    private ServiceRevendeurRepository serviceRevendeurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public PaiementCommission createPaiementCommission(PaiementCommission paiementCommission)  throws Exception{

        ServiceRevendeur serviceRevendeur = paiementCommission.getServiceRevendeur();
        if (serviceRevendeur != null && serviceRevendeur.getId() != 0) {
            Optional<ServiceRevendeur> optionalServiceRevendeur = serviceRevendeurRepository.findById(serviceRevendeur.getId());
            paiementCommission.setServiceRevendeur(optionalServiceRevendeur.orElseThrow(()-> new EntityNotFoundException("This service doesn't exist")));
        }else{
            throw new BadRequestException("Error getting the service");
        }

        Admin admin = paiementCommission.getAdmin();
        if (admin != null && admin.getId() != 0) {
            Optional<Admin> optionalAdmin= utilisateurRepository.findById(admin.getId())
                    .filter(utilisateur -> utilisateur instanceof Admin)
                    .map(utilisateur -> (Admin) utilisateur);
            paiementCommission.setAdmin(optionalAdmin.orElseThrow(()-> new EntityNotFoundException("This admin doesn't exist")));
        }else{
            throw new BadRequestException("Error getting admin");
        }

        try{

            paiementCommission.setDate(LocalDateTime.now());

            this.paiementCommissionRepository.save(paiementCommission);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
    return this.paiementCommissionRepository.save(paiementCommission);
    }


    public PaiementCommission search(int id) {
        Optional<PaiementCommission> optionalPaiementCommission = this.paiementCommissionRepository.findById(id);
        return optionalPaiementCommission.orElseThrow(
                ()-> new EntityNotFoundException("Aucune paiementCommission n'existe avec cet id")
        );
    }

    public Stream<PaiementCommissionDto> showPaiementCommission() {
        if(this.paiementCommissionRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No PaiementCommission found")   ;
        }
        return this.paiementCommissionRepository.findAll().stream().map(paiementCommissionDtoMapper);
    }

    public Stream<PaiementCommissionDto> showPaiementCommissionById(int id) {
        if(this.paiementCommissionRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("This PaiementCommission cannot be found")   ;
        }
        return this.paiementCommissionRepository.findById(id).stream().map(paiementCommissionDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PaiementCommission updatePaiementCommission(int id, PaiementCommission paiementCommission) throws Exception {
        PaiementCommission existingPaiementCommission= this.search(id);

        if (existingPaiementCommission.getId() == id) {
            if (paiementCommission.getStatut() != null) {
                existingPaiementCommission.setStatut(paiementCommission.getStatut());
            }
            if (paiementCommission.getMontant() != null) {
                existingPaiementCommission.setMontant(paiementCommission.getMontant());
            }

            if (paiementCommission.getServiceRevendeur() != null && paiementCommission.getServiceRevendeur().getId() != null) {
                Integer idServiceRevendeur = paiementCommission.getServiceRevendeur().getId();
                Optional<ServiceRevendeur> optionalServiceRevendeur = serviceRevendeurRepository.findById(idServiceRevendeur);
                existingPaiementCommission.setServiceRevendeur(optionalServiceRevendeur.orElseThrow(
                        () -> new EntityNotFoundException("Ce service revendeur n'est pas reconnu")
                ));
            }

            if (paiementCommission.getAdmin() != null && paiementCommission.getAdmin().getId() != null) {
                Integer idAdmin = paiementCommission.getAdmin().getId();
                Optional<Admin> optionalAdmin= utilisateurRepository.findById(idAdmin)
                        .filter(utilisateur -> utilisateur instanceof Admin)
                        .map(utilisateur -> (Admin) utilisateur);
                existingPaiementCommission.setAdmin((Admin) optionalAdmin.orElseThrow(
                        () -> new EntityNotFoundException("Error getting the Admin")
                ));
            }

            existingPaiementCommission.setDate(LocalDateTime.now());
        }

        try{
            this.paiementCommissionRepository.save(existingPaiementCommission);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating paiementCommission");
        }
        return this.paiementCommissionRepository.save(existingPaiementCommission);
    }

    public void deletePaiementCommission(int id) {
        if (!this.paiementCommissionRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune paiementCommission n'existe avec cet id");
        this.paiementCommissionRepository.deleteById(id);
    }
}
