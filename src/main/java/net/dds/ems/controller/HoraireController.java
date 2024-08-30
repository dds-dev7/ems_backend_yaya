package net.dds.ems.controller;

import net.dds.ems.dto.HoraireDto;
import net.dds.ems.entity.Horaire;
import net.dds.ems.service.HoraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/horaire")
@RestController
public class HoraireController {

    @Autowired
    private HoraireService horaireService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<Horaire> createHoraire(@RequestBody Horaire horaire) throws Exception {
        Horaire createdHoraire = this.horaireService.createHoraire(horaire);
        return new ResponseEntity<>(createdHoraire, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<HoraireDto> showHoraire(){
        return this.horaireService.showHoraire();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/readByRevendeur/{revendeurId}")
    public Stream<HoraireDto> showHoraireByRevendeur(@PathVariable int revendeurId){
        return this.horaireService.showHoraireByRevendeur(revendeurId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public void modifier(@PathVariable int id, @RequestBody Horaire horaire) throws Exception {
        this.horaireService.updateHoraire(id, horaire);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteHoraire(@PathVariable int id){
        this.horaireService.deleteHoraire(id);
    }

}
