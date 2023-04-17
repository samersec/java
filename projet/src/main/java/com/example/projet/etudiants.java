package com.example.projet;


public class etudiants {

    private int id;
    private String Prenom;
    private String Nom;

    private String Daten;

    private String Mail;
    private String Cours;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDaten() {
        return Daten;
    }

    public void setDaten(String datenn) {
        Daten = datenn;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getCours() {
        return Cours;
    }

    public void setCours(String cours) {
        Cours = cours;
    }
}

