package net.dds.ems.controller;

import net.dds.ems.dto.CaissierDto;
import net.dds.ems.entity.Caissier;
import net.dds.ems.service.CaissierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/caissier")
@RestController
public class CaissierController {

    @Autowired
    private CaissierService caissierService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<Caissier> createCaissier(@RequestBody Caissier caissier) throws Exception {
        Caissier createdCaissier = this.caissierService.createCaissier(caissier);
        return new ResponseEntity<>( createdCaissier, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<CaissierDto> showCaissier(){
        return this.caissierService.showCaissier();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<CaissierDto> showCaissierById(@PathVariable int id){
        return this.caissierService.showCaissierById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Caissier> modifier(@PathVariable int id, @RequestBody Caissier caissier) throws Exception {
        Caissier updatedCaissier = this.caissierService.updateCaissier(id, caissier);
        return new ResponseEntity<>(updatedCaissier,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteCaissier(@PathVariable int id){
        this.caissierService.deleteCaissier(id);
    }

}
