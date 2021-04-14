package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRateRepository extends JpaRepository<FeeRate, Long> {

    public List<FeeRate> findFeeRatesByDeletedIsFalse();

    public List<FeeRate> findFeeRatesByInstrumentCategorieAndDeletedIsFalse(InstrumentCategorie instrumentCategorie);

    public List<FeeRate> findFeeRatesByFeeTypeAndDeletedIsFalse(FeeType feeType);

}
