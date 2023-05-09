package com.example.ProjetM1.Repository;

import com.example.ProjetM1.Entity.Visiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisiterRepositoryInterface extends JpaRepository<Visiter, Long> {
}
