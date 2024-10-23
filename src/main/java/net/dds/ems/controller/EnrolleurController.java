package net.dds.ems.controller;

import net.dds.ems.dto.EnrolleurDto;
import net.dds.ems.entity.Enrolleur;
import net.dds.ems.service.EnrolleurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/enrolleur")
@RestController
public class EnrolleurController {

    @Autowired
    private EnrolleurService enrolleurService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<EnrolleurDto> createEnrolleur(@RequestBody EnrolleurDto enrolleur) throws Exception {
        EnrolleurDto createdEnrolleur = this.enrolleurService.createEnrolleur(enrolleur);
        return new ResponseEntity<>(createdEnrolleur, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<EnrolleurDto> showEnrolleur(){
        return this.enrolleurService.showEnrolleur();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public EnrolleurDto showEnrolleurById(@PathVariable int id){
        return this.enrolleurService.showEnrolleurById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrolleurDto>  updateEnrolleur(@RequestBody EnrolleurDto enrolleur) throws Exception {
        EnrolleurDto updatedEnrolleur = this.enrolleurService.updateEnrolleur(enrolleur);
        return new ResponseEntity<>(updatedEnrolleur, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteEnrolleur(@PathVariable int id){
        this.enrolleurService.deleteEnrolleur(id);
    }
}
