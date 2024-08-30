package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.RevendeurDto;
import net.dds.ems.dtoMapper.RevendeurDtoMapper;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.RecouvreurRepository;
import net.dds.ems.repository.RevendeurRepository;
import net.dds.ems.repository.RoleRepository;
import net.dds.ems.repository.UtilisateurRepository;
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
public class RevendeurService {


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RecouvreurRepository recouvreurRepository;



    public Revendeur createRevendeur(Revendeur revendeur)  throws Exception{
        Role role = revendeur.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.get().getNom().equals("REVENDEUR")) throw new BadRequestException("Un revendeur ne peut pas etre creer avec cet role");
            revendeur.setRole(optionalRole.orElseThrow(()-> new EntityNotFoundException("This role doesn't exist")));
        }else{
            throw new BadRequestException("Error getting the role");
        }

        Recouvreur recouvreur = revendeur.getRecouvreur();
        if (recouvreur != null && recouvreur.getId() != 0) {
            Optional<Recouvreur> optionalRecouvreur = recouvreurRepository.findById(recouvreur.getId());
            revendeur.setRecouvreur(optionalRecouvreur.orElseThrow(()-> new EntityNotFoundException("This recouveur doesn't exist")));
        }else{
            throw new BadRequestException("Error getting recouvreur");
        }

        // Set the MotDePasse
        revendeur.setMotDePasse(encoder.encode(revendeur.getMotDePasse()));
        try{
            //Generating special value for revendeur
            Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(Revendeur.class);
            if(maxNumeroIdentifiant == null){
                maxNumeroIdentifiant = 40000;
            }else {
                maxNumeroIdentifiant +=1;
            }
            revendeur.setNumeroIdentifiant(maxNumeroIdentifiant);

            //Saving the creation date
            revendeur.setDateCreation(LocalDateTime.now());


            this.utilisateurRepository.save(revendeur);
        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }

        return utilisateurRepository.save(revendeur);

    }

    // Search for a Revendeur by ID
    public Revendeur search(int id) {
        Optional<Revendeur> optionalRevendeur = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Revendeur)
                .map(utilisateur -> (Revendeur) utilisateur);

        return optionalRevendeur.orElseThrow(
                () -> new EntityNotFoundException("Aucun revendeur n'existe avec cet id")
        );
    }

    // Show all Revendeurs
    public Stream<RevendeurDto> showRevendeur() {
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof Revendeur)
                .map(utilisateur -> (Revendeur) utilisateur)
                .map(revendeurDtoMapper);
    }

    // Show a specific Revendeur by ID
    public Stream<RevendeurDto> showRevendeurById(int id) {
        return utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Revendeur)
                .map(utilisateur -> (Revendeur) utilisateur)
                .stream()
                .map(revendeurDtoMapper);
    }


    public Revendeur updateRevendeur(int id, Revendeur revendeur) throws IllegalArgumentException {

        Revendeur existingRevendeur = this.search(id);
        if (revendeur.getNom() != null) {
            existingRevendeur.setNom(revendeur.getNom());
        }
        if (revendeur.getNumero() != null) {
            existingRevendeur.setNumero(revendeur.getNumero());
        }
        if (revendeur.getStatut() != null) {
            existingRevendeur.setStatut(revendeur.getStatut());
        }
        if (revendeur.getMotDePasse() != null) {
            existingRevendeur.setMotDePasse(revendeur.getMotDePasse());
        }
        if (revendeur.getQuartier() != null) {
            existingRevendeur.setQuartier(revendeur.getQuartier());
        }
        if (revendeur.getVille() != null) {
            existingRevendeur.setVille(revendeur.getVille());
        }
        if (revendeur.getNumeroDePiece() != null) {
            existingRevendeur.setNumeroDePiece(revendeur.getNumeroDePiece());
        }
        if (revendeur.getSecteurActivite() != null) {
            existingRevendeur.setSecteurActivite(revendeur.getSecteurActivite());
        }
        if (revendeur.getTypeDePiece() != null) {
            existingRevendeur.setTypeDePiece(revendeur.getTypeDePiece());
        }
        if (revendeur.getTelephoneAirtel() != null) {
            existingRevendeur.setTelephoneAirtel(revendeur.getTelephoneAirtel());
        }
        if (revendeur.getTelephoneMoov() != null) {
            existingRevendeur.setTelephoneMoov(revendeur.getTelephoneMoov());
        }
        if (revendeur.getTelephoneFlash() != null) {
            existingRevendeur.setTelephoneFlash(revendeur.getTelephoneFlash());
        }
        if (revendeur.getCrediterCaisse() != null) {
            existingRevendeur.setCrediterCaisse(revendeur.getCrediterCaisse());
        }
        if (revendeur.getVendreDirectement() != null) {
            existingRevendeur.setVendreDirectement(revendeur.getVendreDirectement());
        }
        if (revendeur.getDateNaissance() != null) {
            existingRevendeur.setDateNaissance(revendeur.getDateNaissance());
        }

        if (revendeur.getRecouvreur() != null && revendeur.getRecouvreur().getId() != null) {
            Integer idRecouvreur = revendeur.getRecouvreur().getId();
            Optional<Recouvreur> optionalRecouvreur= utilisateurRepository.findById(idRecouvreur)
                    .filter(utilisateur -> utilisateur instanceof Recouvreur)
                    .map(utilisateur -> (Recouvreur) utilisateur);
            existingRevendeur.setRecouvreur(optionalRecouvreur.orElseThrow(() -> new EntityNotFoundException("Ce recouvreur n'est pas reconnu")));
        }

        try{
            this.utilisateurRepository.save(existingRevendeur);
        }catch (IllegalArgumentException ex){

            throw new IllegalArgumentException(ex.getMessage());
        }
        return this.utilisateurRepository.save(existingRevendeur);
    }

    public void deleteRevendeur(int id) {
        if (!this.utilisateurRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucun revendeur n'existe avec cet id");
        this.utilisateurRepository.deleteById(id);
    }
}
