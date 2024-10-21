package net.dds.ems.dtoMapper;

import net.dds.ems.dto.NumeroAutoriseDto;
import net.dds.ems.entity.NumeroAutorise;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NumeroAutoriseDtoMapper implements Function<NumeroAutorise, NumeroAutoriseDto>{

    @Override
    public NumeroAutoriseDto apply(NumeroAutorise numeroAutorise) {
        return new NumeroAutoriseDto(
                numeroAutorise.getId(),
                numeroAutorise.getNumero(),
                numeroAutorise.getAdmin().getNom()
                );
    }

    public NumeroAutorise toEntity(NumeroAutoriseDto numeroAutoriseDTO) {
        NumeroAutorise numeroAutorise = new NumeroAutorise();
        numeroAutorise.setId(numeroAutoriseDTO.id());
        numeroAutorise.setNumero(numeroAutoriseDTO.numero());
        return numeroAutorise;
    }
}
