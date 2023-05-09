package com.example.ProjetM1.Service;

import com.example.ProjetM1.Entity.Louer;
import com.example.ProjetM1.Repository.LouerRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LouerService {
    @Autowired
    private LouerRepositoryInterface louerRepo;

    public void createMedecin(Louer louer){
        louerRepo.save(louer);
    }
    public List<Louer> findAll(){
        return louerRepo.findAll();
    }

    public Louer GetId(Long id){
        return louerRepo.findById(id).orElse(null);
    }

    public void Delete(Long id){
        louerRepo.deleteById(id);
    }

    public Page<Louer> findPage(int numpage, int sizepage){
        Pageable pageable = PageRequest.of(numpage -1,sizepage);
        return louerRepo.findAll(pageable);
    }

    public void update(Long id, Louer louer){
        Louer louerExist = louerRepo.findById(id).orElse(null);
        if (louerExist == null) {
            return;
        }
        louerExist.setLocataire(louer.getLocataire());
        louerExist.setVoiture(louer.getVoiture());
        louerExist.setDate(louer.getDate());
        louerExist.setNbjour(louer.getNbjour());
        louerRepo.save(louerExist);
    }

    public Double getTotalAmount() {
        return louerRepo.sumAmounts();
    }
    public Double getMontant(){
        return louerRepo.Montant();
    }
}
