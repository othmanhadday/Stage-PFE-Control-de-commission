package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.CategorieFees;
import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.BookingInstrumentBasisRepository;
import com.hadday.gestion_commission.repositories.CategorieFeesRepository;
import com.hadday.gestion_commission.repositories.FeeRateRepository;
import com.hadday.gestion_commission.repositories.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FeeCategorieTypeServiceImpl implements FeeCategorieTypeService {

    @Autowired
    private CategorieFeesRepository categorieFeesRepository;
    @Autowired
    private FeeTypeRepository feeTypeRepository;
    @Autowired
    private BookingInstrumentBasisRepository bookingInstrumentBasisRepository;
    @Autowired
    private FeeRateRepository feeRateRepository;

    public Optional<CategorieFees> categorieFeesById(Long id) {
        return categorieFeesRepository.findById(id);
    }

    @Override
    public List<CategorieFees> allCategorieFees() {
        return categorieFeesRepository.findCategorieFeesByDeletedIsFalse();
    }

    @Override
    public CategorieFees createOrUpdateCategorieFees(CategorieFees categorieFees) {
        CategorieFees categorieFeesIsNotNull = categorieFeesRepository.findCategorieFeesByCategorieFeeNameAndDeletedIsFalse(categorieFees.getCategorieFeeName());
        if (categorieFees.getId() == null) {
            if (categorieFeesIsNotNull == null) {
                categorieFees = categorieFeesRepository.save(categorieFees);
            } else {
                categorieFees = null;
            }
        } else {
            Optional<CategorieFees> catFee = categorieFeesRepository.findById(categorieFees.getId());
            if (catFee.isPresent()) {
                CategorieFees newCatFee = catFee.get();
                newCatFee.setId(categorieFees.getId());
                newCatFee.setCategorieFeeName(categorieFees.getCategorieFeeName());
                if (categorieFeesIsNotNull == null) {
                    categorieFees = categorieFeesRepository.save(newCatFee);
                } else {
                    categorieFees = null;
                }
            } else {
                return categorieFeesRepository.save(categorieFees);
            }
        }
        return categorieFees;
    }


    @Override
    public CategorieFees deleteCategorieFee(Long id) {
        CategorieFees categorieFees = categorieFeesById(id).get();
        if (feeTypeRepository.findFeeTypesByCategorieFeesAndDeletedIsFalse(categorieFees).size() > 0) {
            return null;
        } else {
            categorieFees.setDeleted(true);
            categorieFeesRepository.save(categorieFees);
            return categorieFees;
        }
    }

    @Override
    public Optional<FeeType> typeFeesById(Long id) {
        return feeTypeRepository.findById(id);
    }

    @Override
    public List<FeeType> allTypeFees() {
        return feeTypeRepository.findFeeTypesByDeletedIsFalse();
    }

    @Override
    public FeeType createOrUpdateFeeType(FeeType feeType) {

        AtomicBoolean isEquals = new AtomicBoolean(false);
        List<FeeType> feeTypes = feeTypeRepository.findFeeTypesByDeletedIsFalse();

        FeeType fee = feeType;
        feeTypes.forEach(its -> {
            if (its.compareTo(fee) == 1) {
                isEquals.set(true);
                return;
            }
        });

        if (feeType.getId() == null) {
            if (isEquals.get() == false) {
                feeType = feeTypeRepository.save(feeType);
            } else {
                feeType = null;
            }
        } else {
            Optional<FeeType> feeTypeOptional = feeTypeRepository.findById(feeType.getId());
            if (feeTypeOptional.isPresent()) {
                FeeType newFeeType = feeTypeOptional.get();
                newFeeType.setId(feeType.getId());
                newFeeType.setTypeName(feeType.getTypeName());
                newFeeType.setCategorieFees(feeType.getCategorieFees());
                if (isEquals.get() == false) {
                    feeType = feeTypeRepository.save(newFeeType);
                } else {
                    feeType = null;
                }
            } else {
                return feeTypeRepository.save(feeType);
            }
        }
        return feeType;
    }

    @Override
    public FeeType deleteFeeType(Long id) {
        FeeType feeType = typeFeesById(id).get();
        if (
                bookingInstrumentBasisRepository.findBookingInstrumentBasesByFeeTypeAndDeletedIsFalse(feeType).size() > 0 ||
                        feeRateRepository.findFeeRatesByFeeTypeAndDeletedIsFalse(feeType).size() > 0
        ) {
            return null;
        } else {
            feeType.setDeleted(true);
            return feeTypeRepository.save(feeType);
        }
    }

    @Override
    public List<FeeType> findFeeTypeByCategorieFee(CategorieFees categorieFees) {
        return feeTypeRepository.findFeeTypesByCategorieFeesAndDeletedIsFalse(categorieFees);
    }
}
