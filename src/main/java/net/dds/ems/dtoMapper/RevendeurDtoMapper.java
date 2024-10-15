package net.dds.ems.dtoMapper;

import net.dds.ems.dto.RevendeurDto;
import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.Revendeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RevendeurDtoMapper implements Function<Revendeur, RevendeurDto>{

    @Autowired
    private RecouvreurDtoMapper recouvreurDtoMapper;

    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @Override
    public RevendeurDto apply(Revendeur revendeur) {
        return new RevendeurDto(
                revendeur.getId(),
                revendeur.getNom(),
                revendeur.getNumero(),
                revendeur.getNumeroIdentifiant(),
                revendeur.getStatut(),
                roleDtoMapper.apply(revendeur.getRole()),
                revendeur.getQuartier(),
                revendeur.getVille(),
                revendeur.getNumeroDePiece(),
                revendeur.getSecteurActivite(),
                revendeur.getTypeDePiece(),
                revendeur.getTelephoneAirtel(),
                revendeur.getTelephoneMoov(),
                revendeur.getTelephoneFlash(),
                revendeur.getCrediterCaisse(),
                revendeur.getVendreDirectement(),
                revendeur.getDateNaissance(),
                recouvreurDtoMapper.apply(revendeur.getRecouvreur()),
                revendeur.getDateCreation());
    }
}
