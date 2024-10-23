package net.dds.ems.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@DiscriminatorValue("RECOUVREUR")
@EqualsAndHashCode(callSuper = true)
public class Recouvreur extends Utilisateur {


}
