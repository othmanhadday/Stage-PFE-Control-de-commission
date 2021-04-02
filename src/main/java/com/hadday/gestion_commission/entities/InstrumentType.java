package com.hadday.gestion_commission.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "instrumentClassBasisInstruments")
public class InstrumentType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String instrumentTypeName;
    private String instrumentTypeCode;
    private boolean deleted;
    @ManyToOne
    private InstrumentClass instrumentClass;
    @OneToMany(mappedBy = "instrumentType",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<InstrumentClassBasisInstrument> instrumentClassBasisInstruments;
}
