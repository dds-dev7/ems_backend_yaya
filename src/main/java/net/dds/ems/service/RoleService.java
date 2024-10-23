package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.RoleDto;
import net.dds.ems.dtoMapper.RoleDtoMapper;
import net.dds.ems.entity.Role;
import net.dds.ems.repository.RoleRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Role createRole(Role role) throws Exception {
        try {
            this.roleRepository.save(role);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }

        return this.roleRepository.save(role);
    }


    public Role search(int id) {
        Optional<Role> optionalRole = this.roleRepository.findById(id);
        return optionalRole.orElseThrow(
                () -> new EntityNotFoundException("Aucune role n'existe avec cet id")
        );
    }

    public Stream<RoleDto> showRole() {
        if (this.roleRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No Role found");
        }
        return this.roleRepository.findAll().stream().map(roleDtoMapper);
    }


    public Stream<RoleDto> showRoleById(int id) {
        if (this.roleRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("This Role cannot be found");
        }
        return this.roleRepository.findById(id).stream().map(roleDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Role updateRole(int id, Role role) throws Exception {
        Role existingRole = this.search(id);

        if (role.getNom() != null) {
            existingRole.setNom(role.getNom());
        }
        if (role.getDroit() != null) {
            existingRole.setDroit(role.getDroit());
        }
        try {
            this.roleRepository.save(existingRole);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating role");
        }
        return this.roleRepository.save(existingRole);
    }


    public void deleteRole(int id) {
        if (!this.roleRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune role n'existe avec cet id");

        Role roleToDelete = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"));

        switch (roleToDelete.getNom()) {
            case "admin":
                utilisateurRepository.deleteByRoleAndType(roleToDelete, "Admin");
                break;
            case "caissier":
                utilisateurRepository.deleteByRoleAndType(roleToDelete, "Caissier");
                break;
            case "enrolleur":
                utilisateurRepository.deleteByRoleAndType(roleToDelete, "Enrolleur");
                break;
            case "recouvreur":
                utilisateurRepository.deleteByRoleAndType(roleToDelete, "Recouvreur");
                break;
            case "revendeur":
                utilisateurRepository.deleteByRoleAndType(roleToDelete, "Revendeur");
                break;
            case "sous-caissier":
                utilisateurRepository.deleteByRoleAndType(roleToDelete, "SousCaissier");
                break;
            default:
                throw new IllegalArgumentException("Type d'utilisateur non pris en charge pour la suppression");
        }

        this.roleRepository.delete(roleToDelete);
    }

}
