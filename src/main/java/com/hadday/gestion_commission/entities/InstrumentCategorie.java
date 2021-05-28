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
@ToString
public class InstrumentCategorie extends Auditable<String> implements Serializable,Comparable<InstrumentCategorie> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String category;
    private boolean deleted;
    @ManyToOne
    private InstrumentType instrumentType;

    @OneToMany(mappedBy = "instrumentCategorie", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Collection<FeeRate> feeRates;


    @Override
    public int compareTo(InstrumentCategorie o) {
        if(
                this.getCategory().equals(o.getCategory()) &&
                        this.getInstrumentType().equals(o.getInstrumentType())
        ){
            return 1;
        }else{
            return -1;
        }
    }
}
