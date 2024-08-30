package net.dds.ems.controller;

import net.dds.ems.entity.Utilisateur;
import net.dds.ems.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;


    @GetMapping("/")
    public List<Utilisateur> returnUsers(){
        return utilisateurService.getAllUtilisateurs();
    }
}
