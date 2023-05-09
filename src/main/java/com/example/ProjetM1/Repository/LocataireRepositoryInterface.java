package com.example.ProjetM1.Repository;

import com.example.ProjetM1.Entity.Locataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocataireRepositoryInterface extends JpaRepository<Locataire, String> {
}
