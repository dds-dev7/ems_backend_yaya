package net.dds.ems.service;

import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Utilisateur;
import net.dds.ems.repository.RevendeurRepository;
import net.dds.ems.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RevendeurRepository revendeurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository, RevendeurRepository revendeurRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.revendeurRepository = revendeurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNom(username);
        Optional<Revendeur> revendeur = revendeurRepository.findByNom(username);
        if (utilisateur.isPresent()) {
        System.out.println(utilisateur.get().getId());
            return User.builder()
                    .username(utilisateur.get().getNom())
                    .password(utilisateur.get().getMotDePasse())
                    .roles(utilisateur.get().getRole().getNom())
                    .build();
        } else if (revendeur.isPresent()) {
        System.out.println("yes");
            return User.builder()
                    .username(revendeur.get().getNom())
                    .password(revendeur.get().getMotDePasse())
                    .roles(revendeur.get().getRole().getNom())
                    .build();
        } else {
            throw new UsernameNotFoundException("Aucun utilisateur ne correspond à ce username");
        }
    }
}
