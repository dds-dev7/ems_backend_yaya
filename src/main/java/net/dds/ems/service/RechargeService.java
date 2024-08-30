package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.RechargeDto;
import net.dds.ems.dtoMapper.RechargeDtoMapper;
import net.dds.ems.entity.*;
import net.dds.ems.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class RechargeService {

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private RechargeDtoMapper rechargeDtoMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ServiceRevendeurRepository serviceRevendeurRepository;

    @Autowired
    private HoraireRepository horaireRepository;


    public Recharge createRecharge(Recharge recharge) throws Exception {
        if (!recharge.getActeur().equals("revendeur") && !recharge.getActeur().equals("admin")) {
            throw new Exception("Verifier le champ acteur");
        }

        Service service = recharge.getService();
        Revendeur revendeur = recharge.getAssignerA();
        Boolean crediterCaisse;
        Admin admin = recharge.getAdmin();

        //Verifier si l'acteur est un revendeur et que l'horaire est bon
        if (recharge.getActeur().equals("revendeur")) {
            String today = LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            Horaire corHoraire = horaireRepository.findByRevendeurIdAndJour(revendeur.getId(), today);
            LocalTime currentTime = LocalTime.now();
            if (!(currentTime.isAfter(corHoraire.getDateDebut()) && currentTime.isBefore(corHoraire.getDateFin()))) {
                throw new Exception("Vous etes pas autoriser a vous recharger actuellement");
            }
        }
        //Prendre le jour actuelle et mapper les horaires du revendeur correspondant en fonction de ce jour
        //Si on est dans l'intervalle entre l'horaire de debut et de fin accepter la transaction sin retourner une erreur disant que les heures sont fermer

        if (service != null && service.getId() != 0) {
            Optional<Service> optionalService = serviceRepository.findById(service.getId());
            recharge.setService(optionalService.orElseThrow(() -> new EntityNotFoundException("This service doesn't exist")));
        } else {
            throw new BadRequestException("Error getting the service");
        }

        if (revendeur != null && revendeur.getId() != 0) {
            Optional<Revendeur> optionalRevendeur = utilisateurRepository.findById(revendeur.getId())
                    .filter(utilisateur -> utilisateur instanceof Revendeur)
                    .map(utilisateur -> (Revendeur) utilisateur);
            crediterCaisse = optionalRevendeur.get().getCrediterCaisse();
            recharge.setAssignerA((Revendeur) optionalRevendeur.orElseThrow(() -> new EntityNotFoundException("This recouveur doesn't exist")));
        } else {
            throw new BadRequestException("Error getting revendeur");
        }

        if (admin != null && service.getId() != 0) {
            Optional<Utilisateur> optionalAdmin = utilisateurRepository.findById(admin.getId())
                    .filter(utilisateur -> utilisateur instanceof Admin)
                    .map(utilisateur -> (Admin) utilisateur);
            recharge.setAdmin((Admin) optionalAdmin.orElseThrow(() -> new EntityNotFoundException("Aucun admin n'eiste avec cet id")));
        } else if (!recharge.getActeur().equals("admin")) {
            recharge.setAdmin(null);
        }

        //Trouver le service Revendeur du correspondant revendeur
        Optional<ServiceRevendeur> corServiceRevendeur = serviceRevendeurRepository.findFirstByRevendeur(revendeur);
        Double revendeurBalance = corServiceRevendeur.get().getMontantCaisse();

        // verifier si le revendeur est autorise a se recharger lui meme, si oui la case admin sera nulle si non
        if (recharge.getActeur().equals("revendeur") && !crediterCaisse) {
            throw new Exception("Vous n'etes pas autoriser a vous recharger actuellement");
        }
        // VERIFIER SI LE MONTANT DE LA RECHARGE EST superieur au montant autoriser dans service revendeur
        else if (recharge.getMontant() < corServiceRevendeur.get().getSoldeAutorise()) {
            throw new Exception("Vous n'etes pas autoriser a recharger un solde en dessous du seuil minimum autorised");
        }
        // ajouter le montant au montantCaisse du service du correspondant revendeur
        else if (recharge.getActeur().isEmpty()) {
            throw new IllegalArgumentException("renseignez le champ acteur");
        }
        corServiceRevendeur.get().setMontantCaisse(recharge.getMontant() + revendeurBalance);

        // verifier si le revendeur est autorise a se recharger lui meme, si oui la case admin sera nulle si non

        try {
            //Saving the creation date
            recharge.setDate(LocalDateTime.now());

            recharge.setStatut("effectuer");

            this.rechargeRepository.save(recharge);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.rechargeRepository.save(recharge);
    }


    public Recharge search(int id) {
        Optional<Recharge> optionalRecharge = this.rechargeRepository.findById(id);
        return optionalRecharge.orElseThrow(
                () -> new EntityNotFoundException("Aucune recharge n'existe avec cet id")
        );
    }

    public Stream<RechargeDto> showRecharge() {
        if (this.rechargeRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No Recharge found");
        }
        return this.rechargeRepository.findAll().stream().map(rechargeDtoMapper);
    }

    public Stream<RechargeDto> showRechargeById(int id) {
        if (this.rechargeRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("This Recharge cannot be found");
        }
        return this.rechargeRepository.findById(id).stream().map(rechargeDtoMapper);
    }


    public void deleteRecharge(int id) {
        if (!this.rechargeRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune recharge n'existe avec cet id");
        this.rechargeRepository.deleteById(id);
    }
}
