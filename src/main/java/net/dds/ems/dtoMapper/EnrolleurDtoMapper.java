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
                enrolleur.getNumeroIdentifiant(),
                enrolleur.getStatut(),
                roleDtoMapper.apply(enrolleur.getRole()),
                enrolleur.getQuartier(),
                enrolleur.getVille(),
                enrolleur.getDateCreation());
    }
}
