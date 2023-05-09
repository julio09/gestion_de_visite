package com.example.ProjetM1.Controller;

import com.example.ProjetM1.Entity.Medicin;
import com.example.ProjetM1.Service.MedicineService;
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
@RequestMapping("/Medecin")
public class MedecinController {
    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public String AddMedicine(@ModelAttribute Medicin medicin, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            System.out.println("Erreur ");
            redirectAttributes.addFlashAttribute("Error", "Enregistrement échouer");
            redirectAttributes.addFlashAttribute("class", "alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/Medecin/list";
        }
        medicineService.createMedecin(medicin);
        redirectAttributes.addFlashAttribute("message", "Cette medecin a été enregistrer avec succer");
        redirectAttributes.addFlashAttribute("class","alert alert-success shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/Medecin/list";
    }
    @GetMapping("/list")
    public ModelAndView list(){
        return saveMedecin(1);
    }
    @GetMapping("/list/{pageNum}")
    public ModelAndView saveMedecin(@PathVariable(value = "pageNum") int Pagenum){
        int pageSize=5;
        Page<Medicin> page= medicineService.findPage(Pagenum,pageSize);
        List<Medicin> medicinList =page.getContent();
        ModelAndView mv = new ModelAndView("medecin/medicin");
        mv.addObject("current",Pagenum);
        mv.addObject("page",page.getTotalPages());
        mv.addObject("Item",page.getTotalElements());
        mv.addObject("medecins",medicinList);
        mv.addObject("Title", "Medecine");
        return mv;
    }
    @GetMapping("/save")
    public String formMedecin(Model model, Medicin medicin){
        model.addAttribute("medecin",medicin);
        model.addAttribute("Title","Ajout");
        model.addAttribute("pageTitle","Enregistrement de medecin :");
        model.addAttribute("button","Enregistrer");
        model.addAttribute("class","btn btn-success col-md-12");
        return "medecin/form_Medecin";
    }
    @GetMapping("/{id}")
    public String getId(@PathVariable("id") Long id, RedirectAttributes model){
        ModelAndView mv = new ModelAndView("view-med");
        mv.addObject("medicin", medicineService.GetId(id));
        if(medicineService.GetId(id) == null){
             model.addFlashAttribute("Erreur1", "Cette medecin "+id+" n'existe pas dans notre liste");
             model.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/Medecin/list";
        }
        return "medecin/view-med";
    }
    @GetMapping("/delete/{id}")
    public String DeleteMedecin(@PathVariable("id") Long id,RedirectAttributes ra){
        System.out.println("delete");
        try {
            medicineService.Delete(id);
            ra.addFlashAttribute("delete"
                    ,"Cette medecin "+ id + " a été effacer");
            ra.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/Medecin/list";
    }
    @GetMapping("/edit/{id}")
    public String EditMed(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

               Medicin medicin = medicineService.GetId(id);
               if (medicin == null) {
                   redirectAttributes.addFlashAttribute("Erreur1", "Cette medecin "+id+" n'existe pas dans notre liste");
                   redirectAttributes.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
                   return "redirect:/Medecin/list";
               }
               model.addAttribute("medecin",medicin);
               model.addAttribute("Title","Modification");
               model.addAttribute("pageTitle","Modification de medecin : " + id);
               model.addAttribute("button","Modifier");
               model.addAttribute("class","btn btn-danger col-md-12");
               redirectAttributes.addFlashAttribute("update", "Cette medecin "+ id + " a été modifier");
               redirectAttributes.addFlashAttribute("class","alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
               return "medecin/form_Medecin";
    }


//----------------------------------------Service web REST-----------------------------------------
/**
@Autowired
private MedicineService medicineService;

    @RequestMapping()
    public List<Medicin> list(Medicin medicin){
        return medicineService.findAll(medicin);
    }
    @GetMapping("/{id}")
    public Optional<Medicin> getId(@PathVariable("id") Long id){
        return medicineService.GetId(id);
    }

    @PostMapping
    public Medicin AddMedicine(@RequestBody Medicin medicin){
        return medicineService.createMedecin(medicin);
    }
    @DeleteMapping("/{id}")
    public Medicin DeleteMedecin(@PathVariable("id") Long id){
        return medicineService.Delete(id);
    }

    @PutMapping("/{id}")
    public Medicin Update(@PathVariable("id") Long id, @RequestBody Medicin medicin){
        return medicineService.update(id, medicin);
    }
    **/
}

