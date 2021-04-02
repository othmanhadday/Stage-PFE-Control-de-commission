package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.entities.CategorieFees;
import com.hadday.gestion_commission.entities.FeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gestion-commission/feeCategorie")
public class CategorieFeeController {

    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("feeCategorie", new CategorieFees());
        model.addAttribute("feeType", new FeeType());
        model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
        model.addAttribute("feeTypes", feeCategorieTypeService.allTypeFees());
        return "/gestion-commission/feesCat_feesType";
    }

    @PostMapping
    public String createOrUpdateFeeCategorie(@Valid @ModelAttribute("feeCategorie") CategorieFees categorieFees, Model model, BindingResult result) {
        if (categorieFees.getCategorieFeeName().isEmpty()) {
            result.rejectValue("categorieFeeName", null, "Categorie Fees Name field is Empty");
        }
        if (result.hasErrors()) {
            model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
            model.addAttribute("feeTypes", feeCategorieTypeService.allTypeFees());
            model.addAttribute("feeType", new FeeType());
            return "/gestion-commission/feesCat_feesType";
        }
        feeCategorieTypeService.createOrUpdateCategorieFees(categorieFees);
        return "redirect:/gestion-commission/feeCategorie";
    }

    @GetMapping("/{id}")
    public String deleteFeeCategorie(@PathVariable("id") Long id) {
        feeCategorieTypeService.deleteCategorieFee(id);
        return "redirect:/gestion-commission/feeCategorie";
    }
}

@RestController
class GestionCommissionControllerRest {
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @GetMapping("/categorieFees/{id}")
    public CategorieFees categorieFeesById(@PathVariable("id") Long id) {
        return feeCategorieTypeService.categorieFeesById(id).get();
    }
}
