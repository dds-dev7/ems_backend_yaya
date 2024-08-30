package net.dds.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "compte_internationale")
public class CompteInternationale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message= "Pays ne devrait pas etre vide")
    private String pays;


    @NotEmpty(message = "MNC ne devrait pas etre vide")
    private String mnc;


    @NotNull(message= "Comission ne devrait pas etre nulle")
    private Double commission;

}
