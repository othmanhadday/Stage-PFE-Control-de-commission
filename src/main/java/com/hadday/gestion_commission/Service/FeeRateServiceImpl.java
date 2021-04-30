package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.FeeRateRepository;
import com.hadday.gestion_commission.repositories.InstrumentCategorieRepository;
import com.hadday.gestion_commission.repositories.InstrumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FeeRateServiceImpl implements FeeRateService {

    @Autowired
    private FeeRateRepository feeRateRepository;
    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;
    @Autowired
    private InstrumentCategorieRepository instrumentCategorieRepository;

    @Override
    public FeeRate findFeeRatById(Long id) {
        return feeRateRepository.findById(id).get();
    }

    @Override
    public List<FeeRate> findFeeRates() {
        return feeRateRepository.findFeeRatesByDeletedIsFalse();
    }

    @Override
    public FeeRate createUpdateFeeRate(FeeRateDto feeRateDto) {
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<FeeRate> feeRates = feeRateRepository.findFeeRatesByDeletedIsFalse();
        FeeRate feeRate = new FeeRate();
        feeRate.setId(feeRateDto.getId());
        feeRate.setTauxMontant(feeRateDto.getTauxMontant());

        if (feeRateDto.getInstrumentCategorie() != null) {
            InstrumentType instType = new InstrumentType(null, "-", "-", false, feeRateDto.getInstrumentClass(), null);
            if (feeRateDto.getInstrumentType() != null) {
                if (feeRateDto.getInstrumentType().equals("-")) {
                    instType = instrumentTypeRepository.save(instrumentTypeisExist(instType));
                } else {
                    instType = instrumentTypeRepository.findById(Long.valueOf(feeRateDto.getInstrumentType())).get();
                }
            }
            InstrumentCategorie instrumentCategorie = new InstrumentCategorie(null, "-", false, instType);
            if (feeRateDto.getInstrumentCategorie().equals("-")) {
                instrumentCategorie = instrumentCategorieRepository.save(instrumentCategorieisExist(instrumentCategorie));
            } else {
                instrumentCategorie = instrumentCategorieRepository.findById(Long.valueOf(feeRateDto.getInstrumentCategorie())).get();
            }
            feeRate.setInstrumentCategorie(instrumentCategorie);
        }
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
        feeRates.forEach(rate -> {
            if (rate.compareTo(feeR) == 1) {
                isEquals.set(true);
            }
        });

        if (feeRate.getId() == null) {
            if (isEquals.get() == false) {
                feeRate = feeRateRepository.save(feeRate);
            } else {
                feeRate = null;
            }
        } else {
            Optional<FeeRate> feeRateOptional = feeRateRepository.findById(feeRate.getId());
            if (feeRateOptional.isPresent()) {
                if (isEquals.get() == false) {
                    feeRate = feeRateRepository.save(feeRate);
                } else {
                    feeRate = null;
                }
            } else {
                if (isEquals.get() == false) {
                    feeRate = feeRateRepository.save(feeRate);
                } else {
                    feeRate = null;
                }
            }
        }
        return feeRate;
    }

    @Transactional
    public InstrumentType instrumentTypeisExist(InstrumentType instrumentType) {
        List<InstrumentType> instrumentTypes = instrumentTypeRepository.findInstrumentTypesByDeletedIsFalse();
        AtomicBoolean isEqual = new AtomicBoolean(false);
        InstrumentType finalInstrumentType = instrumentType;
        AtomicReference<InstrumentType> instrumentTypeExist = new AtomicReference<>();
        instrumentTypes.forEach(instType -> {
            if (finalInstrumentType.compareTo(instType) == 1) {
                instrumentTypeExist.set(instType);
                isEqual.set(true);
            }
        });

        if (isEqual.get() == true) {
            return instrumentTypeExist.get();
        } else {
            return instrumentType;
        }
    }

    @Transactional
    public InstrumentCategorie instrumentCategorieisExist(InstrumentCategorie instrumentCategorie) {
        List<InstrumentCategorie> instrumentCategories = instrumentCategorieRepository.findCategorieRatesByDeletedIsFalse();
        AtomicBoolean isEqual = new AtomicBoolean(false);
        InstrumentCategorie finalInstrumentCat = instrumentCategorie;
        AtomicReference<InstrumentCategorie> instrumentCategorieExist = new AtomicReference<>();
        instrumentCategories.forEach(instCat -> {
            if (finalInstrumentCat.compareTo(instCat) == 1) {
                instrumentCategorieExist.set(instCat);
                isEqual.set(true);
            }
        });

        if (isEqual.get() == true) {
            return instrumentCategorieExist.get();
        } else {
            return instrumentCategorie;
        }
    }

    @Override
    public void deleteFeeRate(Long id) {
        FeeRate feeRate = feeRateRepository.findById(id).get();
        feeRate.setDeleted(true);
        feeRateRepository.save(feeRate);
    }

}
