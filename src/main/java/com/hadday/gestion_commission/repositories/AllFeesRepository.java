package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.Constante.Queries;
import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.EcartAllFees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AllFeesRepository extends JpaRepository<AllFees, Long> {

//    public AllFees findAllFeesByCOM_SEQ(Long id);

    public Page<AllFees> findAllFeesByDeletedIsFalseAndDATE(Pageable pageable, Date date);

    public List<AllFees> findAllFeesByDeletedIsFalseAndDATE(Date date);

    public List<AllFees> findAllFeesByDeletedIsFalseAndProcessedIsTrueAndEcartIsFalse();

    public Page<AllFees> findAllFeesByDeletedIsFalseAndProcessedIsTrueAndEcartIsFalse(Pageable pageable);

    public Page<AllFees> findAllFeesByDeletedIsFalseAndDATEBetweenAndProcessedIsTrueAndEcartIsFalse(Pageable pageable, Date date, Date date2);

    @Query(value = Queries.GET_ALLFEES_BY_IDENTITY)
    public List<AllFees> findAll(
            @Param("feeCategorie") String feeCategorie,
            @Param("DATE") Date Date,
            @Param("ISIN") String ISIN,
            @Param("FEEBASIS") double FEEBASIS,
            @Param("BPID_RECIPIENT") String BPID_RECIPIENT,
            @Param("BPID_LIABLE") String BPID_LIABLE
    );

}
