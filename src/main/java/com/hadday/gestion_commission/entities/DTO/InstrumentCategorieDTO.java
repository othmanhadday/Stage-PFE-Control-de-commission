package com.hadday.gestion_commission.entities.DTO;

import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstrumentCategorieDTO {
    private Long id;
    private String categorieName;
    private String instrumentType;
    private InstrumentClass instrumentClass;

}
