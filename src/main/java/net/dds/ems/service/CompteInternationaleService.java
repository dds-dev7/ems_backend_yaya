package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.CompteInternationaleDto;
import net.dds.ems.dtoMapper.CompteInternationaleDtoMapper;
import net.dds.ems.entity.CompteInternationale;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.CompteInternationaleRepository;
import net.dds.ems.repository.RoleRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CompteInternationaleService {

    @Autowired
    private CompteInternationaleRepository compteInternationaleRepository;

    @Autowired
    private CompteInternationaleDtoMapper compteInternationaleDtoMapper;

    public CompteInternationaleDto createCompteInternationale(CompteInternationaleDto compteInternationaleDto)  throws Exception{
        // Vérification des champs obligatoires dans le DTO
        if (compteInternationaleDto.pays() == null || compteInternationaleDto.pays().trim().isEmpty()) {
            throw new BadRequestException("Le pays est obligatoire et ne doit pas etre vide");
        }
        if (compteInternationaleDto.mNC() == null || compteInternationaleDto.mNC().trim().isEmpty()) {
            throw new BadRequestException("Le MNC est obligatoire et ne doit pas etre vide");
        }
        if (compteInternationaleDto.commission() == null) {
            throw new BadRequestException("a commission est obligatoire et ne doit pas etre vide");
        }
        CompteInternationale compteInternationale = compteInternationaleDtoMapper.toEntity(compteInternationaleDto);
        try{
            CompteInternationale savedCompteInternationale = this.compteInternationaleRepository.save(compteInternationale);
            return compteInternationaleDtoMapper.apply(savedCompteInternationale);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
    }

    public CompteInternationale search(int id) {
        Optional<CompteInternationale> optionalCompteInternationale = this.compteInternationaleRepository.findById(id);
        return optionalCompteInternationale.orElseThrow(
                ()-> new EntityNotFoundException("Aucune compteInternationale n'existe avec cet id")
        );
    }

    public Stream<CompteInternationaleDto> showCompteInternationale() {
        if(this.compteInternationaleRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No CompteInternationale found")   ;
        }
        return this.compteInternationaleRepository.findAll().stream().map(compteInternationaleDtoMapper);
    }

    public CompteInternationaleDto showCompteInternationaleById(int id) {
        CompteInternationale retCompteInternation = this.compteInternationaleRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Aucun Compte ne correspond"));
        return compteInternationaleDtoMapper.apply(retCompteInternation);
    }

    public CompteInternationaleDto updateCompteInternationale(CompteInternationaleDto compteInternationaleDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (compteInternationaleDto.id() == null) {
            throw new BadRequestException("Le champ id est obligatoire pour la modification");
        }
        if (compteInternationaleDto.pays() == null || compteInternationaleDto.pays().trim().isEmpty()) {
            throw new BadRequestException("Le pays est obligatoire et ne doit pas etre vide");
        }
        if (compteInternationaleDto.mNC() == null || compteInternationaleDto.mNC().trim().isEmpty()) {
            throw new BadRequestException("Le MNC est obligatoire et ne doit pas etre vide");
        }
        if (compteInternationaleDto.commission() == null) {
            throw new BadRequestException("a commission est obligatoire et ne doit pas etre vide");
        }
        CompteInternationale compteInternationale = compteInternationaleDtoMapper.toEntity(compteInternationaleDto);
        try{
            CompteInternationale savedCompteInternationale = this.compteInternationaleRepository.save(compteInternationale);
            return compteInternationaleDtoMapper.apply(savedCompteInternationale);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating compteInternationale");
        }
    }

    public void deleteCompteInternationale(int id) {
        if (!this.compteInternationaleRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune compteInternationale n'existe avec cet id");
        this.compteInternationaleRepository.deleteById(id);
    }
}
