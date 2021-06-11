package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Helper.AllFeesExcelExporter;
import com.hadday.gestion_commission.Service.AllFeesService;
import com.hadday.gestion_commission.entities.AllFees;
import com.hadday.gestion_commission.entities.EcartAllFees;
import com.hadday.gestion_commission.repositories.AllFeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/allFees")
public class AllFeesController {

    @Autowired
    private AllFeesService allFeesService;

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1000") int size
    ) {


        Page<AllFees> allFees = allFeesService.findAllFees(PageRequest.of(page, size));

        int[] pages = new int[allFees.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);

        model.addAttribute("firstDate", null);
        model.addAttribute("secondDate", null);
        model.addAttribute("errors", null);
        model.addAttribute("ecart", new EcartAllFees());

        model.addAttribute("allfees", allFees);
        return "allFees/allFees";
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
            Page<AllFees> allFees = allFeesService.findAllFees(PageRequest.of(0, 1000));

            int[] pages = new int[allFees.getTotalPages()];
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", 0);

            model.addAttribute("firstDate", null);
            model.addAttribute("secondDate", null);
            model.addAttribute("errors", errors);

            model.addAttribute("allfees", allFees);
            return "allFees/allFees";
        }

        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
        date2 = new SimpleDateFormat("yyyy-MM-dd").parse(secondDate);

        Page<AllFees> allFees = allFeesService.
                findAllFeesBetwwenFDate(
                        PageRequest.of(Integer.valueOf(currentPage), 1000),
                        date1,
                        date2
                );

        System.out.println(allFees.getContent());

        int[] pages = new int[allFees.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstDate", firstDate);
        model.addAttribute("secondDate", secondDate);
        model.addAttribute("ecart", new EcartAllFees());

        model.addAttribute("allfees", allFees);
        return "allFees/allFees";
    }



    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=AllFees_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<AllFees> allFees = allFeesService.findAllFees();

        AllFeesExcelExporter excelExporter = new AllFeesExcelExporter(allFees);

        excelExporter.export(response);

//        return "redirect:/allFees/";
    }

}
