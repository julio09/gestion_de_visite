package com.example.ProjetM1.Repository;

import com.example.ProjetM1.Entity.Patient;
import com.example.ProjetM1.Entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepositoryInterface extends JpaRepository<Voiture, String> {
}
