package net.dds.ems.dtoMapper;

import net.dds.ems.dto.SousCaissierDto;
import net.dds.ems.entity.SousCaissier;
import net.dds.ems.entity.SousCaissier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SousCaissierDtoMapper implements Function<SousCaissier, SousCaissierDto>{

    @Autowired
    private RoleDtoMapper roleDtoMapper;
    @Override
    public SousCaissierDto apply(SousCaissier sousCaissier) {
        return new SousCaissierDto(
                sousCaissier.getId(),
                sousCaissier.getNom(),
                sousCaissier.getNumero(),
                sousCaissier.getMotDePasse(),
                sousCaissier.getNumeroIdentifiant(),
                sousCaissier.getStatut(),
                roleDtoMapper.apply(sousCaissier.getRole()),
                sousCaissier.getQuartier(),
                sousCaissier.getVille(),
                sousCaissier.getDateCreation());
    }

    public SousCaissier toEntity(SousCaissierDto sousCaisseirDTO) {
        SousCaissier sousCaisseir = new SousCaissier();
        sousCaisseir.setId(sousCaisseirDTO.id());
        sousCaisseir.setNom(sousCaisseirDTO.nom());
        sousCaisseir.setNumero(sousCaisseirDTO.numero());
        sousCaisseir.setMotDePasse(sousCaisseirDTO.motDePasse());
        sousCaisseir.setNumeroIdentifiant(sousCaisseirDTO.numeroIdentifiant());
        sousCaisseir.setStatut(sousCaisseirDTO.statut());
        sousCaisseir.setRole(roleDtoMapper.toEntity(sousCaisseirDTO.role()));
        sousCaisseir.setQuartier(sousCaisseirDTO.quartier());
        sousCaisseir.setVille(sousCaisseirDTO.ville());
        sousCaisseir.setDateCreation(sousCaisseirDTO.dateCreation());
        return sousCaisseir;
    }
}
