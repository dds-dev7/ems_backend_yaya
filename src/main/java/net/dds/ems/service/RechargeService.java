package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.RechargeDto;
import net.dds.ems.dtoMapper.RechargeDtoMapper;
import net.dds.ems.entity.*;
import net.dds.ems.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @Autowired
    private RevendeurRepository revendeurRepository;


    public RechargeDto createRecharge(RechargeDto rechargeDto) throws Exception {

        // Vérification des champs obligatoires dans le DTO
        if (rechargeDto.type() == null || rechargeDto.type().trim().isEmpty()) {
            throw new BadRequestException("Le type est obligatoire et ne doit pas etre vide");
        }
        if (rechargeDto.montant() == null) {
            throw new BadRequestException("Le montant est obligatoire et ne doit pas etre vide");
        }
        if (rechargeDto.date() == null) {
            throw new BadRequestException("Le date est obligatoire et ne doit pas etre vide");
        }
        if (rechargeDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (rechargeDto.service() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }
        if (rechargeDto.acteur() == null) {
            throw new BadRequestException("L'acteur est obligatoire et ne doit pas etre vide");
        }

        Recharge recharge = rechargeDtoMapper.toEntity(rechargeDto);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Admin admin) {
            recharge.setAdmin(admin);
        } else if (principal instanceof Revendeur revendeur) {
            recharge.setAssignerA(revendeur);
        } else {
            throw new EntityNotFoundException("Erreur de recuperation de l'utilisateur");
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
            if (!(currentTime.isAfter(corHoraire.getHeureDebut()) && currentTime.isBefore(corHoraire.getHeureFin()))) {
                throw new Exception("Vous etes pas autoriser a vous recharger actuellement");
            }
        }

        if (service != null && service.getId() != 0) {
            Optional<Service> optionalService = serviceRepository.findById(service.getId());
            recharge.setService(optionalService.orElseThrow(() -> new EntityNotFoundException("This service doesn't exist")));
        } else {
            throw new BadRequestException("Error getting the service");
        }

        if (revendeur != null && revendeur.getId() != 0 && revendeur.getCrediterCaisse()) {
            Revendeur optionalRevendeur = revendeurRepository.findById(revendeur.getId()).orElseThrow(()-> new EntityNotFoundException("Le revendeur avec cet id n'existe pas"));
            crediterCaisse = optionalRevendeur.getCrediterCaisse();
            recharge.setAssignerA(optionalRevendeur);
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
            Recharge savedRecharge = rechargeRepository.save(recharge);
            return rechargeDtoMapper.apply(savedRecharge);
        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
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

    public RechargeDto showRechargeById(int id) {

        return rechargeDtoMapper.apply(search(id));
    }

    public void deleteRecharge(int id) {
        if (!this.rechargeRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune recharge n'existe avec cet id");
        this.rechargeRepository.deleteById(id);
    }
}
