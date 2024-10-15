package net.dds.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transfert_international")
public class TransfertInternational {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "compte_id",referencedColumnName = "id")
    private CompteInternationale compte;

    @NotEmpty
    private String destinateur;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "revendeur_id", referencedColumnName = "id")
    private Revendeur expediteur;

    @NotNull
    private Double montant;

    @NotNull
    private Double frais;

    @NotNull
    private LocalDateTime date;


}
