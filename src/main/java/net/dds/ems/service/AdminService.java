package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.AdminDto;
import net.dds.ems.dtoMapper.AdminDtoMapper;
import net.dds.ems.entity.Admin;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.RoleRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AdminService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UtilisateurRepository utilisateurRepository; // Use the repository for Utilisateur

    @Autowired
    private AdminDtoMapper adminDtoMapper;

    @Autowired
    private RoleRepository roleRepository;

    // Create an admin in the single table inheritance
    public AdminDto createAdmin(AdminDto adminDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (adminDto.nom() == null || adminDto.nom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.numero() == null || adminDto.numero().trim().isEmpty()) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.motDePasse() == null || adminDto.motDePasse().trim().isEmpty()) {
            throw new BadRequestException("Le mot de passe est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.role() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }

        Role role = roleRepository.findById(adminDto.role().id()).orElseThrow(() -> new EntityNotFoundException("Erreur lors de la récupération du rôle"));
        if (!role.getNom().equals("ADMIN")) {
            throw new BadRequestException("Un admin ne peut pas être créé avec ce rôle");
        }
        Admin admin = adminDtoMapper.toEntity(adminDto);

        admin.setRole(role);

        // Set the MotDePasse
        admin.setMotDePasse(encoder.encode(admin.getMotDePasse()));
        try {
            Admin savedAdmin = this.utilisateurRepository.save(admin);
            return adminDtoMapper.apply(savedAdmin);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new BadRequestException(ex.getMessage());
        }
    }

    // Show all Admins
    public Stream<AdminDto> showAdmin() {
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof Admin)
                .map(utilisateur -> (Admin) utilisateur)
                .map(adminDtoMapper);
    }

    // Show a specific Admin by ID
    public AdminDto showAdminById(int id) {
        Admin retAdmin = utilisateurRepository.findById(id)
                .filter(utilisateur -> utilisateur instanceof Admin)
                .map(utilisateur -> (Admin) utilisateur).orElseThrow(() -> new EntityNotFoundException("Cet Admin n'existe pas"));

        return adminDtoMapper.apply(retAdmin);
    }

    // Update an existing Admin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AdminDto updateAdmin(AdminDto adminDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (adminDto.id() == 0 ) {
            throw new BadRequestException("L'Id est obligatoire pour la modification");
        }
        if (adminDto.nom() == null || adminDto.nom().trim().isEmpty()) {
            throw new BadRequestException("Le nom est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.numero() == null || adminDto.numero().trim().isEmpty()) {
            throw new BadRequestException("Le numero est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.statut() == null) {
            throw new BadRequestException("Le statut est obligatoire et ne doit pas etre vide");
        }
        if (adminDto.role() == null) {
            throw new BadRequestException("Le role est obligatoire et ne doit pas etre vide");
        }

        Admin admin = adminDtoMapper.toEntity(adminDto);
        if (adminDto.motDePasse() != null) {
            String passwordCrypt = this.encoder.encode(adminDto.motDePasse());
            admin.setMotDePasse(passwordCrypt);
        } else {
            Optional<Admin> existingAdmin = utilisateurRepository.findById(adminDto.id())
                    .filter(utilisateur -> utilisateur instanceof Admin)
                    .map(utilisateur -> (Admin) utilisateur);
            existingAdmin.ifPresent(value -> admin.setMotDePasse(value.getMotDePasse()));
        }

        Role role = admin.getRole();
        if (role != null && role.getId() != 0) {
            Optional<Role> optionalRole = roleRepository.findById(role.getId());
            if (!optionalRole.isPresent() || !optionalRole.get().getNom().equals("ADMIN")) {
                throw new BadRequestException("Un admin ne peut pas être assigner à ce rôle");
            }
            admin.setRole(optionalRole.get());
        } else {
            throw new BadRequestException("Erreur lors de la récupération du rôle");
        }

        try {
            Admin savedAdmin = this.utilisateurRepository.save(admin);
            return adminDtoMapper.apply(savedAdmin);
        } catch (Exception ex) {
            throw new BadRequestException("Erreur lors de la mise à jour de l'admin, vérifiez votre syntaxe !");
        }
    }

    // Delete an Admin by ID
    public void deleteAdmin(int id) {
        utilisateurRepository.deleteById(id);
    }
}
