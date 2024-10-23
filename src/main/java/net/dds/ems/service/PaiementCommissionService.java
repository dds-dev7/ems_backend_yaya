package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.PaiementCommissionDto;
import net.dds.ems.dtoMapper.PaiementCommissionDtoMapper;
import net.dds.ems.entity.Admin;
import net.dds.ems.entity.PaiementCommission;
import net.dds.ems.entity.ServiceRevendeur;
import net.dds.ems.repository.AdminRepository;
import net.dds.ems.repository.PaiementCommissionRepository;
import net.dds.ems.repository.ServiceRevendeurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    private AdminRepository utilisateurRepository;


    public PaiementCommissionDto createPaiementCommission(PaiementCommissionDto paiementCommissionDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (paiementCommissionDto.statut() == null) {
            throw new BadRequestException("Le champ statut est obligatoire et ne doit pas etre vide");
        }
        if (paiementCommissionDto.montant() == null) {
            throw new BadRequestException("Le champ montant est obligatoire et ne doit pas etre vide");
        }
        if (paiementCommissionDto.date() == null) {
            throw new BadRequestException("le champ date est obligatoire et ne doit pas etre vide");
        }
        // Check if the revendeur exists in the database
        ServiceRevendeur serviceRevendeur = serviceRevendeurRepository.findById(paiementCommissionDto.serviceRevendeur().id()).orElseThrow(() -> new EntityNotFoundException("Revendeur pas trouver"));

        PaiementCommission paiementCommission = paiementCommissionDtoMapper.toEntity(paiementCommissionDto);
        paiementCommission.setServiceRevendeur(serviceRevendeur);

        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paiementCommission.setAdmin(admin);

        try {
            PaiementCommission savedPaiementCommission = this.paiementCommissionRepository.save(paiementCommission);
            return paiementCommissionDtoMapper.apply(savedPaiementCommission);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
    }

    public PaiementCommission search(int id) {
        Optional<PaiementCommission> optionalPaiementCommission = this.paiementCommissionRepository.findById(id);
        return optionalPaiementCommission.orElseThrow(
                () -> new EntityNotFoundException("Aucune paiementCommission n'existe avec cet id")
        );
    }

    public Stream<PaiementCommissionDto> showPaiementCommission() {
        if (this.paiementCommissionRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No PaiementCommission found");
        }
        return this.paiementCommissionRepository.findAll().stream().map(paiementCommissionDtoMapper);
    }

    public PaiementCommissionDto showPaiementCommissionById(int id) {
        return paiementCommissionDtoMapper.apply(search(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PaiementCommissionDto updatePaiementCommission(PaiementCommissionDto paiementCommissionDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (paiementCommissionDto.id() == null) {
            throw new BadRequestException("Le champ Id est obligatoire et ne doit pas etre vide");
        }
        if (paiementCommissionDto.statut() == null) {
            throw new BadRequestException("Le champ statut est obligatoire et ne doit pas etre vide");
        }
        if (paiementCommissionDto.montant() == null) {
            throw new BadRequestException("Le champ montant est obligatoire et ne doit pas etre vide");
        }
        if (paiementCommissionDto.date() == null) {
            throw new BadRequestException("le champ date est obligatoire et ne doit pas etre vide");
        }
        // Check if the revendeur exists in the database
        ServiceRevendeur serviceRevendeur = serviceRevendeurRepository.findById(paiementCommissionDto.serviceRevendeur().id()).orElseThrow(() -> new EntityNotFoundException("Revendeur pas trouver"));

        PaiementCommission paiementCommission = paiementCommissionDtoMapper.toEntity(paiementCommissionDto);
        paiementCommission.setServiceRevendeur(serviceRevendeur);

        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paiementCommission.setAdmin(admin);
        try {
            PaiementCommission savedPaiement = this.paiementCommissionRepository.save(paiementCommission);
            return paiementCommissionDtoMapper.apply(savedPaiement);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating paiementCommission");
        }
    }

    public void deletePaiementCommission(int id) {
        if (!this.paiementCommissionRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune paiementCommission n'existe avec cet id");
        this.paiementCommissionRepository.deleteById(id);
    }
}
