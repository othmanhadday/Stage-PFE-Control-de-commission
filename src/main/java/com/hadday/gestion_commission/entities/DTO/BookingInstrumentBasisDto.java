package com.hadday.gestion_commission.entities.DTO;

import com.hadday.gestion_commission.entities.BookingFunction;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentClassBasisInstrument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingInstrumentBasisDto {
    private Long id;
    private FeeType feeType;
    private String feeRate;
    private BookingFunction bookFunction;
    private String instrumentClassBasisInstrument;
    private String instrumentType;
    private InstrumentClass instrumentClass;
    private char creditDebit;
    private boolean deleted;
}
