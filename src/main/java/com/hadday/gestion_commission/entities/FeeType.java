package com.hadday.gestion_commission.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "bookingInstrumentBases")
public class FeeType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String typeName;
    @ManyToOne
    private CategorieFees categorieFees;
    @OneToMany(mappedBy = "feeType",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<BookingInstrumentBasis> bookingInstrumentBases;
    private boolean deleted;

}
