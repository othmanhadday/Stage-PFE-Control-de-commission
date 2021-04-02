package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.InstrumentCategorieService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.DTO.CategorieRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gestion-commission/category-rate")
public class CategorieRateController {

    @Autowired
    private InstrumentCategorieService instrumentCategorieService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categorieRates", instrumentCategorieService.getCategorieRates());
        model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
        model.addAttribute("categorieRate", new CategorieRateDTO());
        return "gestion-commission/categoryRate";
    }

    @PostMapping
    public String createUpdateCategorieRate(@ModelAttribute("categorieRate") CategorieRateDTO categorieRateDto, Model model, BindingResult result) {
        if (categorieRateDto.getCategorieName().isEmpty()) {
            result.rejectValue("categorieName", null, "Categorie Name field is Empty");
        }

        if (result.hasErrors()) {
            model.addAttribute("categorieRates", instrumentCategorieService.getCategorieRates());
            model.addAttribute("instrumentTypes", instrumentClassTypeService.getAllInstrumentType());
            return "gestion-commission/categoryRate";
        }
        InstrumentCategorie instrumentCategorie = new InstrumentCategorie();
        instrumentCategorie.setId(categorieRateDto.getId());
        instrumentCategorie.setCategory(categorieRateDto.getCategorieName());
        instrumentCategorie.setInstrumentType(categorieRateDto.getInstrumentType());
        instrumentCategorieService.createUpdateCategorieRate(instrumentCategorie);
        return "redirect:/gestion-commission/category-rate";
    }

    @GetMapping("/{id}")
    public String deleteCategorieRate(@PathVariable("id") Long id){
        instrumentCategorieService.deleteCategorieRate(id);
        return "redirect:/gestion-commission/category-rate";
    }
}

@RestController
class CategorieRateControllerRest {

    @Autowired
    private InstrumentCategorieService instrumentCategorieService;

    @GetMapping("/categorieRate/{id}")
    public InstrumentCategorie categorieRateById(@PathVariable("id") Long id) {
        return instrumentCategorieService.getCategorieRateById(id);
    }
}
