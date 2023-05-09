package com.example.ProjetM1.Controller;

import com.example.ProjetM1.Entity.Visiter;
import com.example.ProjetM1.Service.VisiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visiter")
public class VisiterController {

    @Autowired
    public VisiterService visiterService;

    @GetMapping
    @RequestMapping()
    public List<Visiter> list(){
        return visiterService.Listevisite();
    }
    @GetMapping("/{id}")
    public Visiter getId(@PathVariable("id") Long id){
        return visiterService.getId(id);
    }

    @PostMapping
    public Visiter addVisite(@RequestBody Visiter visiter){
        return visiterService.Create(visiter);
    }
    @DeleteMapping("/{id}")
    public void DeleteVisite(@PathVariable("id") Long id){
          visiterService.delete(id);
    }

    @PutMapping("/{id}")
    public Visiter Update(@PathVariable("id") Long id, @RequestBody Visiter visiter){
        return visiterService.update(id, visiter);
    }
}
