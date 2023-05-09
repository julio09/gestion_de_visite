package com.example.ProjetM1.Controller;

import com.example.ProjetM1.Entity.Locataire;
import com.example.ProjetM1.Entity.Louer;
import com.example.ProjetM1.Entity.Voiture;
import com.example.ProjetM1.Repository.LocataireRepositoryInterface;
import com.example.ProjetM1.Repository.LouerRepositoryInterface;
import com.example.ProjetM1.Repository.VoitureRepositoryInterface;
import com.example.ProjetM1.Service.LouerService;
import com.example.ProjetM1.Service.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/loyer")
public class LoyerController {

    @Autowired
    private LouerService louerService;

    @Autowired
    private LocataireRepositoryInterface loca;

    @Autowired
    private LouerRepositoryInterface louers;

    @Autowired
    private VoitureRepositoryInterface voiture;

    public final PDFGenerator pdfGenerator;

    public LoyerController(PDFGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }


    @PostMapping("/list")
    public String AddLoyer(@ModelAttribute("louer") Louer louer, BindingResult bindingResult, RedirectAttributes redirectAttributes){
            if (bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("Error", "cette enregistrement a échouer");
                redirectAttributes.addFlashAttribute("class", "alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
                return "redirect:/loyer/list";
            }
        louerService.createMedecin(louer);
        redirectAttributes.addFlashAttribute("message", "Cette loyer a été enregistrer avec succer");
        redirectAttributes.addFlashAttribute("class","alert alert-success shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/loyer/list";
    }
    @GetMapping("/list")
    public ModelAndView list(){
        return ListLoyer(1);
    }
    @GetMapping("/list/{pageNum}")
    public ModelAndView ListLoyer(@PathVariable(value = "pageNum") int Pagenum){
        List<Voiture> voitureList = voiture.findAll();
        Double sum = louerService.getTotalAmount();
        int pageSize=5;
        Page<Louer> page= louerService.findPage(Pagenum,pageSize);
        List<Louer> louerList =page.getContent();
        Louer louer = new Louer();
        Voiture voiture = new Voiture();
        ModelAndView mv = new ModelAndView("loyer/loyer");
        mv.addObject("current",Pagenum);
        mv.addObject("page",page.getTotalPages());
        mv.addObject("Item",page.getTotalElements());
        mv.addObject("loyers",louerList);
        mv.addObject("loyer", louer);
        mv.addObject("voitures",voitureList);
        mv.addObject("Title", "Loyer");
        mv.addObject("sum", sum);
        return mv;
    }
    @GetMapping("/save")
    public String formLoyer(Model model, Louer louer){
        List<Locataire> locataireList =loca.findAll();
        List<Voiture> voitureList = voiture.findAll();
        model.addAttribute("louer",louer);
        model.addAttribute("locataires",locataireList);
        model.addAttribute("voitures",voitureList);
        model.addAttribute("Title","Ajout");
        model.addAttribute("pageTitle","Enregistrement de voiture :");
        model.addAttribute("button","Enregistrer");
        model.addAttribute("class","btn btn-success");
        return "loyer/form_loyer";
    }
    @GetMapping("/{id}")
    public String getId(@PathVariable("id") Long id, RedirectAttributes model){
        ModelAndView mv = new ModelAndView("view-voiture");
        mv.addObject("loyer", louerService.GetId(id));
        if(louerService.GetId(id) == null){
            model.addFlashAttribute("Erreur1", "Cette loyer "+id+" n'existe pas dans notre liste");
            model.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/voiture/list";
        }
        return "voiture/view-voiture";
    }
    @GetMapping("/delete/{id}")
    public String DeleteLoyer(@PathVariable("id") Long id,RedirectAttributes ra){
        System.out.println("delete");
        try {
            louerService.Delete(id);
            ra.addFlashAttribute("delete"
                    ,"Cette loyer "+ id + " a été effacer");
            ra.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/loyer/list";
    }
    @GetMapping("/edit/{id}")
    public String editLoyer(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        Louer louer = louerService.GetId(id);
        List<Locataire> locataireList =loca.findAll();
        List<Voiture> voitureList = voiture.findAll();
        if (louer == null) {
            redirectAttributes.addFlashAttribute("Erreur1", "Cette loyer "+id+" n'existe pas dans notre liste");
            redirectAttributes.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/loyer/list";
        }
        model.addAttribute("louer",louer);
        model.addAttribute("Title","Modification");
        model.addAttribute("pageTitle","Modification de loyer : " + id);
        model.addAttribute("button","Modifier");
        model.addAttribute("class","btn btn-danger");
        model.addAttribute("locataires",locataireList);
        model.addAttribute("voitures",voitureList);
        return "loyer/form_loyer";
    }

    @PostMapping
    public String updateLoyer(@PathVariable("id") Long id,@ModelAttribute("louer") Louer louer, RedirectAttributes redirectAttributes) {
        louerService.update(id, louer);
        redirectAttributes.addFlashAttribute("update", "Cette loyer "+ id + " a été modifier");
        redirectAttributes.addFlashAttribute("class","alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/loyer/list";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> LoyerReport()  throws IOException {
        List<Louer> louers = louerService.findAll();

        ByteArrayInputStream bis = PDFGenerator.employeePDFReport(louers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=loyer.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    @GetMapping("/search")
    public String searchByDateLocationBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        louers.findLouersByDateRange(startDate, endDate);
        return "recherche";
    }
    @PostMapping("/search")
    public String searchByDateLocationBetween(
            @ModelAttribute("loyer") Louer louer,
            Model model) {
        List<Louer> louerList = louers.findLouersByDateRange(louer.getDate(), louer.getDate());
        model.addAttribute("louers", louerList);
        return "recherche";
    }
}
