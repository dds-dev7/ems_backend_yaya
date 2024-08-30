package net.dds.ems.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("REVENDEUR")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Revendeur extends Agent {

    @NotEmpty(message = "Numero de piece ne devrait pas etre vide")
    private String numeroDePiece;


    @NotEmpty(message = "Secteur d'activite ne devrait pas etre vide")
    private String secteurActivite;


    @NotEmpty(message = "Type de piece ne devrait pas etre vide")
    private String typeDePiece;


    @NotNull(message = "Telephone Airtel ne devrait pas etre nulle")
    private Integer telephoneAirtel;


    @NotNull(message = "Telephone Moov ne devrait pas etre nulle")
    private Integer telephoneMoov;


    @NotNull(message = "Telephone Flash ne devrait pas etre nulle")
    private Integer telephoneFlash;


    @NotNull(message = "Crediter Caisse ne devrait pas etre mulle")
    private Boolean crediterCaisse;


    @NotNull(message = "Vendre Directement ne devrait pas etre mulle")
    private Boolean vendreDirectement;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateNaissance;

    @NotNull(message = "recouvreur ne devrait pas etre mulle")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recouvreur_id", referencedColumnName = "id")
    private Recouvreur recouvreur;

}
