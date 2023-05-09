package com.example.ProjetM1.Service;

import com.example.ProjetM1.Entity.Visiter;
import com.example.ProjetM1.Repository.VisiterRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VisiterService {
    @Autowired
    public VisiterRepositoryInterface visiterRepository;

    public Visiter Create(Visiter visiter){
        return visiterRepository.save(visiter);
    }

    public Visiter getId(Long id){
        return visiterRepository.findById(id).orElse(null);
    }


    public List<Visiter> Listevisite(){
        return visiterRepository.findAll();
    }


    public void delete(Long id){
         visiterRepository.deleteById(id);
    }


    public Visiter update(Long id, Visiter visiter){
        Visiter visiterExist = getId(id);
        visiterExist.setMedicin(visiter.getMedicin());
        visiterExist.setPatient(visiter.getPatient());
        visiterExist.setDate(LocalDate.now());
        visiterRepository.save(visiterExist);
        return visiterExist;
    }
}
