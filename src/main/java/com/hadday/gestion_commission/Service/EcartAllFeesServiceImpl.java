package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.EcartAllFees;
import com.hadday.gestion_commission.repositories.AllFeesRepository;
import com.hadday.gestion_commission.repositories.EcartAllFeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EcartAllFeesServiceImpl implements EcartAllFeesService {

    @Autowired
    private EcartAllFeesRepository ecartAllFeesRepository;
    @Autowired
    private AllFeesRepository allFeesRepository;

    @Override
    public Page<EcartAllFees> getEcartAllFees(Pageable pageable) {
        return ecartAllFeesRepository.findEcartAllFeesByDeletedIsFalse(pageable);
    }

    @Override
    public EcartAllFees getEcartById(Long id) {
        return ecartAllFeesRepository.findById(id).get();
    }

    @Override
    public Page<EcartAllFees> getEcartBetweenDate(Pageable pageable, Date date, Date date2) {
        return ecartAllFeesRepository.findEcartAllFeesByDeletedIsFalseAndDateBetween(pageable, date, date2);
    }

    @Override
    public EcartAllFees ajouterEcartoAllFeesFinal(EcartAllFees ecartAllFees) {

        ecartAllFees = getEcartById(ecartAllFees.getId());

        AllFees newAllfees = new AllFees();

        if (ecartAllFees.getAllFees()==null){
            newAllfees.setCOM_SEQ(null);
            newAllfees.setDATE(ecartAllFees.getDate());
            newAllfees.setBPID_RECIPIENT(ecartAllFees.getAllFeesGenerated().getBPID_RECIPIENT());
            newAllfees.setBPID_LIABLE(ecartAllFees.getAllFeesGenerated().getBPID_LIABLE());
            newAllfees.setFEECATEGORY(ecartAllFees.getAllFeesGenerated().getFeeRate().getFeeType().getCategorieFees().getCategorieFeeName());
            newAllfees.setFEETYPE(ecartAllFees.getAllFeesGenerated().getFeeRate().getFeeType().getTypeName());
            newAllfees.setTRADEDATE(ecartAllFees.getDate());
            newAllfees.setSETTLEMENTDATE(ecartAllFees.getDate());
            newAllfees.setISIN(ecartAllFees.getAllFeesGenerated().getISIN());
            newAllfees.setINSTRUMENTTYPE(ecartAllFees.getAllFeesGenerated().getFeeRate().getInstrumentCategorie().getInstrumentType().getInstrumentTypeCode());
            newAllfees.setPRICE(ecartAllFees.getAllFeesGenerated().getPrix());
            newAllfees.setFEEBASIS(ecartAllFees.getAllFeesGenerated().getQuantite());
            newAllfees.setAMOUNT(ecartAllFees.getAllFeesGenerated().getAmount());
            newAllfees.setCURRENCY("MAD");
            newAllfees.setProcessed(true);
        }else{
            newAllfees=ecartAllFees.getAllFees();
            newAllfees.setPRICE(ecartAllFees.getAllFeesGenerated().getPrix());
            newAllfees.setPRICE(ecartAllFees.getAllFeesGenerated().getPrix());
        }
        newAllfees = allFeesRepository.save(newAllfees);


        ecartAllFees.setDeleted(true);
        ecartAllFees.setAllFees(newAllfees);
        ecartAllFeesRepository.save(ecartAllFees);

        return ecartAllFees;
    }

    @Override
    public EcartAllFees deleteEcartoAllFeesFinal(EcartAllFees ecartAllFees) {

        ecartAllFees = getEcartById(ecartAllFees.getId());

        AllFees allFees = ecartAllFees.getAllFees();
        ecartAllFees.setDeleted(true);
        allFees.setDeleted(true);
        allFeesRepository.save(allFees);
        ecartAllFees=ecartAllFeesRepository.save(ecartAllFees);
        System.out.println(ecartAllFees);
        System.out.println(allFees);

        return ecartAllFees;
    }

    @Override
    public EcartAllFees saveEcartAllFees(EcartAllFees ecartAllFees) {
        return ecartAllFeesRepository.save(ecartAllFees);
    }

}
