package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.EcartAllFees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface EcartAllFeesService {

    public Page<EcartAllFees> getEcartAllFees(Pageable pageable);

    public EcartAllFees getEcartById(Long id);

    public Page<EcartAllFees> getEcartBetweenDate(Pageable pageable, Date date, Date date2);

    public EcartAllFees ajouterEcartoAllFeesFinal(EcartAllFees ecartAllFees);

    public EcartAllFees deleteEcartoAllFeesFinal(EcartAllFees ecartAllFees);

    public EcartAllFees saveEcartAllFees(EcartAllFees ecartAllFees);

}
