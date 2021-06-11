package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.EcartCommission;
import com.hadday.gestion_commission.entities.FeeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface EcartTauxCommissionService {

    public Optional<EcartCommission> findEcartById(Long id);

    public FeeRate ecartCreateFeeRate(FeeRateDto feeRateDto);

    public Page<EcartCommission> findEcartCommissionsByRelevesoldesAvoirs(Pageable pageable);

    public Page<EcartCommission> findEcartCommissionsBySsatf(Pageable pageable);

    public Page<EcartCommission> findEcartCommissionsByRelevesoldesComptes(Pageable pageable);

    public EcartCommission calculatEcartCommissionToAllFees(EcartCommission ecartCommission, FeeRate feeRate);

    public void calculateAllEcartCommissionToAllFeesAvoirs(Page<EcartCommission> ecartCommissions);

    public void calculateAllEcartCommissionToAllFeesDroitAdmission(Page<EcartCommission> ecartCommissions);

    public void calculateAllEcartCommissionToAllFeesCompte(Page<EcartCommission> ecartCommissions);

}
