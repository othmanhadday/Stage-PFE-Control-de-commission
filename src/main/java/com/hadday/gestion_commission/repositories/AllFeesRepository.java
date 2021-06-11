package com.hadday.gestion_commission.repositories;

import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.EcartAllFees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AllFeesRepository extends JpaRepository<AllFees, Long> {

    public Page<AllFees> findAllFeesByDeletedIsFalseAndDATE(Pageable pageable, Date date);

    public List<AllFees> findAllFeesByDeletedIsFalseAndDATE(Date date);

    public List<AllFees> findAllFeesByDeletedIsFalse();

    public Page<AllFees> findAllFeesByDeletedIsFalse(Pageable pageable);

    public Page<AllFees> findAllFeesByDeletedIsFalseAndDATEBetween(Pageable pageable, Date date, Date date2);
}
