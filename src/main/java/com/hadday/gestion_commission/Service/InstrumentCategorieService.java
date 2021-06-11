package com.hadday.gestion_commission.Service;

import com.hadday.gestion_commission.entities.DTO.InstrumentCategorieDTO;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentType;

import java.util.List;

public interface InstrumentCategorieService {

    public InstrumentCategorie getCategorieRateById(Long id);

    public List<InstrumentCategorie> getCategorieRates();

    public InstrumentCategorie createUpdateCategorieRate(InstrumentCategorieDTO instrumentCategorieDTO);

    public InstrumentCategorie deleteCategorieRate(Long id);

    public List<InstrumentCategorie> getInstrumentCatByInstrumentType(Long id);

    public List<InstrumentCategorie> getInstrumentCatByInstrumentType(InstrumentType instrumentType);

    public InstrumentCategorie getInstrumentCatByInstrumentTypeAndCategory(InstrumentType instrumentType,String instrCategory);

}
