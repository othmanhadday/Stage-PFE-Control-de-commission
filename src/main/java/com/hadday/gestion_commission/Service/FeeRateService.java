package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.FeeRate;

import java.util.List;

public interface FeeRateService {
    public FeeRate findFeeRatById(Long id);
    public List<FeeRate> findFeeRates();
    public void createUpdateFeeRate(FeeRateDto feeRateDto);
    public void deleteFeeRate(Long id);
}
