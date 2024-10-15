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
@Table(name = "recouvrement")
public class Recouvrement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "recouvreur_id", referencedColumnName = "id")
    private Recouvreur agentRecouvreur;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "revendeur_id", referencedColumnName = "id")
    private Revendeur agentRevendeur;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;

    @NotNull
    private Double montant;

    private LocalDateTime date;
}
