package com.example.ProjetM1.Service;

import com.example.ProjetM1.Entity.Patient;
import com.example.ProjetM1.Repository.PatientRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    public PatientRepositoryInterface patientRepositoryInterface;

    public void createPatient(Patient patient){
        patientRepositoryInterface.save(patient);
    }

    public Patient GetId(Long id){
        return patientRepositoryInterface.findById(id).orElse(null);
    }
    public void Delete(Long id){
        patientRepositoryInterface.deleteById(id);
    }
    public Page<Patient> findPage(int numpage, int sizepage){
        Pageable pageable = PageRequest.of(numpage -1,sizepage);
        return patientRepositoryInterface.findAll(pageable);
    }

    public void update(Long id, Patient patient){
        Patient patientExist = patientRepositoryInterface.findById(id).orElse(null);
        if (patientExist == null) {
            return;
        }
        patientExist.setNom(patient.getNom());
        patientExist.setPrenom(patient.getPrenom());
        patientExist.setSexe(patient.getSexe());
        patientExist.setAdresse(patient.getAdresse());
        patientRepositoryInterface.save(patientExist);
    }
}
