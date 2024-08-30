package net.dds.ems.controller;

import net.dds.ems.dto.RechargeDto;
import net.dds.ems.entity.Recharge;
import net.dds.ems.service.RechargeService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/recharge")
@RestController
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<Recharge> createRecharge(@RequestBody Recharge recharge) throws Exception {
        Recharge createdRecharge = this.rechargeService.createRecharge(recharge);
        return new ResponseEntity<>(createdRecharge, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read")
    public Stream<RechargeDto> showRecharge(){
        return this.rechargeService.showRecharge();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/read/{id}")
    public Stream<RechargeDto> showRechargeById(@PathVariable int id){
        return this.rechargeService.showRechargeById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteRecharge(@PathVariable int id){
        this.rechargeService.deleteRecharge(id);
    }
}
