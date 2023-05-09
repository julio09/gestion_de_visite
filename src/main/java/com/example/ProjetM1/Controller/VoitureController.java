package com.example.ProjetM1.Controller;

import com.example.ProjetM1.Entity.Voiture;
import com.example.ProjetM1.Service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/voiture")
public class VoitureController {

    @Autowired
    private VoitureService voitureService;


    @PostMapping("/list")
    public String AddVoiture(@ModelAttribute("voiture") Voiture voiture, BindingResult bindingResult, RedirectAttributes redirectAttributes){
            if (bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("Error", "cette enregistrement a échouer");
                redirectAttributes.addFlashAttribute("class", "alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
                return "redirect:/voiture/list";
            }
        voitureService.createVoiture(voiture);
        redirectAttributes.addFlashAttribute("message", "Cette voiture a été enregistrer avec succer");
        redirectAttributes.addFlashAttribute("class","alert alert-success shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/voiture/list";
    }
    @GetMapping("/list")
    public ModelAndView list(){
        return ListVoiture(1);
    }
    @GetMapping("/list/{pageNum}")
    public ModelAndView ListVoiture(@PathVariable(value = "pageNum") int Pagenum){
        int pageSize=5;
        Page<Voiture> page= voitureService.findPage(Pagenum,pageSize);
        List<Voiture> voitureList =page.getContent();
        ModelAndView mv = new ModelAndView("voiture/voiture");
        mv.addObject("current",Pagenum);
        mv.addObject("page",page.getTotalPages());
        mv.addObject("Item",page.getTotalElements());
        mv.addObject("voitures",voitureList);
        mv.addObject("Title", "Voiture");
        return mv;
    }
    @GetMapping("/save")
    public String formVoiture(Model model, Voiture voiture){
        model.addAttribute("voiture",voiture);
        model.addAttribute("Title","Ajout");
        model.addAttribute("pageTitle","Enregistrement de voiture :");
        model.addAttribute("button","Enregistrer");
        model.addAttribute("class","btn btn-success");
        return "voiture/form_voiture";
    }
    @GetMapping("/{id}")
    public String getId(@PathVariable("id") String id, RedirectAttributes model){
        ModelAndView mv = new ModelAndView("view-voiture");
        mv.addObject("voiture", voitureService.GetId(id));
        if(voitureService.GetId(id) == null){
            model.addFlashAttribute("Erreur1", "Cette voiture "+id+" n'existe pas dans notre liste");
            model.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/voiture/list";
        }
        return "voiture/view-voiture";
    }
    @GetMapping("/delete/{id}")
    public String DeleteVoiture(@PathVariable("id") String id,RedirectAttributes ra){
        System.out.println("delete");
        try {
            voitureService.Delete(id);
            ra.addFlashAttribute("delete"
                    ,"Cette voiture "+ id + " a été effacer");
            ra.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/voiture/list";
    }
    @GetMapping("/edit/{id}")
    public String editVoiture(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {

        Voiture voitureIn = voitureService.GetId(id);
        if (voitureIn == null) {
            redirectAttributes.addFlashAttribute("Erreur1", "Cette voiture "+id+" n'existe pas dans notre liste");
            redirectAttributes.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/voiture/list";
        }
        model.addAttribute("voitureIn",voitureIn);
        model.addAttribute("Title","Modification");
        model.addAttribute("pageTitle","Modification de voiture : " + id);
        model.addAttribute("button","Modifier");
        model.addAttribute("class","btn btn-danger");
        return "voiture/edit_voiture";
    }

    @PostMapping
    public String updateVoiture(@PathVariable("id") String id,@ModelAttribute("voitureIn") Voiture voitureIn, RedirectAttributes redirectAttributes) {
        voitureService.update(id, voitureIn);
        redirectAttributes.addFlashAttribute("update", "Cette voiture "+ id + " a été modifier");
        redirectAttributes.addFlashAttribute("class","alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/voiture/list";
    }
}
