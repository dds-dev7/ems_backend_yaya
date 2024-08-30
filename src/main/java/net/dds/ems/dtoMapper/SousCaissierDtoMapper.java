package net.dds.ems.dtoMapper;

import net.dds.ems.dto.SousCaissierDto;
import net.dds.ems.entity.SousCaissier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SousCaissierDtoMapper implements Function<SousCaissier, SousCaissierDto>{

    @Override
    public SousCaissierDto apply(SousCaissier sousCaissier) {
        return new SousCaissierDto(sousCaissier.getId(), sousCaissier.getNom(), sousCaissier.getNumero(), sousCaissier.getNumeroIdentifiant(), sousCaissier.getStatut(),sousCaissier.getRole().getNom(), sousCaissier.getQuartier(), sousCaissier.getVille(), sousCaissier.getDateCreation());
    }
}
