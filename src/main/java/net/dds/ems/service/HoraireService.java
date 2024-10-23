package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.HoraireDto;
import net.dds.ems.dto.HoraireDto;
import net.dds.ems.dto.RevendeurDto;
import net.dds.ems.dtoMapper.HoraireDtoMapper;
import net.dds.ems.entity.*;
import net.dds.ems.entity.Horaire;
import net.dds.ems.repository.HoraireRepository;
import net.dds.ems.repository.RevendeurRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class HoraireService {

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private RevendeurRepository revendeurRepository;

    @Autowired
    private HoraireDtoMapper horaireDtoMapper;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public HoraireDto createHoraire(HoraireDto horaireDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (horaireDto.heureDebut() == null) {
            throw new BadRequestException("Le champ heureDebut est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.heureFin() == null) {
            throw new BadRequestException("Le champ heureFin est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.jour() == null) {
            throw new BadRequestException("le champ jour est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.revendeur() == null) {
            throw new BadRequestException("le champ revendeur est obligatoire et ne doit pas etre vide");
        }
        // Check if the revendeur exists in the database
        Revendeur optionalRevendeur = revendeurRepository.findById(horaireDto.revendeur().id()).orElseThrow(()->new EntityNotFoundException("Revendeur pas trouver"));

        Horaire horaire = horaireDtoMapper.toEntity(horaireDto);
        horaire.setRevendeur(optionalRevendeur);

        // Ensure no duplicate entries for the same day
        List<Horaire> corRevendeurHoraireList = horaireRepository.findByRevendeurId(optionalRevendeur.getId());
        for (Horaire obj : corRevendeurHoraireList) {
            if (obj.getJour().equals(horaire.getJour())) {
                throw new Exception("Un horaire ne peut pas être enregistré deux fois pour un même jour");
            }
        }

        // Save the horaire
        try {
            Horaire savedHoraire = this.horaireRepository.save(horaire);
            return horaireDtoMapper.apply(savedHoraire);
        } catch (Exception ex) {
            throw new BadRequestException("Exception lors du processus de création, vérifiez votre syntaxe !");
        }
    }

    public Horaire search(int id) {
        Optional<Horaire> optionalHoraire = this.horaireRepository.findById(id);
        return optionalHoraire.orElseThrow(
                () -> new EntityNotFoundException("Aucune horaire n'existe avec cet id")
        );
    }

    public Stream<HoraireDto> showHoraire() {
        if (this.horaireRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No Horaire found");
        }
        return this.horaireRepository.findAll().stream().map(horaireDtoMapper);
    }

    public HoraireDto showHoraireById(int id) {
        Horaire retHoraire = this.horaireRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Aucun Compte ne correspond"));
        return horaireDtoMapper.apply(retHoraire);
    }

    //Trouver les horaires en Fonction des Revendeurs
    public Stream<HoraireDto> showHoraireByRevendeur(int revendeurId) {
        if (this.horaireRepository.findByRevendeurId(revendeurId).isEmpty()) {
            throw new EntityNotFoundException("This Horaire cannot be found");
        }
        return this.horaireRepository.findByRevendeurId(revendeurId).stream().map(horaireDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HoraireDto updateHoraire(HoraireDto horaireDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (horaireDto.id() == null) {
            throw new BadRequestException("L'Id est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.heureDebut() == null) {
            throw new BadRequestException("Le pays est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.heureFin() == null) {
            throw new BadRequestException("Le champ heureFin est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.jour() == null) {
            throw new BadRequestException("le champ jour est obligatoire et ne doit pas etre vide");
        }
        if (horaireDto.revendeur() == null) {
            throw new BadRequestException("le champ revendeur est obligatoire et ne doit pas etre vide");
        }
        // Fetch the Revendeur from Repository
        Revendeur revendeur = revendeurRepository.findById(horaireDto.revendeur().id()).orElseThrow(()->new EntityNotFoundException("Revendeur pas trouver"));

        Horaire horaire = horaireDtoMapper.toEntity(horaireDto);
        horaire.setRevendeur(revendeur);

        // Ensure no duplicate entries for the same day
        List<Horaire> corRevendeurHoraireList = horaireRepository.findByRevendeurId(revendeur.getId());
        for (Horaire obj : corRevendeurHoraireList) {
            if (obj.getJour().equals(horaire.getJour())) {
                throw new Exception("Un horaire ne peut pas être enregistré deux fois pour un même jour");
            }
        }

        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        horaire.setAuteur(admin);

        try {
            Horaire savedHoraire = this.horaireRepository.save(horaire);
            return horaireDtoMapper.apply(savedHoraire);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating horaire");
        }
    }

    public void deleteHoraire(int id) {
        if (!this.horaireRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune horaire n'existe avec cet id");
        this.horaireRepository.deleteById(id);
    }
}
