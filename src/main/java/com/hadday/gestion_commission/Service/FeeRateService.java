package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.FeeRate;

import java.util.List;

public interface FeeRateService {
    public FeeRate findFeeRatById(Long id);

    public List<FeeRate> findFeeRates();

    public FeeRate createUpdateFeeRate(FeeRateDto feeRateDto);

    public void deleteFeeRate(Long id);

    public FeeRate getFeeRate(String className, String type, String cate,String typeCommission);

    public FeeRate getFeeRate(String className, String type, String cate,String typeCommission,String feeType);

}
