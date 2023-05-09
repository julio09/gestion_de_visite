package com.example.ProjetM1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "medecin")
public class Medicin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom")
    @Size(min = 3)
    @NotNull
    @NotBlank
    private String Nom;
    @Column(name = "prenom")
    @Size(min = 3)
    @NotBlank
    private String Prenom;

    @Column(name = "grade")
    @Size(min = 3)
    @NotBlank
    private String Grade;

    @OneToMany(mappedBy = "medicin", orphanRemoval = true)
    private Set<Visiter> visiters = new LinkedHashSet<>();

    public Set<Visiter> getVisiters() {
        return visiters;
    }

    public void setVisiters(Set<Visiter> visiters) {
        this.visiters = visiters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    @Override
    public String toString() {
        return Nom;
    }
}