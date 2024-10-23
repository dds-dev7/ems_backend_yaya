package net.dds.ems.controller;

import net.dds.ems.dto.RevendeurDto;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.service.RevendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/revendeur")
@RestController
public class RevendeurController {

    @Autowired
    private RevendeurService revendeurService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity <Revendeur> createRevendeur(@RequestBody Revendeur revendeur) throws Exception {
        Revendeur createdRevendeur = this.revendeurService.createRevendeur(revendeur);
        return new ResponseEntity<Revendeur>(createdRevendeur,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<RevendeurDto> showRevendeur(){
        return this.revendeurService.showRevendeur();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public RevendeurDto showRevendeurById(@PathVariable int id){
        return this.revendeurService.showRevendeurById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Revendeur> updateRevendeur(@PathVariable int id, @RequestBody Revendeur revendeur) throws Exception {
        Revendeur updatedRevendeur = this.revendeurService.updateRevendeur(id, revendeur);
        return new ResponseEntity<>(updatedRevendeur, HttpStatus.CREATED);
    }


    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteRevendeur(@PathVariable int id){
        this.revendeurService.deleteRevendeur(id);
    }

}
