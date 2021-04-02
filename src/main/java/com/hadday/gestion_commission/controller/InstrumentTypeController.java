package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gestion-commission/instrumentType")
public class InstrumentTypeController {

    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @PostMapping
    public String createOrUpdateInstrumentType(@Valid @ModelAttribute("instrumentType") InstrumentType instrumentType, Model model, BindingResult result) {
        if (instrumentType.getInstrumentTypeName().isEmpty()) {
            result.rejectValue("instrumentTypeName", null, " Instrument Type Name field is Empty");
        }
        if (result.hasErrors()) {
            model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
            model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
            model.addAttribute("feeTypes", feeCategorieTypeService.allTypeFees());
            model.addAttribute("instrumentClass", new InstrumentClass());
            return "/gestion-commission/InstrumentClass_type";
        }

        instrumentClassTypeService.createUpdateInstrumentType(instrumentType);
        return "redirect:/gestion-commission/instrument-class";
    }


    @GetMapping("/{id}")
    public String deleteInstrumentType(@PathVariable("id") Long id) {
        instrumentClassTypeService.deleteInstrumentType(id);
        return "redirect:/gestion-commission/instrument-class";
    }
}

@RestController
class InstrumentTypeControllerRest {

    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;

    @GetMapping("/instrumentType/{id}")
    public InstrumentType instrumentTypeById(@PathVariable("id") Long id) {
        return instrumentClassTypeService.getInstrumentTypeById(id);
    }

}
