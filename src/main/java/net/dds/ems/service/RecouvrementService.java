package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.RecouvrementDto;
import net.dds.ems.dtoMapper.RecouvrementDtoMapper;
import net.dds.ems.entity.*;
import net.dds.ems.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class RecouvrementService {

    @Autowired
    private RecouvrementRepository recouvrementRepository;

    @Autowired
    private RecouvrementDtoMapper recouvrementDtoMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private RevendeurRepository revendeurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RecouvreurRepository recouvreurRepository;

    @Autowired
    private ServiceRevendeurRepository serviceRevendeurRepository;


    public ServiceRevendeur findObjectWithService(List<ServiceRevendeur> objects, Service service) throws Exception {
        for (ServiceRevendeur obj : objects) {
            if (obj.getService().getId().equals(service.getId())) {
                return obj;
            }else{
                throw new Exception("Le service entrer est incorrect");
            }
        }
        return null;
    }

    public Recouvrement createRecouvrement(Recouvrement recouvrement)  throws Exception{

        Service service = recouvrement.getService();
        Recouvreur recouvreur = recouvrement.getAgentRecouvreur();
        Revendeur revendeur = recouvrement.getAgentRevendeur();
        List<ServiceRevendeur> serviceRevendeurList= serviceRevendeurRepository.findByRevendeur(revendeur);
        ServiceRevendeur corespondantServiceRevendeur = findObjectWithService(serviceRevendeurList,service);
        Double montantCaisse = corespondantServiceRevendeur.getMontantCaisse();
        Double montantARecouvrir = recouvrement.getMontant();

        if (service != null && service.getId() != 0) {
            Optional<Service> optionalService = serviceRepository.findById(service.getId());
            recouvrement.setService(optionalService.orElseThrow(()-> new EntityNotFoundException("This service doesn't exist")));
        }else{
            throw new BadRequestException("Error getting the service");
        }

        if (revendeur != null && revendeur.getId() != 0) {
            Optional<Revendeur> optionalRevendeur= utilisateurRepository.findById(revendeur.getId())
                    .filter(utilisateur -> utilisateur instanceof Revendeur)
                    .map(utilisateur -> (Revendeur) utilisateur);
            recouvrement.setAgentRevendeur(optionalRevendeur.orElseThrow(()-> new EntityNotFoundException("This recouveur doesn't exist")));
        }else{
            throw new BadRequestException("Error getting revendeur");
        }

        if (recouvreur != null && recouvreur.getId() != 0) {
            Optional<Recouvreur> optionalRecouvreur = recouvreurRepository.findById(recouvreur.getId());
            recouvrement.setAgentRecouvreur(optionalRecouvreur.orElseThrow(()-> new EntityNotFoundException("This recouvreur doesn't exist")));
        }else{
            throw new BadRequestException("Error getting recouvreur");
        }

        //Pour l'instant le revendeur ne vend pas sur l'app donc il lui est pris aue ce qui est disponible dans la caisse
        //Tester si le montant du correspondant revendeur dans le service revendeur pour le meme service est
        //superieur ou egal au montant de recouvrement pour pouvoir faire le recouvrement

        if(montantARecouvrir > montantCaisse){
            throw new Exception("Veuillez verifier le fond que vous voulez recouvrir");
        }

        corespondantServiceRevendeur.setMontantCaisse(montantCaisse - montantARecouvrir);




        try{
            //Saving the creation date
            recouvrement.setDate(LocalDateTime.now());

            this.recouvrementRepository.save(recouvrement);

        }catch (Exception ex){
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }

        return this.recouvrementRepository.save(recouvrement);
    }


    public Recouvrement search(int id) {
        Optional<Recouvrement> optionalRecouvrement = this.recouvrementRepository.findById(id);
        return optionalRecouvrement.orElseThrow(
                ()-> new EntityNotFoundException("Aucune recouvrement n'existe avec cet id")
        );
    }

    public Stream<RecouvrementDto> showRecouvrement() {
        if(this.recouvrementRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("No Recouvrement found")   ;
        }
        return this.recouvrementRepository.findAll().stream().map(recouvrementDtoMapper);
    }

    public Stream<RecouvrementDto> showRecouvrementById(int id) {
        if(this.recouvrementRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("This Recouvrement cannot be found")   ;
        }
        return this.recouvrementRepository.findById(id).stream().map(recouvrementDtoMapper);
    }
//
//    public void updateRecouvrement(int id, Recouvrement recouvrement) throws Exception {
//        Recouvrement existingRecouvrement= this.search(id);
//
//        if(existingRecouvrement.getId() == id) {
//            existingRecouvrement.setMontant(recouvrement.getMontant());
//            existingRecouvrement.setDate(LocalDateTime.now());
//            existingRecouvrement.setService(recouvrement.getService());
//            existingRecouvrement.setAgentRevendeur(recouvrement.getAgentRevendeur());
//            existingRecouvrement.setAgentRecouvreur(recouvrement.getAgentRecouvreur());
//        }else{
//            throw new EntityNotFoundException("Exception updating the 'recouvrement' check your syntax");
//        }
//        try{
//            this.recouvrementRepository.save(existingRecouvrement);
//        }catch (Exception ex){
//            throw new BadRequestException("bad syntax for updating recouvrement");
//        }
//    }

//    public void deleteRecouvrement(int id) {
//        if (!this.recouvrementRepository.findById(id).isPresent()) throw new EntityNotFoundException("Aucune recouvrement n'existe avec cet id");
//        this.recouvrementRepository.deleteById(id);
//    }
}
