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

}
