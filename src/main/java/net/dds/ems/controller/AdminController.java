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

    @PostMapping(path = "/inscription")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) throws Exception {
        AdminDto createdAdmin = this.adminService.createAdmin(adminDto);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<AdminDto> showAdmin(){
        return this.adminService.showAdmin();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public AdminDto showAdminById(@PathVariable int id){
        return this.adminService.showAdminById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto) throws Exception {
        AdminDto updatedAdmin = this.adminService.updateAdmin(adminDto);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteAdmin(@PathVariable int id){
        this.adminService.deleteAdmin(id);
    }

}
