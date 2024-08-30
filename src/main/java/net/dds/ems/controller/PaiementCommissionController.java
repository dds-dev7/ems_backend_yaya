package net.dds.ems.controller;

import net.dds.ems.dto.PaiementCommissionDto;
import net.dds.ems.entity.PaiementCommission;
import net.dds.ems.entity.PaiementCommission;
import net.dds.ems.service.PaiementCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/paiement-commission")
@RestController
public class PaiementCommissionController {

    @Autowired
    private PaiementCommissionService paiementCommissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity <PaiementCommission> createPaiementCommission(@RequestBody PaiementCommission paiementCommission) throws Exception {
        PaiementCommission createdPaiementCommmission = this.paiementCommissionService.createPaiementCommission(paiementCommission);
        return new ResponseEntity<PaiementCommission>(createdPaiementCommmission,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<PaiementCommissionDto> showPaiementCommission(){
        return this.paiementCommissionService.showPaiementCommission();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<PaiementCommissionDto> showPaiementCommissionById(@PathVariable int id){
        return this.paiementCommissionService.showPaiementCommissionById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PaiementCommission> updatePaiementCommission(@PathVariable int id, @RequestBody PaiementCommission paiementCommission) throws Exception {
        PaiementCommission updatedPaiementCommission = this.paiementCommissionService.updatePaiementCommission(id, paiementCommission);
        return new ResponseEntity<>(updatedPaiementCommission, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deletePaiementCommission(@PathVariable int id){
        this.paiementCommissionService.deletePaiementCommission(id);
    }
}
