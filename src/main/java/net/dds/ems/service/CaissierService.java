package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.CaissierDto;
import net.dds.ems.dtoMapper.CaissierDtoMapper;
import net.dds.ems.entity.Caissier;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.RoleRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CaissierService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UtilisateurRepository utilisateurRepository; // Using UtilisateurRepository

    @Autowired
    private CaissierDtoMapper caissierDtoMapper;

    @Autowired
    private RoleRepository roleRepository;

    // Create a Caissier in the single table inheritance
    public CaissierDto createCaissier(CaissierDto caissierDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (caissierDto.nom() == null || caissierDto.nom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.numero() == null || caissierDto.numero().trim().isEmpty()) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.motDePasse() == null || caissierDto.motDePasse().trim().isEmpty()) {
            throw new BadRequestException("Le mot de passe est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.role() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.ville() == null) {
            throw new BadRequestException("Le ville est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.quartier() == null) {
            throw new BadRequestException("Le quartier est obligatoire et ne doit pas etre vide");
        }
        Role role = roleRepository.findById(caissierDto.role().id()).orElseThrow(() -> new EntityNotFoundException("Erreur lors de la récupération du rôle"));
        if (!role.getNom().equals("CAISSIER")) {
            throw new BadRequestException("Un caissier ne peut pas être créé avec ce rôle");
        }
        Caissier caissier = caissierDtoMapper.toEntity(caissierDto);
        caissier.setRole(role);

        // Generate unique numeroIdentifiant for caissier
        Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(Caissier.class);
        if (maxNumeroIdentifiant == null) {
            maxNumeroIdentifiant = 10000; // Start from 10000 if no caissiers exist
        } else {
            maxNumeroIdentifiant += 1; // Increment the max value by 1
        }
        caissier.setNumeroIdentifiant(maxNumeroIdentifiant);

        // Set the MotDePasse
        caissier.setMotDePasse(encoder.encode(caissier.getMotDePasse()));
        try {
            Caissier savedCaissier = this.utilisateurRepository.save(caissier);
            return caissierDtoMapper.apply(savedCaissier);
        } catch (Exception ex) {
            throw new BadRequestException("Exception lors de la création du caissier, vérifiez votre syntaxe !");
        }
    }

    // Search for a Caissier by ID
    public Caissier search(int id) {
        Optional<Caissier> optionalCaissier = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Caissier)
                .map(utilisateur -> (Caissier) utilisateur);

        return optionalCaissier.orElseThrow(
                () -> new EntityNotFoundException("Aucun caissier n'existe avec cet id")
        );
    }

    // Show all Caissiers
    public Stream<CaissierDto> showCaissier() {
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof Caissier)
                .map(utilisateur -> (Caissier) utilisateur)
                .map(caissierDtoMapper);
    }

    // Show a specific Caissier by ID
    public CaissierDto showCaissierById(int id) {
        Caissier retCaissier = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Caissier)
                .map(utilisateur -> (Caissier) utilisateur).orElseThrow(() -> new EntityNotFoundException("Aucun Caissier ne correspond"));
        return caissierDtoMapper.apply(retCaissier);
    }

    // Update an existing Caissier
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CaissierDto updateCaissier(CaissierDto caissierDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (caissierDto.id() == 0) {
            throw new BadRequestException("L'Id est obligatoire pour la modification");
        }
        if (caissierDto.nom() == null || caissierDto.nom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.numero() == null || caissierDto.numero().trim().isEmpty()) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.role() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.ville() == null) {
            throw new BadRequestException("Le ville est obligatoire et ne doit pas etre vide");
        }
        if (caissierDto.quartier() == null) {
            throw new BadRequestException("Le quartier est obligatoire et ne doit pas etre vide");
        }

        Caissier caissier = caissierDtoMapper.toEntity(caissierDto);
        if (caissierDto.motDePasse() != null) {
            String passwordCrypt = this.encoder.encode(caissierDto.motDePasse());
            caissier.setMotDePasse(passwordCrypt);
        } else {
            Optional<Caissier> existingCaissier = utilisateurRepository.findById(caissierDto.id())
                    .filter(utilisateur -> utilisateur instanceof Caissier)
                    .map(utilisateur -> (Caissier) utilisateur);
            existingCaissier.ifPresent(value -> caissier.setMotDePasse(value.getMotDePasse()));
        }

        Role role = caissier.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.isPresent() || !optionalRole.get().getNom().equals("CAISSIER")) {
                throw new BadRequestException("Un caissier ne peut pas être assigner à ce rôle");
            }
            caissier.setRole(optionalRole.get());
        } else {
            throw new BadRequestException("Erreur lors de la récupération du rôle");
        }

        try {
            Caissier savedCaissier = this.utilisateurRepository.save(caissier);
            return caissierDtoMapper.apply(savedCaissier);
        } catch (Exception ex) {
            throw new BadRequestException("Erreur lors de la mise à jour du caissier, vérifiez votre syntaxe !");
        }
    }

    // Delete a Caissier by ID
    public void deleteCaissier(int id) {
        Caissier existingCaissier = this.search(id);
        utilisateurRepository.delete(existingCaissier);
    }
}
