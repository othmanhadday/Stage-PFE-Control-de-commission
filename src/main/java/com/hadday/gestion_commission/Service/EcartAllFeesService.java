package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.EcartAllFees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface EcartAllFeesService {

    public Page<EcartAllFees> getEcartAllFees(Pageable pageable);

    public Page<EcartAllFees> getEcartAllFeesNotExist(Pageable pageable);

    public Page<EcartAllFees> getEcartAllFeesSurfacturation(Pageable pageable);

    public Page<EcartAllFees> getEcartAllFeesErrone(Pageable pageable);

    public Page<EcartAllFees> getEcartBetweenDateErrone(Pageable pageable, Date date, Date date2);

    public Page<EcartAllFees> getEcartBetweenDateSurfacturation(Pageable pageable, Date date, Date date2);

    public Page<EcartAllFees> getEcartBetweenDateNotExist(Pageable pageable, Date date, Date date2);

    public EcartAllFees getEcartById(Long id);

    public EcartAllFees ajouterEcartoAllFeesFinal(EcartAllFees ecartAllFees);

    public AllFees ajouterEcartoAllFeesRef(AllFees allFees);

    public AllFees supprimerEcarFromAllFeesRef(AllFees allFees);

    public EcartAllFees deleteEcartoAllFeesFinal(EcartAllFees ecartAllFees);

    public EcartAllFees saveEcartAllFees(EcartAllFees ecartAllFees);

}
