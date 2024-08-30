package net.dds.ems.controller;

import net.dds.ems.dto.TransfertInternationalDto;
import net.dds.ems.entity.TransfertInternational;
import net.dds.ems.service.TransfertInternationalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/transfert-international")
@RestController
public class TransfertInternationalController {

    @Autowired
    private TransfertInternationalService transfertInternationalService;

    @PreAuthorize("hasAnyRole('ADMIN', 'REVENDEUR')")
    @PostMapping(path = "/create")
    public ResponseEntity<TransfertInternational> createTransfertInternational(@RequestBody TransfertInternational transfertInternational) throws Exception {
       TransfertInternational createdtransfertInternational =  this.transfertInternationalService.createTransfertInternational(transfertInternational);
       return new ResponseEntity<>(createdtransfertInternational, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVENDEUR')")
    @GetMapping(path = "/read")
    public Stream<TransfertInternationalDto> showTransfertInternational(){
        return this.transfertInternationalService.showTransfertInternational();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVENDEUR')")
    @GetMapping(path = "/read/{id}")
    public Stream<TransfertInternationalDto> showTransfertInternationalById(@PathVariable int id){
        return this.transfertInternationalService.showTransfertInternationalById(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'REVENDEUR')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteTransfertInternational(@PathVariable int id){
        this.transfertInternationalService.deleteTransfertInternational(id);
    }
}
