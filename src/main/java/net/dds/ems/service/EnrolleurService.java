package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.EnrolleurDto;
import net.dds.ems.dtoMapper.EnrolleurDtoMapper;
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
    public Enrolleur createEnrolleur(Enrolleur enrolleur) throws Exception {
        Role role = enrolleur.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.isPresent() || !optionalRole.get().getNom().equals("ENROLLEUR")) {
                throw new BadRequestException("Un enrolleur ne peut pas être créé avec ce rôle");
            }
            enrolleur.setRole(optionalRole.get());
        } else {
            throw new BadRequestException("Erreur lors de la récupération du rôle");
        }

        // Generate unique numeroIdentifiant for enrolleur
        Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(Enrolleur.class);
        if (maxNumeroIdentifiant == null) {
            maxNumeroIdentifiant = 20000; // Start from 10000 if no enrolleurs exist
        } else {
            maxNumeroIdentifiant += 1; // Increment the max value by 1
        }
        enrolleur.setNumeroIdentifiant(maxNumeroIdentifiant);

        // Set the creation date
        enrolleur.setDateCreation(LocalDateTime.now());

        // Set the MotDePasse
        enrolleur.setMotDePasse(encoder.encode(enrolleur.getMotDePasse()));

        try {
            this.utilisateurRepository.save(enrolleur);
        } catch (Exception ex) {
            throw new BadRequestException("Exception lors de la création de l'enrolleur, vérifiez votre syntaxe !");
        }
        return this.utilisateurRepository.save(enrolleur);
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
    public Stream<EnrolleurDto> showEnrolleurById(int id) {
        return utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Enrolleur)
                .map(utilisateur -> (Enrolleur) utilisateur)
                .stream()
                .map(enrolleurDtoMapper);
    }

    // Update an existing Enrolleur
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Enrolleur updateEnrolleur(int id, Enrolleur enrolleur) throws Exception {
        Enrolleur existingEnrolleur = this.search(id);

        if (enrolleur.getNom() != null) existingEnrolleur.setNom(enrolleur.getNom());
        if (enrolleur.getNumero() != null) existingEnrolleur.setNumero(enrolleur.getNumero());
        if (enrolleur.getStatut() != null) existingEnrolleur.setStatut(enrolleur.getStatut());
        if (enrolleur.getMotDePasse() != null) existingEnrolleur.setMotDePasse(enrolleur.getMotDePasse());
        if (enrolleur.getQuartier() != null) existingEnrolleur.setQuartier(enrolleur.getQuartier());
        if (enrolleur.getVille() != null) existingEnrolleur.setVille(enrolleur.getVille());
        if (enrolleur.getNumeroIdentifiant() != null) existingEnrolleur.setNumeroIdentifiant(enrolleur.getNumeroIdentifiant());
        if (enrolleur.getDateCreation() != null) existingEnrolleur.setDateCreation(enrolleur.getDateCreation());
        if (enrolleur.getRole() != null) existingEnrolleur.setRole(enrolleur.getRole());

        try {
            this.utilisateurRepository.save(existingEnrolleur);
        } catch (Exception ex) {
            throw new BadRequestException("Erreur lors de la mise à jour de l'enrolleur, vérifiez votre syntaxe !");
        }
        return this.utilisateurRepository.save(existingEnrolleur);
    }

    // Delete an Enrolleur by ID
    public void deleteEnrolleur(int id) {
        Enrolleur existingEnrolleur = this.search(id);
        utilisateurRepository.delete(existingEnrolleur);
    }
}
