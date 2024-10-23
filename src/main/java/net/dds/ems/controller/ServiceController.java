package net.dds.ems.controller;

import net.dds.ems.dto.ServiceDto;
import net.dds.ems.entity.Service;
import net.dds.ems.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/service")
@RestController
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<Service> createService(@RequestBody Service service) throws Exception {
       Service createdService = this.serviceService.createService(service);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','REVENDEUR')")
    @GetMapping(path = "/read")
    public Stream<ServiceDto> showService(){
        return this.serviceService.showService();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<ServiceDto> showServiceById(@PathVariable int id){
        return this.serviceService.showServiceById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Service> updateService(@PathVariable int id, @RequestBody Service service) throws Exception {
       Service updatedService = this.serviceService.updateService(id, service);
       return new ResponseEntity<>(updatedService, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteService(@PathVariable int id){
        this.serviceService.deleteService(id);
    }
}
