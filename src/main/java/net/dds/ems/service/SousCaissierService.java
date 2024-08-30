package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.SousCaissierDto;
import net.dds.ems.dtoMapper.SousCaissierDtoMapper;
import net.dds.ems.entity.Role;
import net.dds.ems.entity.SousCaissier;
import net.dds.ems.repository.RoleRepository;
import net.dds.ems.repository.UtilisateurRepository;
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
public class SousCaissierService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private SousCaissierDtoMapper sousCaissierDtoMapper;

    @Autowired
    private RoleRepository roleRepository;


    public SousCaissier createSousCaissier(SousCaissier sousCaissier) throws Exception {
        Role role = sousCaissier.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.get().getNom().equals("SOUSCAISSIER"))
                throw new BadRequestException("Un sous caissier ne peut pas etre creer avec cet role");
            sousCaissier.setRole(optionalRole.orElseThrow(() -> new EntityNotFoundException("This role doesn't exist")));
        } else {
            throw new BadRequestException("Error getting the role");
        }

        // Set the MotDePasse
        sousCaissier.setMotDePasse(encoder.encode(sousCaissier.getMotDePasse()));
        try {
            // Generating unique numeroIdentifiant for souscaissier
            Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(SousCaissier.class);
            if (maxNumeroIdentifiant == null) {
                maxNumeroIdentifiant = 50000; // Start from 1000 if no caissiers exist
            } else {
                maxNumeroIdentifiant += 1; // Increment the max value by 1
            }
            System.out.println(maxNumeroIdentifiant);

            sousCaissier.setNumeroIdentifiant(maxNumeroIdentifiant);

            //Saving the creation date
            sousCaissier.setDateCreation(LocalDateTime.now());
            this.utilisateurRepository.save(sousCaissier);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.utilisateurRepository.save(sousCaissier);
    }


    // Search for a SousCaissier by ID
    public SousCaissier search(int id) {
        Optional<SousCaissier> optionalSousCaissier = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof SousCaissier)
                .map(utilisateur -> (SousCaissier) utilisateur);

        return optionalSousCaissier.orElseThrow(
                () -> new EntityNotFoundException("Aucune sousCaissier n'existe avec cet id")
        );
    }

    // Show all SousCaissiers
    public Stream<SousCaissierDto> showSousCaissier() {
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof SousCaissier)
                .map(utilisateur -> (SousCaissier) utilisateur)
                .map(sousCaissierDtoMapper);
    }

    // Show a specific SousCaissier by ID
    public Stream<SousCaissierDto> showSousCaissierById(int id) {
        return utilisateurRepository.findById(id).stream()
                .filter(utilisateur -> utilisateur instanceof SousCaissier)
                .map(utilisateur -> (SousCaissier) utilisateur)
                .map(sousCaissierDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SousCaissier updateSousCaissier(int id, SousCaissier sousCaissier) throws Exception {
        SousCaissier existingSousCaissier = this.search(id);

        if (sousCaissier.getNom() != null) {
            existingSousCaissier.setNom(sousCaissier.getNom());
        }
        if (sousCaissier.getNumero() != null) {
            existingSousCaissier.setNumero(sousCaissier.getNumero());
        }
        if (sousCaissier.getStatut() != null) {
            existingSousCaissier.setStatut(sousCaissier.getStatut());
        }
        if (sousCaissier.getMotDePasse() != null) {
            existingSousCaissier.setMotDePasse(sousCaissier.getMotDePasse());
        }
        if (sousCaissier.getQuartier() != null) {
            existingSousCaissier.setQuartier(sousCaissier.getQuartier());
        }
        if (sousCaissier.getVille() != null) {
            existingSousCaissier.setVille(sousCaissier.getVille());
        }
        try {
            this.utilisateurRepository.save(existingSousCaissier);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating sousCaissier");
        }
        return this.utilisateurRepository.save(existingSousCaissier);
    }

    public void deleteSousCaissier(int id) {
        if (!this.utilisateurRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune sousCaissier n'existe avec cet id");
        this.utilisateurRepository.deleteById(id);
    }
}
