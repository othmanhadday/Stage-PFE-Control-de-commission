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
@ToString(exclude = "bookingInstruments")
public class BookingFunction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private boolean deleted;
    @OneToMany(mappedBy = "bookFunction",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<BookingInstrumentBasis> bookingInstruments;
}
