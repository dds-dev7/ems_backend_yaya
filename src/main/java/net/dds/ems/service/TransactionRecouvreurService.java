package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.TransactionRecouvreurDto;
import net.dds.ems.dtoMapper.TransactionRecouvreurDtoMapper;
import net.dds.ems.entity.Caissier;
import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.TransactionRecouvreur;
import net.dds.ems.repository.TransactionRecouvreurRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class TransactionRecouvreurService {

    @Autowired
    private TransactionRecouvreurRepository transactionRecouvreurRepository;

    @Autowired
    private TransactionRecouvreurDtoMapper transactionRecouvreurDtoMapper;


    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public TransactionRecouvreur createTransactionRecouvreur(TransactionRecouvreur transactionRecouvreur) throws Exception {
        Caissier caissier = transactionRecouvreur.getAssignerA();
        if (caissier != null && caissier.getId() != 0) {
            Optional<Caissier> optionalCaissier = utilisateurRepository.findById(caissier.getId())
                    .filter(utilisateur -> utilisateur instanceof Caissier)
                    .map(utilisateur -> (Caissier) utilisateur);
            transactionRecouvreur.setAssignerA(optionalCaissier.orElseThrow(() -> new EntityNotFoundException("This caissier doesn't exist")));
        } else {
            throw new BadRequestException("Error getting the caissier");
        }

        Recouvreur recouvreur = transactionRecouvreur.getEffectuerPar();
        if (recouvreur != null && recouvreur.getId() != 0) {
            Optional<Recouvreur> optionalRecouvreur = utilisateurRepository.findById(caissier.getId())
                    .filter(utilisateur -> utilisateur instanceof Recouvreur)
                    .map(utilisateur -> (Recouvreur) utilisateur);
            transactionRecouvreur.setEffectuerPar(optionalRecouvreur.orElseThrow(() -> new EntityNotFoundException("This recouveur doesn't exist")));
        } else {
            throw new BadRequestException("Error getting recouvreur");
        }

        try {
            transactionRecouvreur.setDate(LocalDateTime.now());
            this.transactionRecouvreurRepository.save(transactionRecouvreur);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.transactionRecouvreurRepository.save(transactionRecouvreur);
    }

    public TransactionRecouvreur search(int id) {
        Optional<TransactionRecouvreur> optionalTransactionRecouvreur = this.transactionRecouvreurRepository.findById(id);
        return optionalTransactionRecouvreur.orElseThrow(
                () -> new EntityNotFoundException("Aucune transactionRecouvreur n'existe avec cet id")
        );
    }

    public Stream<TransactionRecouvreurDto> showTransactionRecouvreur() {
        if (this.transactionRecouvreurRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No TransactionRecouvreur found");
        }
        return this.transactionRecouvreurRepository.findAll().stream().map(transactionRecouvreurDtoMapper);
    }

    public Stream<TransactionRecouvreurDto> showTransactionRecouvreurById(int id) {
        if (this.transactionRecouvreurRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("This TransactionRecouvreur cannot be found");
        }
        return this.transactionRecouvreurRepository.findById(id).stream().map(transactionRecouvreurDtoMapper);
    }


//    public void updateTransactionRecouvreur(int id, TransactionRecouvreur transactionRecouvreur) throws Exception {
//        TransactionRecouvreur existingTransactionRecouvreur= this.search(id);
//
//        if(existingTransactionRecouvreur.getId() == id) {
//            existingTransactionRecouvreur.setType(transactionRecouvreur.getType());
//            existingTransactionRecouvreur.setMontant(transactionRecouvreur.getMontant());
//            existingTransactionRecouvreur.setEffectuerPar(transactionRecouvreur.getEffectuerPar());
//            existingTransactionRecouvreur.setAssignerA(transactionRecouvreur.getAssignerA());
//            existingTransactionRecouvreur.setDate(LocalDateTime.now());
//        }else{
//            throw new EntityNotFoundException("Exception updating the 'transactionRecouvreur' check your syntax");
//        }
//        try{
//            this.transactionRecouvreurRepository.save(existingTransactionRecouvreur);
//        }catch (Exception ex){
//            throw new BadRequestException("bad syntax for updating transactionRecouvreur");
//        }
//    }


    public void deleteTransactionRecouvreur(int id) {
        if (!this.transactionRecouvreurRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune transactionRecouvreur n'existe avec cet id");
        this.transactionRecouvreurRepository.deleteById(id);
    }

}
