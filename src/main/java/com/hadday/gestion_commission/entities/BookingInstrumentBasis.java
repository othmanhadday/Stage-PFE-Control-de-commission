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
public class BookingInstrumentBasis implements Serializable, Comparable<BookingInstrumentBasis> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "bookFunction_id")
    private BookingFunction bookFunction;
    @ManyToOne
    @JoinColumn(name = "instrumentBasis_id")
    private InstrumentClassBasisInstrument instrumentClassBasisInstrument;
    @ManyToOne
    @JoinColumn(name = "feeType_id")
    private FeeType feeType;
    private char creditDebit;
    private double feeRate;
    private boolean deleted;

    @Override
    public int compareTo(BookingInstrumentBasis o) {
        if (this.getBookFunction().equals(o.getBookFunction()) &&
                this.getFeeType().equals(o.getFeeType()) &&
                this.getFeeRate() == o.getFeeRate() &&
                this.getInstrumentClassBasisInstrument().equals(o.getInstrumentClassBasisInstrument()) &&
                this.getCreditDebit() == o.getCreditDebit()
        ) {
            return 1;
        }else {
            return -1;
        }
    }
}
