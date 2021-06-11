package com.hadday.gestion_commission.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeeRate extends Auditable<String> implements Serializable, Comparable<FeeRate> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private FeeType feeType;
    @ManyToOne

    private InstrumentCategorie instrumentCategorie;
    private double feeRate;
    private double montant;
    private char tauxMontant;
    private boolean deleted;

    @Override
    public int compareTo(FeeRate o) {
        if (this != null && o != null && this.getInstrumentCategorie().equals(o.getInstrumentCategorie()) &&
                this.getFeeType().equals(o.getFeeType()) &&
                this.getTauxMontant() == o.getTauxMontant()
        ) {
            return 1;
        } else {
            return -1;
        }
    }
}
