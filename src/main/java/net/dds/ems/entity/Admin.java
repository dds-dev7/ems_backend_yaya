package net.dds.ems.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@EqualsAndHashCode(callSuper = true)

public class Admin extends Utilisateur {

}
