package net.dds.ems.controller;

import net.dds.ems.dto.NumeroAutoriseDto;
import net.dds.ems.entity.NumeroAutorise;
import net.dds.ems.entity.NumeroAutorise;
import net.dds.ems.service.NumeroAutoriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/numero-autorise")
@RestController
public class NumeroAutoriseController {

    @Autowired
    private NumeroAutoriseService numeroAutoriseService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<NumeroAutoriseDto> createNumeroAutorise(@RequestBody NumeroAutoriseDto numeroAutorise) throws Exception {
        NumeroAutoriseDto createdNumeroAutorise = this.numeroAutoriseService.createNumeroAutorise(numeroAutorise);
        return new ResponseEntity<>(createdNumeroAutorise, HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public Stream<NumeroAutoriseDto> showNumeroAutorise(){
        return this.numeroAutoriseService.showNumeroAutorise();
    }

    @GetMapping(path = "/read/{id}")
    public NumeroAutoriseDto showNumeroAutoriseById(@PathVariable int id){
        return this.numeroAutoriseService.showNumeroAutoriseById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<NumeroAutoriseDto> updateNumeroAutorise(@RequestBody NumeroAutoriseDto numeroAutorise) throws Exception {
        NumeroAutoriseDto updatedNumeroAutorise = this.numeroAutoriseService.updateNumeroAutorise(numeroAutorise);
        return new ResponseEntity<>(updatedNumeroAutorise, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteNumeroAutorise(@PathVariable int id){
        this.numeroAutoriseService.deleteNumeroAutorise(id);
    }
}
