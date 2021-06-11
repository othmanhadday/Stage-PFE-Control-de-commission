package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.EcartTauxCommissionService;
import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.Service.FeeRateService;
import com.hadday.gestion_commission.Service.InstrumentClassTypeService;
import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.EcartCommission;
import com.hadday.gestion_commission.entities.FeeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ecart")
public class EcartCommissionDAController {

    @Autowired
    private FeeCategorieTypeService feeCategorieTypeService;
    @Autowired
    private EcartTauxCommissionService ecartTauxCommissionService;

    @GetMapping("/droitAdmission")
    public String indexDroitAdmission(Model model,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "100") int size
    ) {
        Page<EcartCommission> ecartCommissionsPage = ecartTauxCommissionService.
                findEcartCommissionsBySsatf(PageRequest.of(page, size));

        model.addAttribute("ecartTaux", ecartCommissionsPage);
        model.addAttribute("feeRate", new FeeRateDto());


        int[] pages = new int[ecartCommissionsPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("categorieFees", feeCategorieTypeService.findCategoirieByTypeCommission("Droits_Admission"));
        model.addAttribute("ecart", new EcartCommission());


        return "/ecrat_commission/ecart_taux_commssion_ssatf";
    }

    @PostMapping("/droitAdmission")
    public String ecartCreateFeeRate(@ModelAttribute("feeRate") FeeRateDto feeRateDto, @RequestParam("currentPage") String currentPage,
                                     RedirectAttributes redirAttrs,
                                     Model model, BindingResult result) {
        System.out.println(feeRateDto);
        if (feeRateDto.getFeeType() == null) {
            result.rejectValue("feeType", null, "Fee Type Not Selected");
        }
        if (feeRateDto.getInstrumentCategorie() == null) {
            result.rejectValue("instrumentCategorie", null, "Instrument Categorie Not Selected");
        }
        if (feeRateDto.getFeeRate().isEmpty()) {
            result.rejectValue("feeRate", null, "Fee Rate field is Empty");
        }

        if (result.hasErrors()) {
            Page<EcartCommission> ecartCommissionsPage = ecartTauxCommissionService
                    .findEcartCommissionsBySsatf(PageRequest.of(Integer.valueOf(currentPage), 100));

            model.addAttribute("ecartTaux", ecartCommissionsPage);
            int[] pages = new int[ecartCommissionsPage.getTotalPages()];
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("ecart", new EcartCommission());
            model.addAttribute("categorieFees", feeCategorieTypeService.findCategoirieByTypeCommission("Droits_Admission"));

            return "ecrat_commission/ecart_taux_commssion_ssatf";
        }
        //don't change it important
//        feeRateDto.setId(null);
        feeRateDto.setTauxMontant('T');
        FeeRate feeRate = ecartTauxCommissionService.ecartCreateFeeRate(feeRateDto);
        if (feeRate != null) {
            redirAttrs.addFlashAttribute("success", " Fée Rate a été inséré avec succès : " + feeRate.getId());
        } else {
            redirAttrs.addFlashAttribute("exist", " Fée Rate deja existe ");
        }


        return "redirect:/ecart/droitAdmission?page=" + currentPage;
    }

    @PostMapping("/calculate_all_ecart_commission_DroitAdmission")
    public String calculateAllEcartsCommissionToAllFees(
            @RequestParam("currentPage") String currentPage,
            @RequestParam(name = "size", defaultValue = "100") int size
    ) {

        Page<EcartCommission> ecartCommissionsPage = ecartTauxCommissionService.
                findEcartCommissionsBySsatf(PageRequest.of(Integer.valueOf(currentPage), size));

        ecartTauxCommissionService.calculateAllEcartCommissionToAllFeesDroitAdmission(ecartCommissionsPage);

        return "redirect:/ecart/droitAdmission?page=" + currentPage;
    }

}

@RestController
class EcartCommissionDAControllerRest {

    @Autowired
    private EcartTauxCommissionService ecartTauxCommissionService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private FeeRateService feeRateService;

    @GetMapping("/findEcartAndFeeRateDAById/{id}")
    public Map<Integer, Object> findEcartAndFeeRateById(@PathVariable("id") Long id) {

        EcartCommission ecartCommission = ecartTauxCommissionService.findEcartById(id).get();

        HashMap<Integer, Object> map = new HashMap<>();
        map.put(1, ecartCommission);
        FeeRate feeRate = feeRateService.getFeeRate(ecartCommission.getInstClass(),
                ecartCommission.getInstType(),
                ecartCommission.getInstCat(),
                "Droits_Admission");
        map.put(2, feeRate);


        return map;
    }

}
