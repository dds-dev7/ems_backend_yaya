package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.Recouvreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecouvreurDtoMapper implements Function<Recouvreur, RecouvreurDto>{
    @Autowired
    private RoleDtoMapper roleDtoMapper;
    @Override
    public RecouvreurDto apply(Recouvreur recouvreur) {
        return new RecouvreurDto(
                recouvreur.getId(),
                recouvreur.getNom(),
                recouvreur.getNumero(),
                recouvreur.getMotDePasse(),
                recouvreur.getNumeroIdentifiant(),
                recouvreur.getStatut(),
                roleDtoMapper.apply(recouvreur.getRole()),
                recouvreur.getQuartier(),
                recouvreur.getVille(),
                recouvreur.getDateCreation());
    }

    public Recouvreur toEntity(RecouvreurDto recouvreurDTO) {
        Recouvreur recouvreur = new Recouvreur();
        recouvreur.setId(recouvreurDTO.id());
        recouvreur.setNom(recouvreurDTO.nom());
        recouvreur.setNumero(recouvreurDTO.numero());
        recouvreur.setMotDePasse(recouvreurDTO.motDePasse());
        recouvreur.setNumeroIdentifiant(recouvreurDTO.numeroIdentifiant());
        recouvreur.setStatut(recouvreurDTO.statut());
        recouvreur.setRole(roleDtoMapper.toEntity(recouvreurDTO.role()));
        recouvreur.setQuartier(recouvreurDTO.quartier());
        recouvreur.setVille(recouvreurDTO.ville());
        recouvreur.setDateCreation(recouvreurDTO.dateCreation());
        return recouvreur;
    }
}
