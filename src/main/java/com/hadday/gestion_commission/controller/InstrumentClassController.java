package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.CategorieFees;
import com.hadday.gestion_commission.entities.FeeType;
import com.hadday.gestion_commission.entities.InstrumentClass;
import com.hadday.gestion_commission.entities.InstrumentType;
import com.hadday.gestion_commission.repositories.InstrumentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gestion-commission/instrument-class")
public class InstrumentClassController {

    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @RequestMapping
    public String index(Model model) {
        model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
        model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
        model.addAttribute("instrumentClass", new InstrumentClass());
        model.addAttribute("instrumentType", new InstrumentType());
        return "/gestion-commission/InstrumentClass_type";
    }

    @PostMapping
    public String createOrUpdateInstrumentClass(@Valid @ModelAttribute("instrumentClass") InstrumentClass instrumentClass, Model model, BindingResult result) {
        if (instrumentClass.getInstrementClass().isEmpty()) {
            result.rejectValue("instrementClass", null, "Instrument Class field is Empty");
        }
//        System.out.println(instrumentClass.getInstrementClass());
        if (result.hasErrors()) {
            model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
            model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
            model.addAttribute("instrumentType", new InstrumentType());

            return "/gestion-commission/InstrumentClass_type";
        }
        instrumentClassTypeService.createUpdateInstrumentClass(instrumentClass);
        return "redirect:/gestion-commission/instrument-class";
    }

    @GetMapping("/{id}")
    public String deleteInstrumentType(@PathVariable("id") Long id) {
        instrumentClassTypeService.deleteInstrumentType(id);
        return "redirect:/gestion-commission/instrument-class";
    }
}

@RestController
class InstrumentClassControllerRest {

    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;

    @GetMapping("/instrumentClass/{id}")
    public InstrumentClass instrumentClassById(@PathVariable("id") Long id) {
        return instrumentClassTypeService.getInstrumentClassById(id);
    }

}
