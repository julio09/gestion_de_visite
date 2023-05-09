package com.example.ProjetM1.Repository;

import com.example.ProjetM1.Entity.Medicin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinRepositoryInterface extends JpaRepository<Medicin, Long> {
}
