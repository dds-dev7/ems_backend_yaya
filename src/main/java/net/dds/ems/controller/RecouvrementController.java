package net.dds.ems.controller;

import net.dds.ems.dto.RecouvrementDto;
import net.dds.ems.entity.Recouvrement;
import net.dds.ems.entity.Recouvrement;
import net.dds.ems.service.RecouvrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/recouvrement")
@RestController
public class RecouvrementController {

    @Autowired
    private RecouvrementService recouvrementService;

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @PostMapping(path = "/create")
    public ResponseEntity<Recouvrement> createRecouvrement(@RequestBody Recouvrement recouvrement) throws Exception {
        Recouvrement createdRecouvrement = this.recouvrementService.createRecouvrement(recouvrement);
        return new ResponseEntity<>(createdRecouvrement, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @GetMapping(path = "/read")
    public Stream<RecouvrementDto> showRecouvrement(){
        return this.recouvrementService.showRecouvrement();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
    @GetMapping(path = "/read/{id}")
    public Stream<RecouvrementDto> showRecouvrementById(@PathVariable int id){
        return this.recouvrementService.showRecouvrementById(id);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'RECOUVREUR')")
//    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
//    public void updateRecouvrement(@PathVariable int id, @RequestBody Recouvrement recouvrement) throws Exception {
//        this.recouvrementService.updateRecouvrement(id, recouvrement);
//    }


    //Les controles de secu sont fait de sorte que l'utilisateur soit sure du recouvrement qu'il veut effectuer
    //De ce fait un recouvrement enregistrer ne peux plus etre supprimer.

//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @DeleteMapping(path = "/delete/{id}")
//    public void deleteRecouvrement(@PathVariable int id){
//        this.recouvrementService.deleteRecouvrement(id);
//    }
}
