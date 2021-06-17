package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.AllFeesGenerated;
import com.hadday.gestion_commission.entities.EcartAllFees;
import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.repositories.AllFeesRepository;
import com.hadday.gestion_commission.repositories.EcartAllFeesRepository;
import com.hadday.gestion_commission.repositories.FeeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AllFeesServiceImpl implements AllFeesService {

    @Autowired
    private AllFeesRepository allFeesRepository;
    @Autowired
    private EcartAllFeesRepository ecartAllFeesRepository;
    @Autowired
    private FeeRateRepository feeRateRepository;

    @Override
    public List<AllFees> findAllFees() {
        return allFeesRepository.findAllFeesByDeletedIsFalseAndProcessedIsTrueAndEcartIsFalse();
    }

    @Override
    public Page<AllFees> findAllFees(Pageable pageable) {
        Date date = new Date();
        return allFeesRepository.findAllFeesByDeletedIsFalseAndProcessedIsTrueAndEcartIsFalse(pageable);
    }

    @Override
    public Page<AllFees> findAllFeesBetwwenFDate(Pageable pageable, Date date, Date date1) {
        return allFeesRepository.findAllFeesByDeletedIsFalseAndDATEBetweenAndProcessedIsTrueAndEcartIsFalse(pageable, date, date1);
    }


    @Override
    public Page<AllFees> findAllFeesisProcessed(Pageable pageable) {
        return allFeesRepository.findAllFeesByDeletedIsFalseAndProcessedIsTrueAndEcartIsFalse(pageable);
    }

    @Override
    public void controllerEtat(AllFeesGenerated allFeesGenerated) {
        double epsilon = 0.000001d;
        FeeRate feeRate = feeRateRepository.findById(allFeesGenerated.getFeeRate().getId()).get();

        System.out.println("//////////////////////////////////////////////////////////////////////////////////");
        List<AllFees> allFees = allFeesRepository.findAll(
                feeRate.getFeeType().getCategorieFees().getCategorieFeeName(),
                allFeesGenerated.getDate(),
                allFeesGenerated.getISIN(),
                allFeesGenerated.getQuantite(),
                allFeesGenerated.getBPID_RECIPIENT(),
                allFeesGenerated.getBPID_LIABLE()
        );

        if (allFees.size() == 0) {
            // Error All Fees not exist
            EcartAllFees ecartAllFees = new EcartAllFees();
            ecartAllFees.setAllFeesGenerated(allFeesGenerated);

            ecartAllFees.setDate(allFeesGenerated.getDate());
            ecartAllFees.setTypeEcart("not exist");
            ecartAllFees.setAjouter(true);

            ecartAllFees.setIdentifiant(
                    allFeesGenerated.getISIN() + "-" +
                            allFeesGenerated.getDate() + "-" +
                            allFeesGenerated.getBPID_LIABLE() + "-" +
                            allFeesGenerated.getBPID_RECIPIENT() + "-" +
                            allFeesGenerated.getQuantite()
            );
            if (exist(null, allFeesGenerated) == false) {
                ecartAllFeesRepository.save(ecartAllFees);
            }
            System.err.println("AllFees Not Exist");
        }

        if (allFees.size() == 1) {
            List<AllFees> finalAllFees = allFees;
            allFees.forEach(allFees1 -> {
                if ((allFees1.getAMOUNT() - allFeesGenerated.getAmount())<epsilon) {
                    allFees1.setProcessed(true);
                    System.out.println("success = " + finalAllFees);
                    allFeesRepository.save(allFees1);
                } else {
                    EcartAllFees ecartAllFees = new EcartAllFees();
                    ecartAllFees.setAllFees(allFees1);
                    ecartAllFees.setAllFeesGenerated(allFeesGenerated);
                    ecartAllFees.setDate(allFees1.getDATE());
                    ecartAllFees.setTypeEcart("Errone");
                    ecartAllFees.setModifier(true);
                    ecartAllFees.setIdentifiant(
                            allFees1.getISIN() + "-" +
                                    allFees1.getDATE() + "-" +
                                    allFees1.getBPID_LIABLE() + "-" +
                                    allFees1.getBPID_RECIPIENT() + "-" +
                                    allFees1.getFEEBASIS()
                    );
                    System.out.println("Errone = " + finalAllFees);

                    allFees1.setProcessed(true);
                    allFees1.setEcart(true);
                    allFeesRepository.save(allFees1);
                    if (exist(allFees1, allFeesGenerated) == false) {
                        ecartAllFeesRepository.save(ecartAllFees);
                    }
                }
            });
        }

        if (allFees.size() > 1) {
            for (AllFees allFees1 : allFees) {
                EcartAllFees ecartAllFees = new EcartAllFees();
                ecartAllFees.setAllFees(allFees1);
                ecartAllFees.setAllFeesGenerated(allFeesGenerated);
                ecartAllFees.setDate(allFees1.getDATE());
                ecartAllFees.setTypeEcart("surfacturation");
                ecartAllFees.setSupprimer(true);
                ecartAllFees.setIdentifiant(
                        allFees1.getISIN() + "-" +
                                allFees1.getDATE() + "-" +
                                allFees1.getBPID_LIABLE() + "-" +
                                allFees1.getBPID_RECIPIENT() + "-" +
                                allFees1.getFEEBASIS()
                );
                allFees1.setProcessed(true);
                allFees1.setEcart(true);
                allFeesRepository.save(allFees1);
                if (exist(allFees1, allFeesGenerated) == false) {
                    ecartAllFeesRepository.save(ecartAllFees);
                }
            }
//            add one of surfacturaion item to allfees
            AllFees allFeesSurf = allFees.get(0);
            allFeesSurf.setEcart(false);
            allFeesRepository.save(allFeesSurf);
        }
        System.out.println("//////////////////////////////////////////////////////////////////////////////////");

    }

    public boolean exist(AllFees allFees, AllFeesGenerated allFeesGenerated) {
        AtomicBoolean exist = new AtomicBoolean(false);

        if (ecartAllFeesRepository.existsEcartAllFeesByAllFees(allFees)) {
            exist.set(true);
        }
        if (ecartAllFeesRepository.existsEcartAllFeesByAllFeesGenerated(allFeesGenerated)) {
            exist.set(true);
        }

        return exist.get();
    }

}
