package com.example.ProjetM1.Service;

import com.example.ProjetM1.Entity.Medicin;
import com.example.ProjetM1.Repository.MedicinRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {
    @Autowired
    private MedicinRepositoryInterface medicinRepositoryInterface;

    public void createMedecin(Medicin medicin){
        medicinRepositoryInterface.save(medicin);
    }
    public List<Medicin> findAll(Medicin medicin){
        return (List<Medicin>) medicinRepositoryInterface.findAll();
    }
    public Medicin GetId(Long id){
        return medicinRepositoryInterface.findById(id).orElse(null);
    }
    public void Delete(Long id){
        medicinRepositoryInterface.deleteById(id);
    }
    public Page<Medicin> findPage(int numpage,int sizepage){
        Pageable pageable = PageRequest.of(numpage -1,sizepage);
        return medicinRepositoryInterface.findAll(pageable);
    }

    public void update(Long id, Medicin medicin){
            Medicin medicinExist = medicinRepositoryInterface.findById(id).orElse(null);
            if (medicinExist == null) {
                return;
            }
            medicinExist.setNom(medicin.getNom());
            medicinExist.setPrenom(medicin.getPrenom());
            medicinExist.setGrade(medicin.getGrade());
        medicinRepositoryInterface.save(medicinExist);
    }
}
