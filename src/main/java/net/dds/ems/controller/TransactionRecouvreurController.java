package net.dds.ems.controller;

import net.dds.ems.dto.TransactionRecouvreurDto;
import net.dds.ems.entity.TransactionRecouvreur;
import net.dds.ems.service.TransactionRecouvreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RequestMapping(path = "/transaction-recouvreur")
@RestController
public class TransactionRecouvreurController {

    @Autowired
    private TransactionRecouvreurService transactionRecouvreurService;

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @PostMapping(path = "/create")
    public ResponseEntity<TransactionRecouvreur> createTransactionRecouvreur(@RequestBody TransactionRecouvreur transactionRecouvreur) throws Exception {
        TransactionRecouvreur createdTransactionRecouvreur = this.transactionRecouvreurService.createTransactionRecouvreur(transactionRecouvreur);
        return new ResponseEntity<TransactionRecouvreur>(createdTransactionRecouvreur, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @GetMapping(path = "/read")
    public Stream<TransactionRecouvreurDto> showTransactionRecouvreur() {
        return this.transactionRecouvreurService.showTransactionRecouvreur();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @GetMapping(path = "/read/{id}")
    public Stream<TransactionRecouvreurDto> showTransactionRecouvreurById(@PathVariable int id) {
        return this.transactionRecouvreurService.showTransactionRecouvreurById(id);
    }

//        @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")  @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
//    public void updateTransactionRecouvreur(@PathVariable int id, @RequestBody TransactionRecouvreur transactionRecouvreur) throws Exception {
//        this.transactionRecouvreurService.updateTransactionRecouvreur(id, transactionRecouvreur);
//    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteTransactionRecouvreur(@PathVariable int id) {
        this.transactionRecouvreurService.deleteTransactionRecouvreur(id);
    }
}
