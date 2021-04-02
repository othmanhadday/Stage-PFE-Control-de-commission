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
@RequestMapping("/gestion-commission/feeType")
public class FeeTypeController {

    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @PostMapping
    public String createOrUpdateFeeCategorie(@Valid @ModelAttribute("feeType") FeeType feeType, Model model, BindingResult result) {
        if (feeType.getTypeName().isEmpty()) {
            result.rejectValue("typeName", null, " Fee Type Name field is Empty");
        }
        if (result.hasErrors()) {
            model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
            model.addAttribute("feeTypes", feeCategorieTypeService.allTypeFees());
            model.addAttribute("feeCategorie", new CategorieFees());

            return "/gestion-commission/feesCat_feesType";
        }
        feeCategorieTypeService.createOrUpdateFeeType(feeType);
        return "redirect:/gestion-commission/feeCategorie";
    }

    @GetMapping("/{id}")
    public String deleteFeeCategorie(@PathVariable("id") Long id) {
        feeCategorieTypeService.deleteFeeType(id);
        return "redirect:/gestion-commission/feeCategorie";
    }
}

@RestController
class FeeTypeControllerRest {
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @GetMapping("/feeType/{id}")
    public FeeType typeFeesById(@PathVariable("id") Long id) {
        return feeCategorieTypeService.typeFeesById(id).get();
    }
}
