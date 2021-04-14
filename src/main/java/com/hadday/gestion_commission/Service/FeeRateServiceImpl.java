package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.repositories.FeeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FeeRateServiceImpl implements FeeRateService {

    @Autowired
    private FeeRateRepository feeRateRepository;

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
            feeRate.setInstrumentCategorie(feeRateDto.getInstrumentCategorie());
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

    @Override
    public void deleteFeeRate(Long id) {
        FeeRate feeRate = feeRateRepository.findById(id).get();
        feeRate.setDeleted(true);
        feeRateRepository.save(feeRate);
    }

}
