package com.hadday.gestion_commission.entities;

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
public class AllFeesGenerated implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_calcul_commission;
    private Double amount;
    @ManyToOne(targetEntity = Ssatf.class)
    private Ssatf ssatf;
    @ManyToOne(targetEntity = RelevesoldesAvoirs.class, fetch = FetchType.LAZY)
    private RelevesoldesAvoirs relevesoldesAvoirs;
    @ManyToOne(targetEntity = RelevesoldesComptes.class, fetch = FetchType.LAZY)
    private RelevesoldesComptes relevesoldesComptes;
    @ManyToOne(targetEntity = FeeRate.class, fetch = FetchType.LAZY)
    private FeeRate feeRate;
    @ManyToOne(targetEntity = BookingInstrumentBasis.class, fetch = FetchType.LAZY)
    private BookingInstrumentBasis bookingInstrumentBasis;
    private String identifiant;
    private String typeCommission;
}
