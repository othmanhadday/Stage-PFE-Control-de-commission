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
public class InstrumentCategorie extends Auditable<String> implements Serializable,Comparable<InstrumentCategorie> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String category;
    private boolean deleted;
    @ManyToOne
    private InstrumentType instrumentType;

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
