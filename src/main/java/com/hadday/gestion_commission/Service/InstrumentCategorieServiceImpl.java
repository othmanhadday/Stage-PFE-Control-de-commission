package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.FeeRateRepository;
import com.hadday.gestion_commission.repositories.InstrumentCategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class InstrumentCategorieServiceImpl implements InstrumentCategorieService {

    @Autowired
    private InstrumentCategorieRepository instrumentCategorieRepository;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private FeeRateRepository feeRateRepository;

    @Override
    public InstrumentCategorie getCategorieRateById(Long id) {
        return instrumentCategorieRepository.findById(id).get();
    }

    @Override
    public List<InstrumentCategorie> getCategorieRates() {
        return instrumentCategorieRepository.findCategorieRatesByDeletedIsFalse();
    }

    @Override
    public InstrumentCategorie createUpdateCategorieRate(InstrumentCategorie instrumentCategorie) {
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<InstrumentCategorie> instrumentCategories = instrumentCategorieRepository.findCategorieRatesByDeletedIsFalse();

        InstrumentCategorie instrumentCategorie1 = new InstrumentCategorie();
        instrumentCategorie1.setCategory(instrumentCategorie.getCategory());
        instrumentCategorie1.setId(instrumentCategorie.getId());
        instrumentCategorie1.setInstrumentType(instrumentCategorie.getInstrumentType());

        instrumentCategories.forEach(ic -> {
            if (instrumentCategorie1.compareTo(ic) == 1) {
                isEquals.set(true);
                return;
            }
        });

        if (instrumentCategorie.getId() == null) {
            if (isEquals.get() == false) {
                instrumentCategorie = instrumentCategorieRepository.save(instrumentCategorie);
            } else {
                instrumentCategorie = null;
            }
        } else {
            Optional<InstrumentCategorie> categorieRateOptional = instrumentCategorieRepository.findById(instrumentCategorie.getId());
            if (categorieRateOptional.isPresent()) {
                InstrumentCategorie newCategorie = categorieRateOptional.get();
                newCategorie.setId(instrumentCategorie.getId());
                newCategorie.setInstrumentType(instrumentCategorie.getInstrumentType());
                newCategorie.setCategory(instrumentCategorie.getCategory());
                if (isEquals.get() == false) {
                    instrumentCategorie = instrumentCategorieRepository.save(newCategorie);
                } else {
                    instrumentCategorie = null;
                }
            } else {
                instrumentCategorie = instrumentCategorieRepository.save(instrumentCategorie);
            }
        }
        return instrumentCategorie;
    }

    @Override
    public InstrumentCategorie deleteCategorieRate(Long id) {
        InstrumentCategorie instrumentCategorie = instrumentCategorieRepository.findById(id).get();
        if (feeRateRepository.findFeeRatesByInstrumentCategorieAndDeletedIsFalse(instrumentCategorie).size() > 0) {
            return null;
        } else {
            instrumentCategorie.setDeleted(true);
            return instrumentCategorieRepository.save(instrumentCategorie);
        }
    }

    @Override
    public List<InstrumentCategorie> getInstrumentCatByInstrumentType(Long id) {
        InstrumentType instrumentType = instrumentClassTypeService.getInstrumentTypeById(id);
        return instrumentCategorieRepository.findInstrumentCategoriesByInstrumentTypeAndDeletedIsFalse(instrumentType);
    }
}
