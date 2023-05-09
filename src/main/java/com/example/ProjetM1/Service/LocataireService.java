package com.example.ProjetM1.Service;

import com.example.ProjetM1.Entity.Locataire;
import com.example.ProjetM1.Repository.LocataireRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocataireService {

    @Autowired
    private LocataireRepositoryInterface locataireRepo;


    public void createLocataire(Locataire locataire){
        locataireRepo.save(locataire);
    }
    public List<Locataire> findAll(){
        return locataireRepo.findAll();
    }
    public Locataire GetId(String id){
        return locataireRepo.findById(id).orElse(null);
    }
    public void Delete(String id){
        locataireRepo.deleteById(id);
    }
    public Page<Locataire> findPage(int numpage, int sizepage){
        Pageable pageable = PageRequest.of(numpage -1,sizepage);
        return locataireRepo.findAll(pageable);
    }
    public void update(String id, Locataire locataire){
        Locataire locataireExist = locataireRepo.findById(id).orElse(null);
        if (locataireExist == null) {
            return;
        }
        locataireExist.setNom(locataire.getNom());
        locataireExist.setAdresse(locataire.getAdresse());
        locataireRepo.save(locataireExist);
    }
}
