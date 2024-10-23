package net.dds.ems.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Revendeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String nom;

    @NotEmpty( message = "Numero ne devrait pas etre vide")
    private String numero;

    @NotNull(message = "Statut ne devrait pas etre nulle")
    private Boolean statut;

    @NotEmpty(message = "Mot de passe ne devrait pas etre nulle")
    private String motDePasse;

    @NotNull(message = "Role ne devrait pas etre nulle")
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @NotEmpty(message = "Quartier ne devrait pas etre vide")
    @Size(max = 30, message = "Quartier devrait etre superieur a 30 caracteres")
    private String quartier;

    private Integer numeroIdentifiant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateCreation;

    @NotEmpty(message = "Ville ne devrait pas etre vide")
    @Size(max = 30, message = "Ville devrait etre superieur a 30 caracteres")
    private String ville;

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
