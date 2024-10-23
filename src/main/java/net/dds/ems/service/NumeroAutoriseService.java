package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.NumeroAutoriseDto;
import net.dds.ems.dtoMapper.NumeroAutoriseDtoMapper;
import net.dds.ems.entity.Admin;
import net.dds.ems.entity.NumeroAutorise;
import net.dds.ems.repository.NumeroAutoriseRepository;
import net.dds.ems.repository.RevendeurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NumeroAutoriseService {

    @Autowired
    private NumeroAutoriseRepository numeroAutoriseRepository;

    @Autowired
    private NumeroAutoriseDtoMapper numeroAutoriseDtoMapper;

    @Autowired
    private RevendeurRepository revendeurRepository;


    public NumeroAutoriseDto createNumeroAutorise(NumeroAutoriseDto numeroAutoriseDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (numeroAutoriseDto.numero() == null) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }

        NumeroAutorise numeroAutorise = numeroAutoriseDtoMapper.toEntity(numeroAutoriseDto);
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        numeroAutorise.setAdmin(admin);
        try {
            NumeroAutorise savedNumeroAutorise = this.numeroAutoriseRepository.save(numeroAutorise);
            return numeroAutoriseDtoMapper.apply(savedNumeroAutorise);
        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
    }

    public NumeroAutorise search(int id) {
        Optional<NumeroAutorise> optionalNumeroAutorise = this.numeroAutoriseRepository.findById(id);
        return optionalNumeroAutorise.orElseThrow(
                () -> new EntityNotFoundException("Aucune NumeroAutorise n'existe avec cet id")
        );
    }

    public Stream<NumeroAutoriseDto> showNumeroAutorise() {
        if (this.numeroAutoriseRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No NumeroAutorise found");
        }
        return this.numeroAutoriseRepository.findAll().stream().map(numeroAutoriseDtoMapper);
    }


    public NumeroAutoriseDto showNumeroAutoriseById(int id) {
        return numeroAutoriseDtoMapper.apply(search(id));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    public NumeroAutoriseDto updateNumeroAutorise(NumeroAutoriseDto numeroAutoriseDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (numeroAutoriseDto.numero() == null) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }

        NumeroAutorise numeroAutorise = numeroAutoriseDtoMapper.toEntity(numeroAutoriseDto);
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        numeroAutorise.setAdmin(admin);

        try {
            NumeroAutorise savedNumeroAutorise = this.numeroAutoriseRepository.save(numeroAutorise);
            return numeroAutoriseDtoMapper.apply(savedNumeroAutorise);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating NumeroAutorise");
        }
    }

    public void deleteNumeroAutorise(int id) {
        if (!this.numeroAutoriseRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune NumeroAutorise n'existe avec cet id");
        this.numeroAutoriseRepository.deleteById(id);
    }
}
