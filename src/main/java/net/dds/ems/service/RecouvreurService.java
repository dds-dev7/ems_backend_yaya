package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.dtoMapper.RecouvreurDtoMapper;
import net.dds.ems.entity.Role;
import net.dds.ems.entity.Recouvreur;
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
public class RecouvreurService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RecouvreurDtoMapper recouvreurDtoMapper;

    @Autowired
    private RoleRepository roleRepository;


    public Recouvreur createRecouvreur(Recouvreur recouvreur)  throws Exception{
        Role role = recouvreur.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.get().getNom().equals("RECOUVREUR")) throw new BadRequestException("Un recouvreur ne peut pas etre creer avec cet role");
            recouvreur.setRole(optionalRole.orElseThrow(()-> new EntityNotFoundException("This role doesn't exist")));
        }else{
            throw new BadRequestException("Error getting the role");
        }

        // Set the MotDePasse
        recouvreur.setMotDePasse(encoder.encode(recouvreur.getMotDePasse()));
        try{
            //Generating special value for recouvreur
            Integer maxNumeroIdentifiant = utilisateurRepository.findMaxNumeroIdentifiantByType(Recouvreur.class);
            if(maxNumeroIdentifiant == null){
                maxNumeroIdentifiant = 30000;
            }else {
                maxNumeroIdentifiant +=1;
            }
            recouvreur.setNumeroIdentifiant(maxNumeroIdentifiant);

            //Saving the creation date
            recouvreur.setDateCreation(LocalDateTime.now());

            this.utilisateurRepository.save(recouvreur);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }

        return this.utilisateurRepository.save(recouvreur);

    }

    public Recouvreur search(int id) {
        Optional<Recouvreur> optionalRecouvreur = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Recouvreur)
                .map(utilisateur -> (Recouvreur) utilisateur);

        return optionalRecouvreur.orElseThrow(
                () -> new EntityNotFoundException("Aucun recouvreur n'existe avec cet id")
        );
    }

    // Show all Recouvreurs
    public Stream<RecouvreurDto> showRecouvreur() {
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof Recouvreur)
                .map(utilisateur -> (Recouvreur) utilisateur)
                .map(recouvreurDtoMapper);

    }

    // Show a specific Recouvreur by ID
    public Stream<RecouvreurDto> showRecouvreurById(int id) {
        return utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Recouvreur)
                .map(utilisateur -> (Recouvreur) utilisateur)
                .stream()
                .map(recouvreurDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Recouvreur updateRecouvreur(int id, Recouvreur recouvreur) throws Exception {
        Recouvreur existingRecouvreur= this.search(id);

        if (recouvreur.getNom() != null) {
            existingRecouvreur.setNom(recouvreur.getNom());
        }
        if (recouvreur.getNumero() != null) {
            existingRecouvreur.setNumero(recouvreur.getNumero());
        }
        if (recouvreur.getStatut() != null) {
            existingRecouvreur.setStatut(recouvreur.getStatut());
        }
        if (recouvreur.getMotDePasse() != null) {
            existingRecouvreur.setMotDePasse(recouvreur.getMotDePasse());
        } else {
            existingRecouvreur.setMotDePasse(existingRecouvreur.getMotDePasse());
        }
        if (recouvreur.getQuartier() != null) {
            existingRecouvreur.setQuartier(recouvreur.getQuartier());
        }
        if (recouvreur.getVille() != null) {
            existingRecouvreur.setVille(recouvreur.getVille());
        }

        try{
            this.utilisateurRepository.save(existingRecouvreur);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating recouvreur");
        }

        return this.utilisateurRepository.save(existingRecouvreur);
    }

    public void deleteRecouvreur(int id) {
        if (!this.utilisateurRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune recouvreur n'existe avec cet id");
        this.utilisateurRepository.deleteById(id);
    }
}
