package com.hadday.gestion_commission.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcartAllFees implements Serializable, Comparable<EcartAllFees> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeEcart;
    private Date date;
    @ManyToOne(targetEntity = AllFees.class)
    private AllFees allFees;
    @ManyToOne(targetEntity = AllFeesGenerated.class)
    private AllFeesGenerated allFeesGenerated;
    private String identifiant;
    private boolean deleted;
    private boolean ajouter;
    private boolean modifier;
    private boolean supprimer;


    @Override
    public int compareTo(EcartAllFees o) {
        if (
                this.getAllFees().equals(o.getAllFees()) ||
                        this.getAllFeesGenerated().equals(o.getAllFeesGenerated())
        ) {
            if (this.getIdentifiant().equals(o.getIdentifiant())) {
                return 1;
            }
        }
        return -1;
    }
}
