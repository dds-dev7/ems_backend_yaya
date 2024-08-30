package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.TransfertInternationalDto;
import net.dds.ems.dtoMapper.TransfertInternationalDtoMapper;
import net.dds.ems.entity.*;
import net.dds.ems.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TransfertInternationalService {

    @Autowired
    private TransfertInternationalRepository transfertInternationalRepository;

    @Autowired
    private TransfertInternationalDtoMapper transfertInternationalDtoMapper;

    @Autowired
    private RevendeurRepository revendeurRepository;

    @Autowired
    private CompteInternationaleRepository compteInternationaleRepository;


    public TransfertInternational createTransfertInternational(TransfertInternational transfertInternational)  throws Exception{

        Revendeur revendeur = transfertInternational.getExpediteur();
        if (revendeur != null && revendeur.getId() != 0) {
            Optional<Revendeur> optionalRevendeur = revendeurRepository.findById(revendeur.getId());
            transfertInternational.setExpediteur(optionalRevendeur.orElseThrow(()-> new EntityNotFoundException("This recouveur doesn't exist")));
        }else{
            throw new BadRequestException("Error getting revendeur");
        }

        CompteInternationale compte = transfertInternational.getCompte();
        if (compte != null && compte.getId() != 0) {
            Optional<CompteInternationale> optionalCompte = compteInternationaleRepository.findById(compte.getId());
            transfertInternational.setCompte(optionalCompte.orElseThrow(()-> new EntityNotFoundException("This compte internationale doesn't exist")));
        }else{
            throw new BadRequestException("Error getting compte");
        }

        try{
            //Saving the creation date
            transfertInternational.setDate(LocalDateTime.now());

            this.transfertInternationalRepository.save(transfertInternational);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
return  transfertInternationalRepository.save(transfertInternational);
    }


    public TransfertInternational search(int id) {
        Optional<TransfertInternational> optionalTransfertInternational = this.transfertInternationalRepository.findById(id);
        return optionalTransfertInternational.orElseThrow(
                ()-> new EntityNotFoundException("Aucune transfertInternational n'existe avec cet id")
        );
    }

    public Stream<TransfertInternationalDto> showTransfertInternational() {
        if(this.transfertInternationalRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No TransfertInternational found")   ;
        }
        return this.transfertInternationalRepository.findAll().stream().map(transfertInternationalDtoMapper);
    }
    public Stream<TransfertInternationalDto> showTransfertInternationalById(int id) {
        if(this.transfertInternationalRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("This TransfertInternational cannot be found")   ;
        }
        return this.transfertInternationalRepository.findById(id).stream().map(transfertInternationalDtoMapper);
    }


    public void deleteTransfertInternational(int id) {
        if (!this.transfertInternationalRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune transfertInternational n'existe avec cet id");
        this.transfertInternationalRepository.deleteById(id);
    }
}
