package com.hadday.gestion_commission.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ssatf implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderreference;
    private Long accountno;
    private String securityid;
    private String issuercd;
    private String agentid;
    private double quantity;
    private Date tradedate;
    private Date expectedsettlementdate;
    private Date actualsettlementdate;
    private String remarks;
    private String transfer_type;
    private String traderbpid;
    private double settlementamount;
    private double tradeprice;
    private String trade_purpose_reason;
    private String trade_status;
    private String CLASSID;
    private String INSTRCTGRY;
    private String INSTRSUBCTGRY;
    private String INSTRTYPE;
    private Date date_alimentation;
    private boolean deleted;
//    @OneToMany(mappedBy = "ssatf")
//    @JsonIgnore
//    @ToString.Exclude
//    private Collection<AllFeesGenerated> allFeesGenerateds;
}
