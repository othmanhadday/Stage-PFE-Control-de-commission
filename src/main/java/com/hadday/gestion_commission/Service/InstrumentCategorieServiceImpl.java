package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.InstrumentCategorieDTO;
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
public class InstrumentCategorieServiceImpl implements InstrumentCategorieService {

    @Autowired
    private InstrumentCategorieRepository instrumentCategorieRepository;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;
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
    public InstrumentCategorie createUpdateCategorieRate(InstrumentCategorieDTO instrumentCategorieDTO) {
        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<InstrumentCategorie> instrumentCategories = instrumentCategorieRepository.findCategorieRatesByDeletedIsFalse();

        InstrumentCategorie instrumentCategorie = new InstrumentCategorie();
        instrumentCategorie.setId(instrumentCategorieDTO.getId());
        instrumentCategorie.setCategory(instrumentCategorieDTO.getCategorieName());
        if(instrumentCategorieDTO.getInstrumentType()!=null){
            InstrumentType instType = new InstrumentType(null, "-", "-", false, instrumentCategorieDTO.getInstrumentClass(),null, null);
            if (instrumentCategorieDTO.getInstrumentType().equals("-")) {
                instType = instrumentTypeRepository.save(instrumentTypeisExist(instType));
            }else{
                instType = instrumentClassTypeService.getInstrumentTypeById(Long.valueOf(instrumentCategorieDTO.getInstrumentType()));
            }
            instrumentCategorie.setInstrumentType(instType);
        }

        InstrumentCategorie finalInstrumentCategorie = instrumentCategorie;
        instrumentCategories.forEach(ic -> {
            if (finalInstrumentCategorie.compareTo(ic) == 1) {
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
    @Transactional
    public InstrumentType instrumentTypeisExist(InstrumentType instrumentType) {
        List<InstrumentType> instrumentTypes = instrumentClassTypeService.getAllInstrumentType();
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

    @Override
    public List<InstrumentCategorie> getInstrumentCatByInstrumentType(InstrumentType instrumentType) {
        return instrumentCategorieRepository.findInstrumentCategoriesByInstrumentTypeAndDeletedIsFalse(instrumentType);
    }
}
