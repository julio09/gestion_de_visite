package com.example.ProjetM1.Entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "locataire")
public class Locataire {
    @Id
    @Column(name = "num_Locataire", nullable = false)
    private String id;
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @OneToMany(mappedBy = "locataire", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Louer> louers = new LinkedHashSet<>();

    public Set<Louer> getLouers() {
        return louers;
    }

    public void setLouers(Set<Louer> louers) {
        this.louers = louers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return nom;
    }
}