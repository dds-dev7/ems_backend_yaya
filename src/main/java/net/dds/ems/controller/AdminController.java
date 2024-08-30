package net.dds.ems.controller;

import net.dds.ems.dto.AdminDto;
import net.dds.ems.entity.Admin;
import net.dds.ems.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequestMapping(path = "/admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws Exception {
        Admin createdAdmin = this.adminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<AdminDto> showAdmin(){
        return this.adminService.showAdmin();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<AdminDto> showAdminById(@PathVariable int id){
        return this.adminService.showAdminById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> modifier(@PathVariable int id, @RequestBody Admin admin) throws Exception {
        Admin updatedAdmin = this.adminService.updateAdmin(id, admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteAdmin(@PathVariable int id){
        this.adminService.deleteAdmin(id);
    }

}
