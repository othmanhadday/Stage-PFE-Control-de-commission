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
public class CategorieFees implements Serializable, Comparable<CategorieFees> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String categorieFeeName;
    private boolean deleted;
    private String typeCommission;

    @Override
    public int compareTo(CategorieFees o) {
        if (
                this.getCategorieFeeName().equals(o.getCategorieFeeName()) &&
                this.getTypeCommission().equals(o.getTypeCommission())
        ) {
            return 1;
        } else {
            return -1;
        }
    }
}
