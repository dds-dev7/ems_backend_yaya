package net.dds.ems.controller;

import net.dds.ems.dto.RecouvreurDto;
import net.dds.ems.entity.Recouvreur;
import net.dds.ems.entity.Recouvreur;
import net.dds.ems.service.RecouvreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/recouvreur")
@RestController
public class RecouvreurController {

    @Autowired
    private RecouvreurService recouvreurService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<Recouvreur> createRecouvreur(@RequestBody Recouvreur recouvreur) throws Exception {
        Recouvreur createdRecouvreur = this.recouvreurService.createRecouvreur(recouvreur);
        return new ResponseEntity<>(createdRecouvreur, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<RecouvreurDto> showRecouvreur(){
        return this.recouvreurService.showRecouvreur();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<RecouvreurDto> showRecouvreurById(@PathVariable int id){
        return this.recouvreurService.showRecouvreurById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Recouvreur> updateRecouvreur(@PathVariable int id, @RequestBody Recouvreur recouvreur) throws Exception {
        Recouvreur updatedRecouvreur = this.recouvreurService.updateRecouvreur(id, recouvreur);
        return new ResponseEntity<>(updatedRecouvreur, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteRecouvreur(@PathVariable int id){
        this.recouvreurService.deleteRecouvreur(id);
    }

}
