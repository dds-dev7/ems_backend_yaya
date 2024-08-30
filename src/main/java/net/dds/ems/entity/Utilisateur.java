package net.dds.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Or InheritanceType.JOINED
@DiscriminatorColumn(name = "utilisateur_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max=30, message = "Nom devrait etre superieur a 30 caracteres")
    @NotEmpty
    private String nom;

    @Size(max=10, message = "Numero devrait etre superieur a 10 caracteres")
    @NotEmpty( message = "Numero ne devrait pas etre vide")
    private String numero;


    @NotNull(message = "Statut ne devrait pas etre nulle")
    private Boolean statut;

    @Size(min=8, message = "Mot de passse ne devrait pas etre inferieur a 8")
    @NotEmpty(message = "Mot de passe ne devrait pas etre nulle")
    private String motDePasse;

    @NotNull(message = "Role ne devrait pas etre nulle")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}   )
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
