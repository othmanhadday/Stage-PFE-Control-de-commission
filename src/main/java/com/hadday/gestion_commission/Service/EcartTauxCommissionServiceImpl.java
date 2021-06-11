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


        //get instrument type by instrument class

        InstrumentCategorie instrumentCategorie = searchForInstrTypeAndCategorie(feeRateDto);
        feeRate.setInstrumentCategorie(instrumentCategorie);


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

        FeeRate feeR = feeRate;
        if (feeR != null) {
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

    public InstrumentCategorie searchForInstrTypeAndCategorie(FeeRateDto feeRateDto) {

        AtomicReference<InstrumentCategorie> instrumentCategorie = new AtomicReference<>();
        AtomicReference<InstrumentType> instrumentType = new AtomicReference<>();
        List<InstrumentType> instrumentTypes = instrumentClassTypeService.getInstrumentTypeByClassAndTypeCode(feeRateDto.getInstrumentClass(), feeRateDto.getInstrumentType());

        if (instrumentTypes.size() <= 0) {
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
            instrumentType.set(instrumentTypes.get(0));
            instrumentCategorie.set(instrumentCategorieRepository.findInstrumentCategoriesByInstrumentTypeAndCategoryAndDeletedIsFalse(instrumentType.get(), feeRateDto.getInstrumentCategorie()));
            if (instrumentCategorie.get() == null) {
                InstrumentCategorieDTO instrumentCategorieDTO = new InstrumentCategorieDTO();
                instrumentCategorieDTO.setCategorieName(feeRateDto.getInstrumentCategorie());
                instrumentCategorieDTO.setInstrumentType(instrumentType.get().getId().toString());
                instrumentCategorie.set(instrumentCategorieService.createUpdateCategorieRate(instrumentCategorieDTO));
            }
        }
        System.out.println(instrumentCategorie.get());
        return instrumentCategorie.get();
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

        double result = 0;
        AllFeesGenerated allFeesGenerated = new AllFeesGenerated();
        allFeesGenerated.setDate_calcul_commission(new Date());

        if (feeRate.getFeeType().getCategorieFees().getTypeCommission().equals("Avoirs")) {
            result = RegleCalcul.avoirsRegle(ecartCommission.relevesoldesAvoirs.getQUANTITE(),
                    ecartCommission.relevesoldesAvoirs.getPrice(),
                    feeRate.getFeeRate());
            allFeesGenerated.setAmount(result);
            allFeesGenerated.setISIN(ecartCommission.relevesoldesAvoirs.getCODE_VALEUR());
            allFeesGenerated.setRelevesoldesAvoirs(ecartCommission.getRelevesoldesAvoirs());
            allFeesGenerated.setIdentifiant(ecartCommission.relevesoldesAvoirs.getCODE_VALEUR() + "-"
                    + ecartCommission.relevesoldesAvoirs.getDATE_MAJ() + "-"
                    + ecartCommission.relevesoldesAvoirs.getQUANTITE());
            allFeesGenerated.setTypeCommission("Releve de solde (Avoirs)");
            allFeesGenerated.setFeeRate(feeRate);
            allFeesGenerated.setQuantite(ecartCommission.relevesoldesAvoirs.getQUANTITE());
            allFeesGenerated.setBPID_LIABLE(ecartCommission.relevesoldesAvoirs.getCODE_MANDANT());
            allFeesGenerated.setBPID_RECIPIENT(ecartCommission.relevesoldesAvoirs.getCODE_MANDATAIRE());
        }

        if (feeRate.getFeeType().getCategorieFees().getTypeCommission().equals("Comptes")) {
            result = RegleCalcul.comptesRegle(ecartCommission.relevesoldesComptes.getQUANTITE(),
                    feeRate.getMontant());
            allFeesGenerated.setAmount(result);
            allFeesGenerated.setISIN(ecartCommission.relevesoldesComptes.getCODE_VALEUR());
            allFeesGenerated.setRelevesoldesComptes(ecartCommission.getRelevesoldesComptes());
            allFeesGenerated.setIdentifiant(ecartCommission.relevesoldesComptes.getCODE_VALEUR() + "-"
                    + ecartCommission.relevesoldesComptes.getDATE_MAJ() + "-"
                    + ecartCommission.relevesoldesComptes.getQUANTITE());
            allFeesGenerated.setTypeCommission("Releve de solde (Comptes)");
            allFeesGenerated.setFeeRate(feeRate);
            allFeesGenerated.setQuantite(ecartCommission.relevesoldesComptes.getQUANTITE());
            allFeesGenerated.setBPID_LIABLE(ecartCommission.relevesoldesComptes.getCODE_MANDANT());
            allFeesGenerated.setBPID_RECIPIENT(ecartCommission.relevesoldesComptes.getCODE_MANDATAIRE());
        }

        if (feeRate.getFeeType().getCategorieFees().getTypeCommission().equals("Droits_Admission")) {
            System.out.println(ecartCommission.ssatf);
            result = RegleCalcul.avoirsRegle(ecartCommission.ssatf.getQuantity(),
                    ecartCommission.ssatf.getTradeprice(),
                    feeRate.getFeeRate());
            allFeesGenerated.setAmount(result);
            allFeesGenerated.setISIN(ecartCommission.ssatf.getSecurityid());
            allFeesGenerated.setSsatf(ecartCommission.getSsatf());
            allFeesGenerated.setIdentifiant(ecartCommission.ssatf.getSecurityid() + "-"
                    + ecartCommission.ssatf.getTradedate() + "-"
                    + ecartCommission.ssatf.getQuantity());
            allFeesGenerated.setTypeCommission("SSATF");
            allFeesGenerated.setFeeRate(feeRate);
            allFeesGenerated.setQuantite(ecartCommission.ssatf.getQuantity());
        }

        allFeesGeneratedRepository.save(allFeesGenerated);

        ecartCommission.setDeleted(true);
        ecartCommission = ecartCommissionRepository.save(ecartCommission);

        return ecartCommission;
    }

    @Override
    public void calculateAllEcartCommissionToAllFeesAvoirs(Page<EcartCommission> ecartCommissions) {

        ecartCommissions.forEach(ecartCommission -> {

            FeeRate feeRate = feeRateRepository.findFeeRate(ecartCommission.getInstClass(),
                    ecartCommission.getInstType(),
                    ecartCommission.getInstCat(),
                    "Avoirs"
            );

            if (feeRate != null) {
                calculatEcartCommissionToAllFees(ecartCommission, feeRate);
            }
        });
    }

    @Override
    public void calculateAllEcartCommissionToAllFeesDroitAdmission(Page<EcartCommission> ecartCommissions) {
        ecartCommissions.forEach(ecartCommission -> {
            FeeRate feeRate = feeRateRepository.findFeeRate(ecartCommission.getInstClass(),
                    ecartCommission.getInstType(),
                    ecartCommission.getInstCat(),
                    "Droits_Admission"
            );

            if (feeRate != null) {
                calculatEcartCommissionToAllFees(ecartCommission, feeRate);
            }

        });
    }

    @Override
    public void calculateAllEcartCommissionToAllFeesCompte(Page<EcartCommission> ecartCommissions) {
        ecartCommissions.forEach(ecartCommission -> {
            String feeType = "";

            if (
                    ecartCommission.relevesoldesComptes.getCODE_MANDATAIRE().equals("00000000001") ||
                            ecartCommission.relevesoldesComptes.getCODE_MANDATAIRE().equals("00000000010")
            ) {
                feeType = "P030";
            } else {
                feeType = "P029";
            }
            FeeRate feeRate = feeRateRepository.findFeeRate(ecartCommission.getInstClass(),
                    ecartCommission.getInstType(),
                    ecartCommission.getInstCat(),
                    "Comptes",
                    feeType
            );

            if (feeRate != null) {
                calculatEcartCommissionToAllFees(ecartCommission, feeRate);
            }

        });
    }

}
