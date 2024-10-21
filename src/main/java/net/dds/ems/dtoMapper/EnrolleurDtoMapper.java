package net.dds.ems.dtoMapper;

import net.dds.ems.dto.EnrolleurDto;
import net.dds.ems.entity.Enrolleur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EnrolleurDtoMapper implements Function<Enrolleur, EnrolleurDto>{

    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @Override
    public EnrolleurDto apply(Enrolleur enrolleur) {
        return new EnrolleurDto(
                enrolleur.getId(),
                enrolleur.getNom(),
                enrolleur.getNumero(),
                enrolleur.getMotDePasse(),
                enrolleur.getNumeroIdentifiant(),
                enrolleur.getStatut(),
                roleDtoMapper.apply(enrolleur.getRole()),
                enrolleur.getQuartier(),
                enrolleur.getVille(),
                enrolleur.getDateCreation());
    }

    public Enrolleur toEntity(EnrolleurDto enrolleurDTO) {
        Enrolleur enrolleur = new Enrolleur();
        enrolleur.setId(enrolleurDTO.id());
        enrolleur.setNom(enrolleurDTO.nom());
        enrolleur.setNumero(enrolleurDTO.numero());
        enrolleur.setMotDePasse(enrolleurDTO.motDePasse());
        enrolleur.setNumeroIdentifiant(enrolleurDTO.numeroIdentifiant());
        enrolleur.setStatut(enrolleurDTO.statut());
        enrolleur.setRole(roleDtoMapper.toEntity(enrolleurDTO.role()));
        enrolleur.setQuartier(enrolleurDTO.quartier());
        enrolleur.setVille(enrolleurDTO.ville());
        enrolleur.setDateCreation(enrolleurDTO.dateCreation());
        return enrolleur;
    }
}
