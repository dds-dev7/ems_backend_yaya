package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.CaissierDto;
import net.dds.ems.dtoMapper.CaissierDtoMapper;
import net.dds.ems.entity.Caissier;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.UtilisateurRepository; // Using UtilisateurRepository
import net.dds.ems.repository.RoleRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
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
    public Caissier createCaissier(Caissier caissier) throws Exception {
        Role role = caissier.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.isPresent() || !optionalRole.get().getNom().equals("CAISSIER")) {
                throw new BadRequestException("Un caissier ne peut pas être créé avec ce rôle");
            }
            caissier.setRole(optionalRole.get());
        } else {
            throw new BadRequestException("Erreur lors de la récupération du rôle");
        }


        // Generate unique numeroIdentifiant for caissier
        Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(Caissier.class);
        if (maxNumeroIdentifiant == null) {
            maxNumeroIdentifiant = 10000; // Start from 10000 if no caissiers exist
        } else {
            maxNumeroIdentifiant += 1; // Increment the max value by 1
        }
        caissier.setNumeroIdentifiant(maxNumeroIdentifiant);

        // Set the creation date
        caissier.setDateCreation(LocalDateTime.now());

        // Set the MotDePasse
        caissier.setMotDePasse(encoder.encode(caissier.getMotDePasse()));
        try {
            this.utilisateurRepository.save(caissier);
        } catch (Exception ex) {
            throw new BadRequestException("Exception lors de la création du caissier, vérifiez votre syntaxe !");
        }
        return this.utilisateurRepository.save(caissier);
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
    public Stream<CaissierDto> showCaissierById(int id) {
        return utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Caissier)
                .map(utilisateur -> (Caissier) utilisateur)
                .stream()
                .map(caissierDtoMapper);
    }

    // Update an existing Caissier
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Caissier updateCaissier(int id, Caissier caissier) throws Exception {
        Caissier existingCaissier = this.search(id);

        if (caissier.getNom() != null) existingCaissier.setNom(caissier.getNom());
        if (caissier.getNumero() != null) existingCaissier.setNumero(caissier.getNumero());
        if (caissier.getStatut() != null) existingCaissier.setStatut(caissier.getStatut());
        if (caissier.getMotDePasse() != null) existingCaissier.setMotDePasse(caissier.getMotDePasse());
        if (caissier.getQuartier() != null) existingCaissier.setQuartier(caissier.getQuartier());
        if (caissier.getVille() != null) existingCaissier.setVille(caissier.getVille());
        if (caissier.getNumeroIdentifiant() != null) existingCaissier.setNumeroIdentifiant(caissier.getNumeroIdentifiant());
        if (caissier.getDateCreation() != null) existingCaissier.setDateCreation(caissier.getDateCreation());
        if (caissier.getRole() != null) existingCaissier.setRole(caissier.getRole());


        try {
            utilisateurRepository.save(existingCaissier);
        } catch (Exception ex) {
            throw new BadRequestException("Erreur lors de la mise à jour du caissier, vérifiez votre syntaxe !");
        }
        return this.utilisateurRepository.save(existingCaissier);
    }

    // Delete a Caissier by ID
    public void deleteCaissier(int id) {
        Caissier existingCaissier = this.search(id);
        utilisateurRepository.delete(existingCaissier);
    }
}
