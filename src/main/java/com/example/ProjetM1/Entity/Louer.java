package com.example.ProjetM1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "louer")
public class Louer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locataire_id")
    private Locataire locataire;

    @Column(name = "nombre_jour", nullable = false)
    private Integer nbjour;

    @Column(name = "date_location", nullable = false)
    private java.sql.Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voiture_id")
    private Voiture voiture;

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Locataire getLocataire() {
        return locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    public Integer getNbjour() {
        return nbjour;
    }

    public void setNbjour(Integer nbjour) {
        this.nbjour = nbjour;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

}