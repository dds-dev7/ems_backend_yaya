package net.dds.ems.controller;

import net.dds.ems.dto.CompteInternationaleDto;
import net.dds.ems.entity.CompteInternationale;
import net.dds.ems.entity.Revendeur;
import net.dds.ems.service.CompteInternationaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/compte-internationale")
@RestController
public class CompteInternationaleController {


    @Autowired
    private CompteInternationaleService compteInternationaleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<CompteInternationale> createCompteInternationale(@RequestBody CompteInternationale compteInternationale) throws Exception {
        CompteInternationale createdCompteInternationale = this.compteInternationaleService.createCompteInternationale(compteInternationale);
        return new ResponseEntity<>(createdCompteInternationale, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<CompteInternationaleDto> showCompteInternationale(){
        return this.compteInternationaleService.showCompteInternationale();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<CompteInternationaleDto> showCompteInternationaleById(@PathVariable int id){
        return this.compteInternationaleService.showCompteInternationaleById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompteInternationale> updateCompteInternationale(@PathVariable int id, @RequestBody CompteInternationale compteInternationale) throws Exception {
        CompteInternationale updatedCompteInternationale = this.compteInternationaleService.updateCompteInternationale(id, compteInternationale);
        return new ResponseEntity<>(updatedCompteInternationale, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteCompteInternationale(@PathVariable int id){
        this.compteInternationaleService.deleteCompteInternationale(id);
    }
}
