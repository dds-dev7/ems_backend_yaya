package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.HoraireDto;
import net.dds.ems.dtoMapper.HoraireDtoMapper;
import net.dds.ems.entity.Horaire;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Horaire;
import net.dds.ems.entity.Utilisateur;
import net.dds.ems.repository.HoraireRepository;
import net.dds.ems.repository.RevendeurRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class HoraireService {

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private HoraireDtoMapper horaireDtoMapper;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public Horaire createHoraire(Horaire horaire) throws Exception {
        // Fetch the Revendeur from UtilisateurRepository
        Revendeur revendeur = horaire.getRevendeur();
        if (revendeur == null || revendeur.getId() == 0) {
            throw new BadRequestException("Erreur lors de la récupération du revendeur");
        }

        // Check if the revendeur exists in the database
        Optional<Revendeur> optionalRevendeur = utilisateurRepository.findById(revendeur.getId())
                .filter(utilisateur -> utilisateur instanceof Revendeur)
                .map(utilisateur -> (Revendeur) utilisateur);

        horaire.setRevendeur(optionalRevendeur.orElseThrow(() -> new EntityNotFoundException("Ce revendeur n'existe pas")));

        // Ensure no duplicate entries for the same day
        List<Horaire> corRevendeurHoraireList = horaireRepository.findByRevendeurId(revendeur.getId());
        for (Horaire obj : corRevendeurHoraireList) {
            if (obj.getJour().equals(horaire.getJour())) {
                throw new Exception("Un horaire ne peut pas être enregistré deux fois pour un même jour");
            }
        }

        // Save the horaire
        try {
            this.horaireRepository.save(horaire);
        } catch (Exception ex) {
            throw new BadRequestException("Exception lors du processus de création, vérifiez votre syntaxe !");
        }

        return this.horaireRepository.save(horaire);
    }


    public Horaire search(int id) {
        Optional<Horaire> optionalHoraire = this.horaireRepository.findById(id);
        return optionalHoraire.orElseThrow(
                ()-> new EntityNotFoundException("Aucune horaire n'existe avec cet id")
        );
    }

    public Stream<HoraireDto> showHoraire() {
        if(this.horaireRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No Horaire found")   ;
        }
        return this.horaireRepository.findAll().stream().map(horaireDtoMapper);
    }

    //Trouver les horaires en Fonction des Revendeurs
    public Stream<HoraireDto> showHoraireByRevendeur(int revendeurId) {
        if(this.horaireRepository.findByRevendeurId(revendeurId).isEmpty()){
            throw new EntityNotFoundException("This Horaire cannot be found")   ;
        }
        return this.horaireRepository.findByRevendeurId(revendeurId).stream().map(horaireDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Horaire updateHoraire(int id, Horaire horaire) throws Exception {
        Horaire existingHoraire= this.search(id);

        if (horaire.getDateDebut() != null) existingHoraire.setDateDebut(horaire.getDateDebut());
        if (horaire.getDateFin() != null) existingHoraire.setDateFin(horaire.getDateFin());
        if (horaire.getJour() != null) existingHoraire.setJour(horaire.getJour());
        if (horaire.getRevendeur() != null)horaire.setRevendeur(horaire.getRevendeur());

        try{
            this.horaireRepository.save(existingHoraire);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating horaire");
        }

        return this.horaireRepository.save(existingHoraire);
    }

    public void deleteHoraire(int id) {
        if (!this.horaireRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune horaire n'existe avec cet id");
        this.horaireRepository.deleteById(id);
    }
}
