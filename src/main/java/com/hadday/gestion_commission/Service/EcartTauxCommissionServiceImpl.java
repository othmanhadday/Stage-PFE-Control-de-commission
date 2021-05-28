package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.Constante.RegleCalcul;
import com.hadday.gestion_commission.entities.*;
import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.DTO.InstrumentCategorieDTO;
import com.hadday.gestion_commission.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class EcartTauxCommissionServiceImpl implements EcartTauxCommissionService {

    @Autowired
    private EcartCommissionRepository ecartCommissionRepository;
    @Autowired
    private FeeRateRepository feeRateRepository;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private InstrumentCategorieService instrumentCategorieService;
    @Autowired
    private InstrumentCategorieRepository instrumentCategorieRepository;
    @Autowired
    private AllFeesGeneratedRepository allFeesGeneratedRepository;

    @Override
    public Optional<EcartCommission> findEcartById(Long id) {
        return ecartCommissionRepository.findById(id);
    }


    @Override
    public FeeRate ecartCreateFeeRate(FeeRateDto feeRateDto) {

        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<FeeRate> feeRates = feeRateRepository.findFeeRatesByDeletedIsFalse();
        FeeRate feeRate = new FeeRate();
        feeRate.setTauxMontant(feeRateDto.getTauxMontant());


        AtomicReference<InstrumentCategorie> instrumentCategorie = new AtomicReference<>();
        AtomicReference<InstrumentType> instrumentType = new AtomicReference<>();
        //get instrument type by instrument class
        List<InstrumentType> instrumentTypes = instrumentClassTypeService.getInstrumentTypeByClass(feeRateDto.getInstrumentClass());
        instrumentTypes.forEach(it -> {
            if (!it.getInstrumentTypeCode().equals(feeRateDto.getInstrumentType())) {
                InstrumentType instrumentTyp = new InstrumentType();
                instrumentTyp.setInstrumentClass(feeRateDto.getInstrumentClass());
                instrumentTyp.setInstrumentTypeCode(feeRateDto.getInstrumentType());
                instrumentTyp.setInstrumentTypeName("-");
                instrumentTyp = instrumentClassTypeService.createUpdateInstrumentType(instrumentTyp);
                if (instrumentTyp != null) {
                    InstrumentCategorie instrumentCat = new InstrumentCategorie();
                    instrumentCat.setCategory(feeRateDto.getInstrumentCategorie());
                    instrumentCat.setInstrumentType(instrumentTyp);
                    instrumentCategorie.set(instrumentCategorieRepository.save(instrumentCat));
                }

            } else {
                instrumentType.set(it);
                //get instrument categorie by instrument class
                List<InstrumentCategorie> instrumentCategories = instrumentCategorieService.getInstrumentCatByInstrumentType(it);
                instrumentCategories.forEach(ic -> {
                    if (ic.getCategory().equals(feeRateDto.getInstrumentCategorie())) {
                        instrumentCategorie.set(ic);
                    } else {
                        InstrumentCategorieDTO instrumentCategorieDTO = new InstrumentCategorieDTO();
                        instrumentCategorieDTO.setCategorieName(feeRateDto.getInstrumentCategorie());
                        instrumentCategorieDTO.setInstrumentType(instrumentType.get().getId().toString());

                        instrumentCategorie.set(instrumentCategorieService.createUpdateCategorieRate(instrumentCategorieDTO));
                    }
                });
            }
        });

        feeRate.setInstrumentCategorie(instrumentCategorie.get());

        if (feeRateDto.getFeeType() != null) {
            feeRate.setFeeType(feeRateDto.getFeeType());
        }

        if (feeRateDto.getTauxMontant() == 'M') {
            feeRate.setMontant(Double.valueOf(feeRateDto.getMontant()));
            feeRate.setFeeRate(-1);
        }

        if (feeRateDto.getTauxMontant() == 'T') {
            feeRate.setFeeRate(Double.valueOf(feeRateDto.getFeeRate()));
            feeRate.setMontant(-1);
        }

        if (feeRate!=null){
            FeeRate feeR = feeRate;
            feeRates.forEach(rate -> {
                if (rate.compareTo(feeR) == 1) {
                    isEquals.set(true);
                }
            });
        }


        if (feeRate.getId() == null) {
            if (isEquals.get() == false) {
                feeRate = feeRateRepository.save(feeRate);
            } else {
                feeRate = null;
            }
        }
        return feeRate;
    }

    @Override
    public Page<EcartCommission> findEcartCommissionsByRelevesoldesAvoirs(Pageable pageable) {
        return ecartCommissionRepository.findEcartCommissionsByRelevesoldesAvoirsIsNotNullAndDeletedIsFalse(pageable);
    }

    @Override
    public Page<EcartCommission> findEcartCommissionsBySsatf(Pageable pageable) {
        return ecartCommissionRepository.findEcartCommissionsBySsatfIsNotNullAndDeletedIsFalse(pageable);
    }

    @Override
    public Page<EcartCommission> findEcartCommissionsByRelevesoldesComptes(Pageable pageable) {
        return ecartCommissionRepository.findEcartCommissionsByRelevesoldesComptesIsNotNullAndDeletedIsFalse(pageable);
    }

    @Override
    public EcartCommission calculatEcartCommissionToAllFees(EcartCommission ecartCommission, FeeRate feeRate) {

        double result = RegleCalcul.avoirsRegle(ecartCommission.relevesoldesAvoirs.getQUANTITE(),
                ecartCommission.relevesoldesAvoirs.getPrice(),
                feeRate.getFeeRate());

        AllFeesGenerated allFeesGenerated = new AllFeesGenerated();
        allFeesGenerated.setAmount(result);
        allFeesGenerated.setDate_calcul_commission(new Date());
        allFeesGenerated.setRelevesoldesAvoirs(ecartCommission.getRelevesoldesAvoirs());
        allFeesGenerated.setIdentifiant(ecartCommission.relevesoldesAvoirs.getCODE_VALEUR() + "-"
                + ecartCommission.relevesoldesAvoirs.getDATE_MAJ() + "-"
                + ecartCommission.relevesoldesAvoirs.getQUANTITE());
        allFeesGenerated.setTypeCommission("Releve de solde (Avoirs)");
        allFeesGenerated.setFeeRate(feeRate);
        allFeesGeneratedRepository.save(allFeesGenerated);

        ecartCommission.setDeleted(true);
        ecartCommission.setTypeCommission("Releve de solde (Avoirs)");
        ecartCommission = ecartCommissionRepository.save(ecartCommission);

        return ecartCommission;
    }

    @Override
    public void calculateAllEcartCommissionToAllFees(Page<EcartCommission> ecartCommissions) {

        ecartCommissions.forEach(ecartCommission -> {
            FeeRate feeRate = feeRateRepository.findFeeRate(ecartCommission.getInstClass(),
                    ecartCommission.getInstType(),
                    ecartCommission.getInstCat(),
                    "Avoirs"
            );

            if (feeRate != null) {
                calculatEcartCommissionToAllFees(ecartCommission,feeRate);
            }

        });
    }

}
