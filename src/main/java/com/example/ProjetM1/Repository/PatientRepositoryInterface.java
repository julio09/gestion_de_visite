package com.example.ProjetM1.Repository;

import com.example.ProjetM1.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepositoryInterface extends JpaRepository<Patient, Long> {
}
