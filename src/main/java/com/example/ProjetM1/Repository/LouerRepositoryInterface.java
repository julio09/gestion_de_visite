package com.example.ProjetM1.Repository;

import com.example.ProjetM1.Entity.Louer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LouerRepositoryInterface extends JpaRepository<Louer, Long> {
    @Query(value = "SELECT SUM(l1.nombre_jour*v1.loyer) AS SOMME FROM louer l1 INNER JOIN voiture v1 WHERE v1.id=l1.voiture_id", nativeQuery = true)
    Double sumAmounts();

    @Query(value = "SELECT (l.nombre_jour * v.loyer) as Montant FROM louer l INNER JOIN voiture v WHERE l.voiture_id = v.id",nativeQuery = true)
    Double Montant();


    @Query("SELECT l FROM Louer l WHERE l.date BETWEEN :startDate AND :endDate")
    List<Louer> findLouersByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
