package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.Constante.Queries;
import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRateRepository extends JpaRepository<FeeRate, Long> {

    public List<FeeRate> findFeeRatesByDeletedIsFalse();

    public List<FeeRate> findFeeRatesByInstrumentCategorieAndDeletedIsFalse(InstrumentCategorie instrumentCategorie);

    public List<FeeRate> findFeeRatesByFeeTypeAndDeletedIsFalse(FeeType feeType);

    @Query(value = Queries.DA_Avoirs_Query)
     FeeRate findFeeRate(
            @Param("className") String className,
            @Param("typeCode") String typeCode,
            @Param("category") String category,
            @Param("typeCommission") String typeCommission
    );

}
