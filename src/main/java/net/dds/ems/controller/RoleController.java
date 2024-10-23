package net.dds.ems.controller;

import net.dds.ems.dto.ServiceDto;
import net.dds.ems.dto.RoleDto;
import net.dds.ems.entity.Role;
import net.dds.ems.entity.Service;
import net.dds.ems.entity.Role;
import net.dds.ems.service.ServiceService;
import net.dds.ems.service.RoleService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) throws Exception {
        Role createdRole = this.roleService.createRole(role);
        return new ResponseEntity<Role>(createdRole, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<RoleDto> showRole(){
        return this.roleService.showRole();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<RoleDto> showRoleById(@PathVariable int id){
        return this.roleService.showRoleById(id);
    }

    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> updateRole(@PathVariable int id, @RequestBody Role role) throws Exception {
        Role updatedRole = this.roleService.updateRole(id, role);
        return new ResponseEntity<>(updatedRole, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteRole(@PathVariable int id){
        this.roleService.deleteRole(id);
    }
}
