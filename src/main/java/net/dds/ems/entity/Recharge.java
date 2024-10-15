package net.dds.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "recharge")
public class Recharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty
    private String type;

    @NotNull
    private Double montant;

    @NotEmpty
    private String acteur;

    private LocalDateTime date;

    private String statut;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service", referencedColumnName = "id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName ="id")
    private Admin admin;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "revendeur_id", referencedColumnName ="id")
    private Revendeur assignerA;

}
