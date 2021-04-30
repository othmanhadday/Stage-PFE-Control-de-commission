package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Constante.TypeCommission;
import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.entities.CategorieFees;
import com.hadday.gestion_commission.entities.FeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/gestion-commission/feeType")
public class FeeTypeController {

    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @PostMapping
    public String createOrUpdateFeeCategorie(@Valid @ModelAttribute("feeType") FeeType feeType, Model model,
                                             BindingResult result, RedirectAttributes redirAttrs) {
        if (feeType.getTypeName().isEmpty()) {
            result.rejectValue("typeName", null, " Fee Type Name field is Empty");
        }
        if (result.hasErrors()) {
            model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
            model.addAttribute("feeTypes", feeCategorieTypeService.allTypeFees());
            model.addAttribute("typeCommission", TypeCommission.values());
            model.addAttribute("feeCategorie", new CategorieFees());

            return "/gestion-commission/feesCat_feesType";
        }
        feeType = feeCategorieTypeService.createOrUpdateFeeType(feeType);
        if(feeType !=null){
            redirAttrs.addFlashAttribute("success", " Fée Type a été inséré avec succès : " + feeType.getTypeName());
        }else{
            redirAttrs.addFlashAttribute("exist", " Fée Type deja existe ");
        }

        return "redirect:/gestion-commission/feeCategorie";
    }

    @GetMapping("/{id}")
    public String deleteFeeCategorie(@PathVariable("id") Long id, RedirectAttributes redirAttrs) {
        FeeType feeType = feeCategorieTypeService.deleteFeeType(id);

        if(feeType==null){
            redirAttrs.addFlashAttribute("error", "Vous n’avez pas le droit de supprimer cet Fee Type");
        }else {
            redirAttrs.addFlashAttribute("delete", "Fee Type a été supprimer  avec succès ");
        }

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
