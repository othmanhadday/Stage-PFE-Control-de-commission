package com.hadday.gestion_commission.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "testfess")
public class AllFees implements Serializable, Comparable<AllFees> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long COM_SEQ;
    private String FACT_NO;
    private Date DATE;
    private String BPID_RECIPIENT;
    private String BPID_LIABLE;
    private String FEECATEGORY;
    private String FEETYPE;
    private String TRANSACTIONREFERENCE;
    private String TRANSACTIONTYPE;
    private Date TRADEDATE;
    private Date SETTLEMENTDATE;
    private Long ACNO;
    private String ACCOUNTTYPE;
    private String ACCATEGORY;
    private String ISIN;
    private String ISIN_CREATIONDATE;
    private String INSTRUMENTTYPE;
    private Long id;
    @Column(nullable = false)
    private double PRICE;
    private Double FEEBASIS;
    private double AMOUNT;
    private String CURRENCY;
    private Long BPID_AMC4MF;
    private String DC;
    private Double CAPI;
    private boolean processed;
    private boolean deleted;

    @Override
    public int compareTo(AllFees o) {
        if (
                this.ISIN.equals(o.ISIN) &&
                        this.DATE.equals(o.DATE) &&
                        this.FEEBASIS.equals(o.FEEBASIS) &&
                        this.PRICE == o.PRICE &&
                        this.BPID_RECIPIENT.equals(o.BPID_RECIPIENT) &&
                        this.BPID_LIABLE.equals(o.BPID_LIABLE)
        ) {
            return 1;
        }
        return 0;
    }
}
