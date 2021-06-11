package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Constante.RegleCalcul;
import com.hadday.gestion_commission.Service.EcartAllFeesService;
import com.hadday.gestion_commission.entities.AllFeesGenerated;
import com.hadday.gestion_commission.entities.DTO.FeeRateDto;
import com.hadday.gestion_commission.entities.EcartAllFees;
import com.hadday.gestion_commission.entities.EcartCommission;
import com.hadday.gestion_commission.repositories.AllFeesGeneratedRepository;
import com.hadday.gestion_commission.repositories.AllFeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ecart_allFees")
public class EcartAllFeesController {

    @Autowired
    private EcartAllFeesService ecartAllFeesService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "100") int size) {

        Page<EcartAllFees> ecartAllFeesPage = ecartAllFeesService.getEcartAllFees(PageRequest.of(page, size));


        int[] pages = new int[ecartAllFeesPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);

        model.addAttribute("firstDate", null);
        model.addAttribute("secondDate", null);
        model.addAttribute("errors", null);
        model.addAttribute("ecart", new EcartAllFees());


        model.addAttribute("ecartAllFees", ecartAllFeesPage);
        return "ecartsAllFees/ecart_allFees";
    }

    @GetMapping("/searchDate")
    public String searchByDate(
            Model model,
            @RequestParam("firstDate") String firstDate,
            @RequestParam("secondDate") String secondDate,
            @RequestParam("currentPage") String currentPage
    ) throws ParseException {
        HashMap<String, String> errors = new HashMap<>();
        Date date1 = null;
        Date date2 = null;
        if (firstDate.isEmpty()) {
            errors.put("firstDate", "First Date is Empty");
        }
        if (secondDate.isEmpty()) {
            errors.put("secondDate", "Second Date is Empty");
        }

        if (errors.size() > 0) {
            Page<EcartAllFees> ecartAllFeesPage = ecartAllFeesService.getEcartAllFees(PageRequest.of(0, 100));

            int[] pages = new int[ecartAllFeesPage.getTotalPages()];
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", 0);

            model.addAttribute("firstDate", null);
            model.addAttribute("secondDate", null);
            model.addAttribute("errors", errors);
            model.addAttribute("ecart", new EcartAllFees());

            model.addAttribute("ecartAllFees", ecartAllFeesPage);
            return "ecartsAllFees/ecart_allFees";
        }

        System.out.println(date1);
        System.out.println(date2);
        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
        date2 = new SimpleDateFormat("yyyy-MM-dd").parse(secondDate);

        Page<EcartAllFees> ecartAllFeesPage = ecartAllFeesService.
                getEcartBetweenDate(
                        PageRequest.of(Integer.valueOf(currentPage), 100),
                        date1,
                        date2
                );

        int[] pages = new int[ecartAllFeesPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstDate", firstDate);
        model.addAttribute("secondDate", secondDate);
        model.addAttribute("ecart", new EcartAllFees());

        model.addAttribute("ecartAllFees", ecartAllFeesPage);
        return "ecartsAllFees/ecart_allFees";
    }


    @PostMapping("/ajouter")
    public String ajouterToAllFees(@ModelAttribute("ecart") EcartAllFees ecartAllFees,
                                   RedirectAttributes redirAttrs,
                                   Model model, BindingResult result) {

        ecartAllFees = ecartAllFeesService.ajouterEcartoAllFeesFinal(ecartAllFees);
        if (ecartAllFees != null) {
            redirAttrs.addFlashAttribute("exist", " ce ligne a ete ajoute avec success ");
        }

        return "redirect:/ecart_allFees/";
    }

    @PostMapping("/supprimer")
    public String suprimerFromAllFees(@ModelAttribute("ecart") EcartAllFees ecartAllFees,
                                      RedirectAttributes redirAttrs,
                                      Model model, BindingResult result) {

        ecartAllFees = ecartAllFeesService.deleteEcartoAllFeesFinal(ecartAllFees);
        if (ecartAllFees != null) {
            redirAttrs.addFlashAttribute("success", " ce ligne a ete supprimer avec success ");
        }

        return "redirect:/ecart_allFees/";
    }


}

@RestController
class EcartAllFeesControllerRest {

    @Autowired
    private EcartAllFeesService ecartAllFeesService;

    @GetMapping("/findEcartAllFeesById/{id}")
    public EcartAllFees findEcartById(@PathVariable("id") Long id) {

        EcartAllFees ecartCommission = ecartAllFeesService.getEcartById(id);
        return ecartCommission;
    }

    @PostMapping("/modifierEcartAllFees/{id}")
    public EcartAllFees modifierEcartAllFees(@PathVariable("id") Long id, @RequestBody AllFeesGenerated allFeesGenerated) {

        double amount = 0;

        EcartAllFees ecartCommission = ecartAllFeesService.getEcartById(id);
        if (ecartCommission.getAllFees() != null) {
            ecartCommission.getAllFees().setPRICE(Double.valueOf(allFeesGenerated.getPrix()));
        }
        ecartCommission.getAllFeesGenerated().setPrix(Double.valueOf(allFeesGenerated.getPrix()));

        if (ecartCommission.getAllFeesGenerated().getSsatf() != null) {
            amount = RegleCalcul.droitAdmissionRegle(
                    ecartCommission.getAllFeesGenerated().getQuantite(),
                    ecartCommission.getAllFeesGenerated().getPrix(),
                    ecartCommission.getAllFeesGenerated().getFeeRate().getFeeRate()
            );
        }

        if (ecartCommission.getAllFeesGenerated().getRelevesoldesAvoirs() != null) {
            amount = RegleCalcul.avoirsRegle(
                    ecartCommission.getAllFeesGenerated().getQuantite(),
                    ecartCommission.getAllFeesGenerated().getPrix(),
                    ecartCommission.getAllFeesGenerated().getFeeRate().getFeeRate()
            );
        }

        if (ecartCommission.getAllFeesGenerated().getRelevesoldesComptes() != null) {
            amount = RegleCalcul.comptesRegle(
                    ecartCommission.getAllFeesGenerated().getQuantite(),
                    ecartCommission.getAllFeesGenerated().getFeeRate().getMontant()
            );
        }
        ecartCommission.getAllFeesGenerated().setAmount(amount);
        if (ecartCommission.getAllFees() != null) {
            ecartCommission.getAllFees().setAMOUNT(amount);
        }

        ecartCommission = ecartAllFeesService.saveEcartAllFees(ecartCommission);
        return ecartCommission;
    }

}
