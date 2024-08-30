package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.NumeroAutoriseDto;
import net.dds.ems.dto.NumeroAutoriseDto;
import net.dds.ems.dtoMapper.NumeroAutoriseDtoMapper;
import net.dds.ems.entity.NumeroAutorise;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.repository.NumeroAutoriseRepository;
import net.dds.ems.repository.RevendeurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    public NumeroAutorise createNumeroAutorise(NumeroAutorise numeroAutorise)  throws Exception{

        try{
            this.numeroAutoriseRepository.save(numeroAutorise);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.numeroAutoriseRepository.save(numeroAutorise);
    }

    public NumeroAutorise search(int id) {
        Optional<NumeroAutorise> optionalNumeroAutorise = this.numeroAutoriseRepository.findById(id);
        return optionalNumeroAutorise.orElseThrow(
                ()-> new EntityNotFoundException("Aucune NumeroAutorise n'existe avec cet id")
        );
    }

    public Stream<NumeroAutoriseDto> showNumeroAutorise() {
        if(this.numeroAutoriseRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No NumeroAutorise found")   ;
        }
        return this.numeroAutoriseRepository.findAll().stream().map(numeroAutoriseDtoMapper);
    }


    public Stream<NumeroAutoriseDto> showNumeroAutoriseById(int id) {
        if(this.numeroAutoriseRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("This NumeroAutorise cannot be found")   ;
        }
        return this.numeroAutoriseRepository.findById(id).stream().map(numeroAutoriseDtoMapper);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    public NumeroAutorise updateNumeroAutorise(int id, NumeroAutorise NumeroAutorise) throws Exception {
        NumeroAutorise existingNumeroAutorise= this.search(id);

        if(existingNumeroAutorise.getId() == id) {
            existingNumeroAutorise.setNumero(NumeroAutorise.getNumero());
        }else{
            throw new EntityNotFoundException("Exception updating the 'NumeroAutorise' check your syntax");
        }
        try{
            this.numeroAutoriseRepository.save(existingNumeroAutorise);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating NumeroAutorise");
        }
        return this.numeroAutoriseRepository.save(existingNumeroAutorise);
    }

    public void deleteNumeroAutorise(int id) {
        if (!this.numeroAutoriseRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune NumeroAutorise n'existe avec cet id");
        this.numeroAutoriseRepository.deleteById(id);
    }
}
