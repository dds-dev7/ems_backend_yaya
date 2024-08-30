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

    public CompteInternationale createCompteInternationale(CompteInternationale compteInternationale)  throws Exception{

        try{
            System.out.println(compteInternationale.getMnc());
            this.compteInternationaleRepository.save(compteInternationale);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
    return this.compteInternationaleRepository.save(compteInternationale);

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
    public Stream<CompteInternationaleDto> showCompteInternationaleById(int id) {
        if(this.compteInternationaleRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("This CompteInternationale cannot be found")   ;
        }
        return this.compteInternationaleRepository.findById(id).stream().map(compteInternationaleDtoMapper);
    }

    public CompteInternationale updateCompteInternationale(int id, CompteInternationale compteInternationale) throws Exception {
        CompteInternationale existingCompteInternationale = this.search(id);

        if(existingCompteInternationale.getId() == id) {
            existingCompteInternationale.setPays(compteInternationale.getPays());
            existingCompteInternationale.setMnc(compteInternationale.getMnc());
            existingCompteInternationale.setCommission(compteInternationale.getCommission());
        }else{
            throw new EntityNotFoundException("Exception updating the 'compteInternationale' check your syntax");
        }
        try{
            this.compteInternationaleRepository.save(existingCompteInternationale);
        }catch (Exception ex){
            throw new BadRequestException("bad syntax for updating compteInternationale");
        }
        return compteInternationaleRepository.save(existingCompteInternationale);
    }

    public void deleteCompteInternationale(int id) {
        if (!this.compteInternationaleRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune compteInternationale n'existe avec cet id");
        this.compteInternationaleRepository.deleteById(id);
    }
}
