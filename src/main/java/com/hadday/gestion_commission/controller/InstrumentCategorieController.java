package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.InstrumentCategorieService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.DTO.InstrumentCategorieDTO;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import com.hadday.gestion_commission.entities.InstrumentClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gestion-commission/category-rate")
public class InstrumentCategorieController {

    @Autowired
    private InstrumentCategorieService instrumentCategorieService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categorieRates", instrumentCategorieService.getCategorieRates());
        model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
        model.addAttribute("categorieRate", new InstrumentCategorieDTO());
        return "/gestion-commission/instrumentCategorie";
    }

    @PostMapping
    public String createUpdateCategorieRate(@ModelAttribute("categorieRate") InstrumentCategorieDTO instrumentCategorieDTO, Model model,
                                            BindingResult result, RedirectAttributes redirAttrs) {
        if (instrumentCategorieDTO.getCategorieName().isEmpty()) {
            result.rejectValue("categorieName", null, "Categorie Name field is Empty");
        }
        if (instrumentCategorieDTO.getInstrumentType() == null) {
            result.rejectValue("instrumentType", null, "Instrument Type Select is Null");
        }
        if (instrumentCategorieDTO.getInstrumentClass() == null) {
            result.rejectValue("instrumentClass", null, "Instrument Class Select is Null");
        }

        if (result.hasErrors()) {
            model.addAttribute("categorieRates", instrumentCategorieService.getCategorieRates());
            model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
            return "/gestion-commission/instrumentCategorie";
        }

        InstrumentCategorie instrumentCategorie = instrumentCategorieService.createUpdateCategorieRate(instrumentCategorieDTO);
        if (instrumentCategorie != null) {
            redirAttrs.addFlashAttribute("success", " Instrument Categorie a été inséré avec succès : " +
                    instrumentCategorieDTO.getCategorieName());
        } else {
            redirAttrs.addFlashAttribute("exist", " Instrument Categorie deja existe : " +
                    instrumentCategorieDTO.getCategorieName());
        }
        return "redirect:/gestion-commission/category-rate";
    }

    @GetMapping("/{id}")
    public String deleteCategorieRate(@PathVariable("id") Long id, RedirectAttributes redirAttrs) {
        InstrumentCategorie instrumentCategorie = instrumentCategorieService.deleteCategorieRate(id);
        if(instrumentCategorie==null){
            redirAttrs.addFlashAttribute("error", "Vous n’avez pas le droit de supprimer cet Instrument Catégorie. ");
        }else {
            redirAttrs.addFlashAttribute("delete", "Instrument Categorie a été supprimer  avec succès ");
        }

        return "redirect:/gestion-commission/category-rate";
    }
}

@RestController
class InstrumentCategorieControllerRest {

    @Autowired
    private InstrumentCategorieService instrumentCategorieService;

    @GetMapping("/categorieRate/{id}")
    public InstrumentCategorie categorieRateById(@PathVariable("id") Long id) {
        return instrumentCategorieService.getCategorieRateById(id);
    }
}
