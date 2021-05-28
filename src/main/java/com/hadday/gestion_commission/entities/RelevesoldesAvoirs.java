package com.hadday.gestion_commission.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Table(name = "releve_solde")
public class RelevesoldesAvoirs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date DATE_MAJ;
    private String CODE_MANDATAIRE;
    private String LIBELLE_MANDATAIRE;
    private String CODE_MANDANT;
    private String LIBELLE_MANDANT;
    private Long NUM_COMPTE;
    private String EMETTEUR;
    private String AGENT;
    private String GESTION;
    private String CODE_VALEUR;
    private String LIBELLE_VALEUR;
    private Double QUANTITE;
    private Date DATE_OUVERTURE;
    private Double Price;
    private String SENS;
    private String CLASS;
    private String TYPE;
    private String INSTRCTGRY;
    private Double capitalisation;
    private Date date_alimentation;
}
