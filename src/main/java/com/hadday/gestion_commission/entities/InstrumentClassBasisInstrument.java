package com.hadday.gestion_commission.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.mapping.FetchProfile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "bookingInstruments")
public class InstrumentClassBasisInstrument extends Auditable<String> implements Serializable, Comparable<InstrumentClassBasisInstrument> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private InstrumentType instrumentType;
    @OneToMany(mappedBy = "instrumentClassBasisInstrument", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<BookingInstrumentBasis> bookingInstruments;
    private boolean deleted;

    @Override
    public int compareTo(InstrumentClassBasisInstrument o) {
        if (
                this.getName().equals(o.getName()) &&
                        this.getInstrumentType().equals(o.getInstrumentType())
        ) {
            return 1;
        } else {
            return -1;
        }
    }
}
