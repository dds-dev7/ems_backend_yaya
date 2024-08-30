package net.dds.ems.controller;

import net.dds.ems.dto.ServiceRevendeurDto;
import net.dds.ems.entity.ServiceRevendeur;
import net.dds.ems.service.ServiceRevendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/service-revendeur")
@RestController
public class ServiceRevendeurController {

    @Autowired
    private ServiceRevendeurService serviceRevendeurService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<ServiceRevendeur> createServiceRevendeur(@RequestBody ServiceRevendeur serviceRevendeur) throws Exception {
        ServiceRevendeur createdServiceRevendeur = this.serviceRevendeurService.createServiceRevendeur(serviceRevendeur);
    return new ResponseEntity<>(createdServiceRevendeur, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<ServiceRevendeurDto> showServiceRevendeur(){
        return this.serviceRevendeurService.showServiceRevendeur();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<ServiceRevendeurDto> showServiceRevendeurById(@PathVariable int id){
        return this.serviceRevendeurService.showServiceRevendeurById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceRevendeur> updateServiceRevendeur(@PathVariable int id, @RequestBody ServiceRevendeur serviceRevendeur) throws Exception {
        ServiceRevendeur updatedServiceRevendeur = this.serviceRevendeurService.updateServiceRevendeur(id, serviceRevendeur);
        return new ResponseEntity<>(updatedServiceRevendeur,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteServiceRevendeur(@PathVariable int id){
        this.serviceRevendeurService.deleteServiceRevendeur(id);
    }
}
