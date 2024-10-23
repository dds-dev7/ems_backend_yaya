package net.dds.ems.controller;

import net.dds.ems.dto.HoraireDto;
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
    public ResponseEntity<HoraireDto> createHoraire(@RequestBody HoraireDto horaire) throws Exception {
        HoraireDto createdHoraire = this.horaireService.createHoraire(horaire);
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
    @GetMapping(path = "/readById/{id}")
    public HoraireDto showHoraireById(@PathVariable int id){
        return this.horaireService.showHoraireById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<HoraireDto> modifier(@RequestBody HoraireDto horaire) throws Exception {
        HoraireDto createdHoraire = this.horaireService.updateHoraire(horaire);
        return new ResponseEntity<>(createdHoraire, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteHoraire(@PathVariable int id){
        this.horaireService.deleteHoraire(id);
    }

}
