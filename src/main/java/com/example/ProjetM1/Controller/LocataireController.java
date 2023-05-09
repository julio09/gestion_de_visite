package com.example.ProjetM1.Controller;

import com.example.ProjetM1.Entity.Locataire;
import com.example.ProjetM1.Repository.LocataireRepositoryInterface;
import com.example.ProjetM1.Service.LocataireService;
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
@RequestMapping("/locataire")
public class LocataireController {

    @Autowired
    private LocataireService locataireService;


    @PostMapping("/list")
    public String AddLocataire(@ModelAttribute("locataire") Locataire locataire, BindingResult bindingResult, RedirectAttributes redirectAttributes){
            if (bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("Error", "cette enregistrement a échouer");
                redirectAttributes.addFlashAttribute("class", "alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
                return "redirect:/locataire/list";
            }
        locataireService.createLocataire(locataire);
        redirectAttributes.addFlashAttribute("message", "Cette locataire a été enregistrer avec succer");
        redirectAttributes.addFlashAttribute("class","alert alert-success shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/locataire/list";
    }
    @GetMapping("/list")
    public ModelAndView list(){
        return ListLocataire(1);
    }
    @GetMapping("/list/{pageNum}")
    public ModelAndView ListLocataire(@PathVariable(value = "pageNum") int Pagenum){
        int pageSize=5;
        Page<Locataire> page= locataireService.findPage(Pagenum,pageSize);
        List<Locataire> locataireList =page.getContent();
        ModelAndView mv = new ModelAndView("locataire/locataire");
        mv.addObject("current",Pagenum);
        mv.addObject("page",page.getTotalPages());
        mv.addObject("Item",page.getTotalElements());
        mv.addObject("locataires",locataireList);
        mv.addObject("Title", "Locataire");
        return mv;
    }
    @GetMapping("/save")
    public String formLocataire(Model model, Locataire locataire){
        model.addAttribute("locataire",locataire);
        model.addAttribute("Title","Ajout");
        model.addAttribute("pageTitle","Enregistrement de locataire :");
        model.addAttribute("button","Enregistrer");
        model.addAttribute("class","btn btn-success");
        return "locataire/form_locataire";
    }
    @GetMapping("/{id}")
    public String getId(@PathVariable("id") String id, RedirectAttributes model){
        ModelAndView mv = new ModelAndView("view-locataire");
        mv.addObject("locataire", locataireService.GetId(id));
        if(locataireService.GetId(id) == null){
            model.addFlashAttribute("Erreur1", "Cette locataire "+id+" n'existe pas dans notre liste");
            model.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/locataire/list";
        }
        return "locataire/view-locataire";
    }
    @GetMapping("/delete/{id}")
    public String DeleteLocataire(@PathVariable("id") String id,RedirectAttributes ra){
        System.out.println("delete");
        try {
            locataireService.Delete(id);
            ra.addFlashAttribute("delete"
                    ,"Cette locataire "+ id + " a été effacer");
            ra.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/locataire/list";
    }
    @GetMapping("/edit/{id}")
    public String editLocataire(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {

        Locataire locataire = locataireService.GetId(id);
        if (locataire == null) {
            redirectAttributes.addFlashAttribute("Erreur1", "Cette locatiare "+id+" n'existe pas dans notre liste");
            redirectAttributes.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/locataire/list";
        }
        model.addAttribute("locataire",locataire);
        model.addAttribute("Title","Modification");
        model.addAttribute("pageTitle","Modification de locataire : " + id);
        model.addAttribute("button","Modifier");
        model.addAttribute("class","btn btn-danger");
        return "locataire/form_locataire";
    }
}
