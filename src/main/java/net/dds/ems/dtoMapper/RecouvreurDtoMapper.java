package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.entity.Recouvreur;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecouvreurDtoMapper implements Function<Recouvreur, RecouvreurDto>{

    @Override
    public RecouvreurDto apply(Recouvreur recouvreur) {
        return new RecouvreurDto(recouvreur.getId(), recouvreur.getNom(), recouvreur.getNumero(),recouvreur.getNumeroIdentifiant(),recouvreur.getStatut(),recouvreur.getRole().getNom(), recouvreur.getQuartier(), recouvreur.getVille(), recouvreur.getDateCreation());
    }
}
