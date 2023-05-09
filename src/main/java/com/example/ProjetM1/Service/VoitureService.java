package com.example.ProjetM1.Service;

import com.example.ProjetM1.Entity.Voiture;
import com.example.ProjetM1.Repository.VoitureRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureService {

    @Autowired
    private VoitureRepositoryInterface voitureRepo;

    public void createVoiture(Voiture voiture){
        voitureRepo.save(voiture);
    }
    public List<Voiture> findAll(){
        return voitureRepo.findAll();
    }
    public Voiture GetId(String id){
        return voitureRepo.findById(id).orElse(null);
    }
    public void Delete(String id){
        voitureRepo.deleteById(id);
    }
    public Page<Voiture> findPage(int numpage, int sizepage){
        Pageable pageable = PageRequest.of(numpage -1,sizepage);
        return voitureRepo.findAll(pageable);
    }

    public void update(String id, Voiture voiture){
        Voiture voitureExist = voitureRepo.findById(id).orElse(null);
        if (voitureExist == null) {
            return;
        }
        voitureExist.setDesignation(voiture.getDesignation());
        voitureExist.setLoyer(voiture.getLoyer());
        voitureRepo.save(voitureExist);
    }
}
