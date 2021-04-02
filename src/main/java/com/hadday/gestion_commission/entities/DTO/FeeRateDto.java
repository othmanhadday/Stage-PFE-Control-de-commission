package com.hadday.gestion_commission.entities.DTO;

import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeeRateDto {
    private Long id;
    private FeeType feeType;
    private InstrumentCategorie instrumentCategorie;
    private String feeRate;
    private char tauxMontant;
    private String montant;
}
