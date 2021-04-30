package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.Service.FeeRateService;
import com.hadday.gestion_commission.Service.InstrumentCategorieService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.FeeRate;
import com.hadday.gestion_commission.entities.InstrumentCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/gestion-commission/feeRate")
public class FeeRateController {

    @Autowired
    private FeeRateService feeRateService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("feeRate", new FeeRateDto());
        model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
        model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
        model.addAttribute("feeRates", feeRateService.findFeeRates());
        return "gestion-commission/feeRate";
    }

    @PostMapping
    public String createUpdateFeeRate(@ModelAttribute("feeRate") FeeRateDto feeRateDto,RedirectAttributes redirAttrs,
                                      Model model, BindingResult result) {
        if (feeRateDto.getFeeType() == null) {
            result.rejectValue("feeType", null, "Fee Type Not Selected");
        }
        if (feeRateDto.getInstrumentCategorie() == null) {
            result.rejectValue("instrumentCategorie", null, "Instrument Categorie Not Selected");
        }
        if (feeRateDto.getFeeRate().isEmpty() && feeRateDto.getMontant().isEmpty()) {
            result.rejectValue("feeRate", null, "Fee Rate field is Empty");
            result.rejectValue("montant", null, "Montant field is Empty");
        }

        if (result.hasErrors()) {
            model.addAttribute("instrumentClasses", instrumentClassTypeService.getAllInstrumentClass());
            model.addAttribute("feeCategories", feeCategorieTypeService.allCategorieFees());
            model.addAttribute("feeRates", feeRateService.findFeeRates());
            return "gestion-commission/feeRate";
        }

        FeeRate feeRate = feeRateService.createUpdateFeeRate(feeRateDto);
        if(feeRate !=null){
            redirAttrs.addFlashAttribute("success", " Fée Rate a été inséré avec succès : " + feeRate.getId());
        }else{
            redirAttrs.addFlashAttribute("exist", " Fée Rate deja existe ");
        }

        System.out.println(feeRate);

        return "redirect:/gestion-commission/feeRate";
    }

    @GetMapping("/{id}")
    public String deleteFeeRate(@PathVariable("id") Long id, RedirectAttributes redirAttrs) {
        feeRateService.deleteFeeRate(id);
        redirAttrs.addFlashAttribute("delete", "Fee Rate a été supprimer  avec succès ");
        return "redirect:/gestion-commission/feeRate";
    }
}

@RestController
class FeeRateControllerRest {

    @Autowired
    private FeeRateService feeRateService;
    @Autowired
    private InstrumentCategorieService instrumentCategorieService;

    @GetMapping("/instrumentCatByInstrumentType/{id}")
    public List<InstrumentCategorie> getInstrumentCategorieByInstrumentType(@PathVariable("id") Long id) {
        return instrumentCategorieService.getInstrumentCatByInstrumentType(id);
    }

    @GetMapping("/feeRateById/{id}")
    public FeeRate feeRateById(@PathVariable("id") Long id) {
        return feeRateService.findFeeRatById(id);
    }

}
