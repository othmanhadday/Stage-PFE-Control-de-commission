package com.hadday.gestion_commission.entities.DTO;

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
public class InstrumentClassBasisInstrumentDto {
    private Long id;
    private String name;
    private String instrumentType;
    private InstrumentClass instrumentClass;
}
