package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.EnrolleurDto;
import net.dds.ems.dtoMapper.EnrolleurDtoMapper;
import net.dds.ems.entity.Enrolleur;
import net.dds.ems.entity.Enrolleur;
import net.dds.ems.entity.Enrolleur;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.UtilisateurRepository; // Using UtilisateurRepository
import net.dds.ems.repository.RoleRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EnrolleurService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EnrolleurDtoMapper enrolleurDtoMapper;

    @Autowired
    private RoleRepository roleRepository;

    // Create an Enrolleur in the single table inheritance
    public EnrolleurDto createEnrolleur(EnrolleurDto enrolleurDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (enrolleurDto.nom() == null || enrolleurDto.nom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.numero() == null || enrolleurDto.numero().trim().isEmpty()) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.motDePasse() == null || enrolleurDto.motDePasse().trim().isEmpty()) {
            throw new BadRequestException("Le mot de passe est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.role() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.ville() == null) {
            throw new BadRequestException("Le ville est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.quartier() == null) {
            throw new BadRequestException("Le quartier est obligatoire et ne doit pas etre vide");
        }
        Role role = roleRepository.findById(enrolleurDto.role().id()).orElseThrow(() -> new EntityNotFoundException("Erreur lors de la récupération du rôle"));
        if (!role.getNom().equals("ENROLLEUR")) {
            throw new BadRequestException("Un enrolleur ne peut pas être créé avec ce rôle");
        }
        Enrolleur enrolleur = enrolleurDtoMapper.toEntity(enrolleurDto);
        enrolleur.setRole(role);

        // Generate unique numeroIdentifiant for enrolleur
        Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(Enrolleur.class);
        if (maxNumeroIdentifiant == null) {
            maxNumeroIdentifiant = 20000; // Start from 10000 if no enrolleurs exist
        } else {
            maxNumeroIdentifiant += 1; // Increment the max value by 1
        }
        enrolleur.setNumeroIdentifiant(maxNumeroIdentifiant);

        // Set the creation date

        // Set the MotDePasse
        enrolleur.setMotDePasse(encoder.encode(enrolleur.getMotDePasse()));

        try {
            Enrolleur savedEnrolleur = this.utilisateurRepository.save(enrolleur);
            return enrolleurDtoMapper.apply(savedEnrolleur);
        } catch (Exception ex) {
            throw new BadRequestException("Exception lors de la création de l'enrolleur, vérifiez votre syntaxe !");
        }
    }

    // Search for an Enrolleur by ID
    public Enrolleur search(int id) {
        Optional<Enrolleur> optionalEnrolleur = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Enrolleur)
                .map(utilisateur -> (Enrolleur) utilisateur);

        return optionalEnrolleur.orElseThrow(
                () -> new EntityNotFoundException("Aucun enrolleur n'existe avec cet id")
        );
    }

    // Show all Enrolleurs
    public Stream<EnrolleurDto> showEnrolleur() {
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof Enrolleur)
                .map(utilisateur -> (Enrolleur) utilisateur)
                .map(enrolleurDtoMapper);
    }

    // Show a specific Enrolleur by ID
    public EnrolleurDto showEnrolleurById(int id) {
        Enrolleur retEnrolleur=  utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Enrolleur)
                .map(utilisateur -> (Enrolleur) utilisateur).orElseThrow(() -> new EntityNotFoundException("Aucun Enrolleur ne correspond"));
        return enrolleurDtoMapper.apply(retEnrolleur);
    }

    // Update an existing Enrolleur
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EnrolleurDto updateEnrolleur( EnrolleurDto enrolleurDto) throws Exception {
// Vérification des champs obligatoires dans le DTO
        if (enrolleurDto.id() == null) {
            throw new BadRequestException("L'Id est obligatoire pour la modification");
        }
        if (enrolleurDto.nom() == null || enrolleurDto.nom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.numero() == null || enrolleurDto.numero().trim().isEmpty()) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.role() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.ville() == null) {
            throw new BadRequestException("Le ville est obligatoire et ne doit pas etre vide");
        }
        if (enrolleurDto.quartier() == null) {
            throw new BadRequestException("Le quartier est obligatoire et ne doit pas etre vide");
        }

        Enrolleur enrolleur = enrolleurDtoMapper.toEntity(enrolleurDto);
        if (enrolleurDto.motDePasse() != null) {
            String passwordCrypt = this.encoder.encode(enrolleurDto.motDePasse());
            enrolleur.setMotDePasse(passwordCrypt);
        } else {
            Optional<Enrolleur> existingEnrolleur = utilisateurRepository.findById(enrolleurDto.id())
                    .filter(utilisateur -> utilisateur instanceof Enrolleur)
                    .map(utilisateur -> (Enrolleur) utilisateur);
            existingEnrolleur.ifPresent(value -> enrolleur.setMotDePasse(value.getMotDePasse()));
        }

        Role role = enrolleur.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.isPresent() || !optionalRole.get().getNom().equals("ENROLLEUR")) {
                throw new BadRequestException("Un enrolleur ne peut pas être assigner à ce rôle");
            }
            enrolleur.setRole(optionalRole.get());
        } else {
            throw new BadRequestException("Erreur lors de la récupération du rôle");
        }
        try {
            Enrolleur savedEnrolleur = this.utilisateurRepository.save(enrolleur);
            return enrolleurDtoMapper.apply(savedEnrolleur);
        } catch (Exception ex) {
            throw new BadRequestException("Erreur lors de la mise à jour de l'enrolleur, vérifiez votre syntaxe !");
        }
    }

    // Delete an Enrolleur by ID
    public void deleteEnrolleur(int id) {
        Enrolleur existingEnrolleur = this.search(id);
        utilisateurRepository.delete(existingEnrolleur);
    }
}
