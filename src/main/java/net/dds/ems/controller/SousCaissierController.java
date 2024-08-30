package net.dds.ems.controller;

import net.dds.ems.dto.SousCaissierDto;
import net.dds.ems.entity.SousCaissier;
import net.dds.ems.entity.SousCaissier;
import net.dds.ems.service.SousCaissierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/sous-caissier")
@RestController
public class SousCaissierController {

    @Autowired
    private SousCaissierService sousCaissierService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity <SousCaissier> createSousCaissier(@RequestBody SousCaissier sousCaissier) throws Exception {
        SousCaissier createdSousCaissier = this.sousCaissierService.createSousCaissier(sousCaissier);
        return new ResponseEntity<SousCaissier>(createdSousCaissier,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<SousCaissierDto> showSousCaissier(){
        return this.sousCaissierService.showSousCaissier();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<SousCaissierDto> showSousCaissierById(@PathVariable int id){
        return this.sousCaissierService.showSousCaissierById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<SousCaissier> updateSousCaissier(@PathVariable int id, @RequestBody SousCaissier sousCaissier) throws Exception {
        SousCaissier updatedSousCaissier = this.sousCaissierService.updateSousCaissier(id, sousCaissier);
        return new ResponseEntity<>(updatedSousCaissier, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteSousCaissier(@PathVariable int id){
        this.sousCaissierService.deleteSousCaissier(id);
    }

}
