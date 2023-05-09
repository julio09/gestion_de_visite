package com.example.ProjetM1.Controller;

import com.example.ProjetM1.Entity.Patient;
import com.example.ProjetM1.Service.PatientService;
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
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public String AddMedicine(@ModelAttribute Patient patient, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            System.out.println("Erreur ");
            redirectAttributes.addFlashAttribute("Error", "Enregistrement échouer");
            redirectAttributes.addFlashAttribute("class", "alert alert-warning shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/patient/list";
        }
        patientService.createPatient(patient);
        redirectAttributes.addFlashAttribute("message", "Cette patient a été enregistrer avec succer");
        redirectAttributes.addFlashAttribute("class","alert alert-success shadow-lg p-3 mb-5 bg-body rounded");
        return "redirect:/patient/list";
    }
    @GetMapping("/list")
    public ModelAndView list(){
        return savePatient(1);
    }
    @GetMapping("/list/{pageNum}")
    public ModelAndView savePatient(@PathVariable(value = "pageNum") int Pagenum){
        int pageSize=5;
        Page<Patient> page= patientService.findPage(Pagenum,pageSize);
        List<Patient> patientList =page.getContent();
        ModelAndView mv = new ModelAndView("patient/patients");
        mv.addObject("current",Pagenum);
        mv.addObject("page",page.getTotalPages());
        mv.addObject("Item",page.getTotalElements());
        mv.addObject("patients",patientList);
        mv.addObject("Title", "Patient");
        return mv;
    }
    @GetMapping("/save")
    public String formPatient(Model model, Patient patient){
        model.addAttribute("patient",patient);
        model.addAttribute("Title","Ajout");
        model.addAttribute("pageTitle","Enregistrement de patient :");
        model.addAttribute("button","Enregistrer");
        model.addAttribute("class","btn btn-success col-md-12");
        return "patient/form_patient";
    }
    @GetMapping("/{id}")
    public String getId(@PathVariable("id") Long id, RedirectAttributes model){
        ModelAndView mv = new ModelAndView("view-med");
        mv.addObject("patient", patientService.GetId(id));
        if(patientService.GetId(id) == null){
            model.addFlashAttribute("Erreur1", "Cette patient "+id+" n'existe pas dans notre liste");
            model.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/patient/list";
        }
        return "patient/view-patient";
    }
    @GetMapping("/delete/{id}")
    public String DeletePatient(@PathVariable("id") Long id,RedirectAttributes ra){
        System.out.println("delete");
        try {
            patientService.Delete(id);
            ra.addFlashAttribute("delete"
                    ,"Cette patient "+ id + " a été effacer");
            ra.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/patient/list";
    }
    @GetMapping("/edit/{id}")
    public String EditMed(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        Patient patient = patientService.GetId(id);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("Erreur1", "Cette medecin "+id+" n'existe pas dans notre liste");
            redirectAttributes.addFlashAttribute("class", "alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
            return "redirect:/Patient/list";
        }
        model.addAttribute("patient",patient);
        model.addAttribute("Title","Modification");
        model.addAttribute("pageTitle","Modification de patient : " + id);
        model.addAttribute("button","Modifier");
        model.addAttribute("class","btn btn-danger col-md-12");
        redirectAttributes.addFlashAttribute("update", "Cette patient "+ id + " a été modifier");
        redirectAttributes.addFlashAttribute("class","alert alert-danger shadow-lg p-3 mb-5 bg-body rounded");
        return "patient/form_Patient";
    }






    //----------------------------------------------service web Rest Patient--------------------------------------------------//

    /**
    @Autowired
    public PatientService patientService;

    @RequestMapping()
    public List<Patient> list(){
        return patientService.findAll();
    }
    @GetMapping("/{id}")
    public Patient getId(@PathVariable("id") Long id){
        return patientService.GetId(id);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient){
        return patientService.createPatient(patient);
    }
    @DeleteMapping("/{id}")
    public Patient DeleteMedecin(@PathVariable("id") Long id){
        return patientService.Delete(id);
    }

    @PutMapping("/{id}")
    public Patient Update(@PathVariable("id") Long id, @RequestBody Patient patient){
        return patientService.update(id, patient);
    }**/

}
