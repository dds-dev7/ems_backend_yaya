package net.dds.ems.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@DiscriminatorValue("SOUSCAISSIER")
@EqualsAndHashCode(callSuper = true)
public class SousCaissier extends Agent {


}
