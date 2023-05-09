package com.example.ProjetM1.Entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "voiture")
public class Voiture {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "loyer")
    private Double Loyer;

    @OneToMany(mappedBy = "voiture", cascade = CascadeType.MERGE, orphanRemoval = true)
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getLoyer() {
        return Loyer;
    }

    public void setLoyer(Double loyer) {
        Loyer = loyer;
    }

    @Override
    public String toString() {
        return designation;
    }
}