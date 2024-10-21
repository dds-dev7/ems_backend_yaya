package net.dds.ems.dtoMapper;

import net.dds.ems.dto.CaissierDto;
import net.dds.ems.entity.Caissier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CaissierDtoMapper implements Function<Caissier, CaissierDto>{
    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @Override
    public CaissierDto apply(Caissier caissier) {
        return new CaissierDto(
                caissier.getId(),
                caissier.getNom(),
                caissier.getNumero(),
                caissier.getMotDePasse(),
                caissier.getNumeroIdentifiant(),
                caissier.getStatut(),
                roleDtoMapper.apply(caissier.getRole()),
                caissier.getQuartier(),
                caissier.getVille(),
                caissier.getDateCreation());
    }

    public Caissier toEntity(CaissierDto caissierDTO) {
        Caissier caissier = new Caissier();
        caissier.setId(caissierDTO.id());
        caissier.setNom(caissierDTO.nom());
        caissier.setNumero(caissierDTO.numero());
        caissier.setMotDePasse(caissierDTO.motDePasse());
        caissier.setNumeroIdentifiant(caissierDTO.numeroIdentifiant());
        caissier.setStatut(caissierDTO.statut());
        caissier.setRole(roleDtoMapper.toEntity(caissierDTO.role()));
        caissier.setQuartier(caissierDTO.quartier());
        caissier.setVille(caissierDTO.ville());
        caissier.setDateCreation(caissierDTO.dateCreation());
        return caissier;
    }
}
