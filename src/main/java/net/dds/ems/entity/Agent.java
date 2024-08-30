package net.dds.ems.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("AGENT")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public abstract class Agent extends Utilisateur {

    @NotEmpty(message = "Quartier ne devrait pas etre vide")
    @Size(max = 30, message = "Quartier devrait etre superieur a 30 caracteres")
    private String quartier;

    private Integer numeroIdentifiant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateCreation;

    @NotEmpty(message = "Ville ne devrait pas etre vide")
    @Size(max = 30, message = "Ville devrait etre superieur a 30 caracteres")
    private String ville;
}
