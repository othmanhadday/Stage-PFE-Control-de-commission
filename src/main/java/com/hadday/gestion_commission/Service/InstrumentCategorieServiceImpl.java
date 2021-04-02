package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.CategorieRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentCategorieServiceImpl implements InstrumentCategorieService {

    @Autowired
    private CategorieRateRepository categorieRateRepository;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;

    @Override
    public InstrumentCategorie getCategorieRateById(Long id) {
        return categorieRateRepository.findById(id).get();
    }

    @Override
    public List<InstrumentCategorie> getCategorieRates() {
        return categorieRateRepository.findCategorieRatesByDeletedIsFalse();
    }

    @Override
    public InstrumentCategorie createUpdateCategorieRate(InstrumentCategorie instrumentCategorie) {
        InstrumentCategorie instrumentCategorieRateisNull = categorieRateRepository.findCategorieRateByCategory(instrumentCategorie.getCategory());
        if (instrumentCategorie.getId() == null) {
            if (instrumentCategorieRateisNull == null) {
                return categorieRateRepository.save(instrumentCategorie);
            } else {
                instrumentCategorie.setId(instrumentCategorieRateisNull.getId());
                instrumentCategorie.setDeleted(false);
                return categorieRateRepository.save(instrumentCategorie);
            }
        } else {
            Optional<InstrumentCategorie> categorieRateOptional = categorieRateRepository.findById(instrumentCategorie.getId());
            if (categorieRateOptional.isPresent()) {
                InstrumentCategorie newCategorie = categorieRateOptional.get();
                newCategorie.setId(instrumentCategorie.getId());
                newCategorie.setInstrumentType(instrumentCategorie.getInstrumentType());
                if (instrumentCategorieRateisNull == null) {
                    newCategorie.setCategory(instrumentCategorie.getCategory());
                } else {
                    newCategorie.setDeleted(false);
                }
                return categorieRateRepository.save(newCategorie);
            } else {
                return categorieRateRepository.save(instrumentCategorie);
            }
        }
    }

    @Override
    public void deleteCategorieRate(Long id) {
        InstrumentCategorie instrumentCategorie = categorieRateRepository.findById(id).get();
        instrumentCategorie.setDeleted(true);
        categorieRateRepository.save(instrumentCategorie);
    }

    @Override
    public List<InstrumentCategorie> getInstrumentCatByInstrumentType(Long id) {
        InstrumentType instrumentType = instrumentClassTypeService.getInstrumentTypeById(id);
        return categorieRateRepository.findInstrumentCategoriesByInstrumentType(instrumentType);
    }
}
