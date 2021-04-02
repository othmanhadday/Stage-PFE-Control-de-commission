package com.hadday.gestion_commission.entities.DTO;

import com.hadday.gestion_commission.entities.InstrumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategorieRateDTO {

    private Long id;
    private String categorieName;
    private InstrumentType instrumentType;

}
