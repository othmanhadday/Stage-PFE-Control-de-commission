package com.hadday.gestion_commission.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class EcartCommissionController {

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

        int[] pages = new int[ecartCommissionsPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);

        return "/ecrat_commission/ecart_taux_commssion_ssatf";
    }

    @GetMapping("/avoirs")
    public String indexAvoirs(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "100") int size
    ) {

        Page<EcartCommission> ecartCommissionsPage = ecartTauxCommissionService.findEcartCommissionsByRelevesoldesAvoirs(PageRequest.of(page, size));

        model.addAttribute("ecartTaux", ecartCommissionsPage);
        model.addAttribute("feeRate", new FeeRateDto());
        model.addAttribute("ecart", new EcartCommission());

        int[] pages = new int[ecartCommissionsPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("categorieFees", feeCategorieTypeService.findCategoirieByTypeCommission("Avoirs"));

        return "/ecrat_commission/ecart_taux_commssion_ReleveSolde_avoirs";
    }

    @GetMapping("/compte")
    public String indexCompte(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "100") int size
    ) {
        Page<EcartCommission> ecartCommissionsPage = ecartTauxCommissionService.findEcartCommissionsByRelevesoldesComptes(PageRequest.of(page, size));

        model.addAttribute("ecartTaux", ecartCommissionsPage);

        int[] pages = new int[ecartCommissionsPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);

        return "/ecrat_commission/ecart_taux_commssion_ReleveSolde_compte";
    }


    @PostMapping("/avoirs")
    public String ecartCreateFeeRate(@ModelAttribute("feeRate") FeeRateDto feeRateDto, @RequestParam("currentPage") String currentPage,
                                     RedirectAttributes redirAttrs,
                                     Model model, BindingResult result) {

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
                    .findEcartCommissionsByRelevesoldesAvoirs(PageRequest.of(Integer.valueOf(currentPage), 100));

            model.addAttribute("ecartTaux", ecartCommissionsPage);
            int[] pages = new int[ecartCommissionsPage.getTotalPages()];
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("ecart", new EcartCommission());


            model.addAttribute("categorieFees", feeCategorieTypeService.findCategoirieByTypeCommission("Avoirs"));

            return "ecrat_commission/ecart_taux_commssion_ReleveSolde_avoirs";
        }
        //don't change it important
        feeRateDto.setId(null);
        FeeRate feeRate = ecartTauxCommissionService.ecartCreateFeeRate(feeRateDto);
        if (feeRate != null) {
            redirAttrs.addFlashAttribute("success", " Fée Rate a été inséré avec succès : " + feeRate.getId());
        } else {
            redirAttrs.addFlashAttribute("exist", " Fée Rate deja existe ");
        }


        return "redirect:/ecart/avoirs?page=" + currentPage;
    }


    @PostMapping("/calculate_ecart_commission")
    public String calculateEcartCommissionToAllFees(@ModelAttribute("ecart") EcartCommission ecartCommission,
                                                    @RequestParam("feeRate") String feeRateForm,
                                                    @RequestParam("currentPage") String currentPage,
                                                    RedirectAttributes redirAttrs
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        if (!feeRateForm.isEmpty()) {
            FeeRate feeRate = objectMapper.readValue(feeRateForm, FeeRate.class);
            ecartTauxCommissionService.calculatEcartCommissionToAllFees(ecartCommission, feeRate);
            redirAttrs.addFlashAttribute("success", " ce ligne a ete enregistré vers all fees ");
        } else {
            redirAttrs.addFlashAttribute("exist", " Fee Rate n'existe pas!! ");
        }

        return "redirect:/ecart/avoirs?page=" + currentPage;
    }

    @PostMapping("/calculate_all_ecart_commission")
    public String calculateAllEcartsCommissionToAllFees(
            @RequestParam("currentPage") String currentPage,
            @RequestParam(name = "size", defaultValue = "100") int size
    ) {

        Page<EcartCommission> ecartCommissionsPage = ecartTauxCommissionService.
                findEcartCommissionsByRelevesoldesAvoirs(PageRequest.of(Integer.valueOf(currentPage), size));

        ecartTauxCommissionService.calculateAllEcartCommissionToAllFees(ecartCommissionsPage);

        return "redirect:/ecart/avoirs?page=" + currentPage;
    }


}


@RestController
class EcartCommissionControllerRest {

    @Autowired
    private EcartTauxCommissionService ecartTauxCommissionService;
    @Autowired
    private InstrumentClassTypeService instrumentClassTypeService;
    @Autowired
    private FeeRateService feeRateService;

    @GetMapping("/findEcartById/{id}")
    public Map<Integer, Object> findEcartById(@PathVariable("id") Long id) {

        EcartCommission ecartCommission = ecartTauxCommissionService.findEcartById(id).get();


        // get instrument class from ecart comission
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(1, ecartCommission);
        map.put(2, instrumentClassTypeService.getInstrumentByName(ecartCommission.getInstClass()));

        return map;
    }

    @GetMapping("/findEcartAndFeeRateById/{id}")
    public Map<Integer, Object> findEcartAndFeeRateById(@PathVariable("id") Long id) {

        EcartCommission ecartCommission = ecartTauxCommissionService.findEcartById(id).get();

        HashMap<Integer, Object> map = new HashMap<>();
        map.put(1, ecartCommission);
        FeeRate feeRate = feeRateService.getFeeRate(ecartCommission.getInstClass(),
                ecartCommission.getInstType(),
                ecartCommission.getInstCat());
        map.put(2, feeRate);

        return map;
    }

}
