package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Constante.RegleCalcul;
import com.hadday.gestion_commission.Service.AllFeesService;
import com.hadday.gestion_commission.Service.EcartAllFeesService;
import com.hadday.gestion_commission.entities.AllFees;
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
    @Autowired
    private AllFeesService allFeesService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "currentPageNotExist", defaultValue = "0") int currentPageNotExist,
                        @RequestParam(name = "currentPageSurfacturation", defaultValue = "0") int currentPageSurfacturation,
                        @RequestParam(name = "currentPageErrone", defaultValue = "0") int currentPageErrone,
                        @RequestParam(name = "size", defaultValue = "100") int size) {

        // Not Exist allFees generated
        Page<AllFees> ecartAllFeesGeneratedPageNotExist = allFeesService.findAllFeesisProcessed(PageRequest.of(currentPageNotExist, size));
        int[] pagesNotExistFeeGen = new int[ecartAllFeesGeneratedPageNotExist.getTotalPages()];
        model.addAttribute("pagesNotExistFeeGen", pagesNotExistFeeGen);
        model.addAttribute("currentPageNotExistFeeGen", currentPageNotExist);
        model.addAttribute("ecartAllFeesGeneratedPageNotExist", ecartAllFeesGeneratedPageNotExist);


        // Not Exist allFeesben
        Page<EcartAllFees> ecartAllFeesPageNotExist = ecartAllFeesService.getEcartAllFeesNotExist(PageRequest.of(currentPageNotExist, size));
        int[] pagesNotExist = new int[ecartAllFeesPageNotExist.getTotalPages()];
        model.addAttribute("pagesNotExist", pagesNotExist);
        model.addAttribute("currentPageNotExist", currentPageNotExist);
        model.addAttribute("ecartAllFeesNotExist", ecartAllFeesPageNotExist);


        Page<EcartAllFees> ecartAllFeesSurfacturation = ecartAllFeesService.getEcartAllFeesSurfacturation(PageRequest.of(currentPageSurfacturation, size));
        int[] pagesSurfacturation = new int[ecartAllFeesSurfacturation.getTotalPages()];
        model.addAttribute("pagesSurfacturation", pagesSurfacturation);
        model.addAttribute("currentPageSurfacturation", currentPageSurfacturation);
        model.addAttribute("ecartAllFeesSurfacturation", ecartAllFeesSurfacturation);


        Page<EcartAllFees> ecartAllFeesErrone = ecartAllFeesService.getEcartAllFeesErrone(PageRequest.of(currentPageErrone, size));
        int[] pagesErrone = new int[ecartAllFeesErrone.getTotalPages()];
        model.addAttribute("pagesErrone", pagesErrone);
        model.addAttribute("currentPageErrone", currentPageErrone);
        model.addAttribute("ecartAllFeesErrone", ecartAllFeesErrone);


        model.addAttribute("firstDate", null);
        model.addAttribute("secondDate", null);
        model.addAttribute("errors", null);
        model.addAttribute("ecart", new EcartAllFees());
        model.addAttribute("allFee", new AllFees());

        return "ecartsAllFees/ecart_allFees";
    }


    @GetMapping("/searchDate")
    public String searchByDate(
            Model model,
            @RequestParam("firstDate") String firstDate,
            @RequestParam("secondDate") String secondDate,
            @RequestParam(name = "currentPageNotExist", defaultValue = "0") int currentPageNotExist,
            @RequestParam(name = "currentPageSurfacturation", defaultValue = "0") int currentPageSurfacturation,
            @RequestParam(name = "currentPageErrone", defaultValue = "0") int currentPageErrone
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

            // Not Exist allFees
            Page<EcartAllFees> ecartAllFeesPageNotExist = ecartAllFeesService.getEcartAllFeesNotExist(PageRequest.of(currentPageNotExist, 100));
            int[] pagesNotExist = new int[ecartAllFeesPageNotExist.getTotalPages()];
            model.addAttribute("pagesNotExist", pagesNotExist);
            model.addAttribute("currentPageNotExist", currentPageNotExist);
            model.addAttribute("ecartAllFeesNotExist", ecartAllFeesPageNotExist);


            Page<EcartAllFees> ecartAllFeesSurfacturation = ecartAllFeesService.getEcartAllFeesSurfacturation(PageRequest.of(currentPageSurfacturation, 100));
            int[] pagesSurfacturation = new int[ecartAllFeesSurfacturation.getTotalPages()];
            model.addAttribute("pagesSurfacturation", pagesSurfacturation);
            model.addAttribute("currentPageSurfacturation", currentPageSurfacturation);
            model.addAttribute("ecartAllFeesSurfacturation", ecartAllFeesSurfacturation);


            Page<EcartAllFees> ecartAllFeesErrone = ecartAllFeesService.getEcartAllFeesErrone(PageRequest.of(currentPageErrone, 100));
            int[] pagesErrone = new int[ecartAllFeesErrone.getTotalPages()];
            model.addAttribute("pagesErrone", pagesErrone);
            model.addAttribute("currentPageErrone", currentPageErrone);
            model.addAttribute("ecartAllFeesErrone", ecartAllFeesErrone);


            model.addAttribute("firstDate", null);
            model.addAttribute("secondDate", null);
            model.addAttribute("errors", null);
            model.addAttribute("ecart", new EcartAllFees());

            return "ecartsAllFees/ecart_allFees";
        }


        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
        date2 = new SimpleDateFormat("yyyy-MM-dd").parse(secondDate);

        // Not existe in AllFees
        Page<EcartAllFees> ecartAllFeesPageNotExist = ecartAllFeesService.
                getEcartBetweenDateNotExist(
                        PageRequest.of(Integer.valueOf(currentPageNotExist), 100),
                        date1,
                        date2
                );
        int[] pagesNotExist = new int[ecartAllFeesPageNotExist.getTotalPages()];
        model.addAttribute("pagesNotExist", pagesNotExist);
        model.addAttribute("currentPageNotExist", currentPageNotExist);
        model.addAttribute("ecartAllFeesNotExist", ecartAllFeesPageNotExist);


        // Surfacturation in AllFees
        Page<EcartAllFees> ecartAllFeesSurfacturation = ecartAllFeesService.
                getEcartBetweenDateSurfacturation(
                        PageRequest.of(Integer.valueOf(currentPageNotExist), 100),
                        date1,
                        date2
                );
        int[] pagesSurfacturation = new int[ecartAllFeesSurfacturation.getTotalPages()];
        model.addAttribute("pagesSurfacturation", pagesSurfacturation);
        model.addAttribute("currentPageSurfacturation", currentPageSurfacturation);
        model.addAttribute("ecartAllFeesSurfacturation", ecartAllFeesSurfacturation);


        // Errone in AllFees
        Page<EcartAllFees> ecartAllFeesErrone = ecartAllFeesService.
                getEcartBetweenDateErrone(
                        PageRequest.of(Integer.valueOf(currentPageNotExist), 100),
                        date1,
                        date2
                );
        int[] pagesErrone = new int[ecartAllFeesErrone.getTotalPages()];
        model.addAttribute("pagesErrone", pagesErrone);
        model.addAttribute("currentPageErrone", currentPageErrone);
        model.addAttribute("ecartAllFeesErrone", ecartAllFeesErrone);
        model.addAttribute("firstDate", firstDate);
        model.addAttribute("secondDate", secondDate);
        model.addAttribute("ecart", new EcartAllFees());

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


    @PostMapping("/ajouterToAllFeesRef")
    public String ajouterToAllFeesRef(@ModelAttribute("allFee") AllFees allFees,
                                      RedirectAttributes redirAttrs,
                                      Model model, BindingResult result) {


        allFees = ecartAllFeesService.ajouterEcartoAllFeesRef(allFees);
        if (allFees != null) {
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

    @PostMapping("/supprimerAllFeesRef")
    public String suprimerFromAllFeesRef(@ModelAttribute("allFee") AllFees allFees,
                                      RedirectAttributes redirAttrs,
                                      Model model, BindingResult result) {

        allFees = ecartAllFeesService.supprimerEcarFromAllFeesRef(allFees);
        if (allFees != null) {
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
